package me.test.first.spring.boot.test;

import io.github.resilience4j.bulkhead.*;
import io.github.resilience4j.cache.Cache;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedRunnable;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author dangqian.zll
 * @date 2020/11/1
 * @see <a href="https://resilience4j.readme.io/docs">Resilience4j user guide</a>
 */
@Slf4j
public class Resilience4jTest {


    /**
     * 用于原有链路报错时,给出替代动作（执行额外事情，返回默认值等）。
     *
     * @see reactor.core.publisher.Flux#doOnError(java.util.function.Consumer)
     * @see reactor.core.publisher.Flux#subscribe(java.util.function.Consumer, java.util.function.Consumer)
     * @see org.reactivestreams.Subscriber#onError(Throwable)
     * @see io.reactivex.rxjava3.core.Flowable#subscribe(io.reactivex.rxjava3.functions.Consumer, io.reactivex.rxjava3.functions.Consumer)
     * @see io.reactivex.rxjava3.core.Observable#onErrorReturn
     */
    @Test
    public void circuitBreaker01() {
        // Given
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        // When I decorate my function and invoke the decorated function
        Supplier<String> checkedSupplier =
                CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
                    throw new RuntimeException("BAM!");
                });
        Try<String> result = Try.ofSupplier(checkedSupplier)
                .recover(throwable -> "Hello Recovery");

        // Then the function should be a success,
        // because the exception could be recovered
        assertTrue(result.isSuccess());
        // and the result must match the result of the recovery function.
        assertEquals("Hello Recovery", result.get());
    }

    public static String getIsoTimeStr() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static class MyJob implements Runnable {
        long id = 0;
        String data;
        long sleepMs;

        public MyJob(long id, String data, long sleepMs) {
            this.id = id;
            this.data = data;
            this.sleepMs = sleepMs;
        }

        @Override
        public void run() {
            try {
                String startTime = getIsoTimeStr();
                Thread.sleep(sleepMs);
                String endTime = getIsoTimeStr();
                String msg = "biz: id=" + id
                             + ", data=" + data
                             + ", thread=" + Thread.currentThread().getName()
                             + ", startTime=" + startTime
                             + ", endTime=" + endTime;
                System.out.println(msg);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Bulkhead 用来限制最大并发数。
     * 比如：作为业务平台实现方，会有很多业务场景接入，且每个业务场景的流量不一样。
     * 预设有一个大的共享线程池服务业务，但每个业务场景最多允许4个任务同时执行，
     * 防止单个业务场景过度抢占资源。
     * <p>
     * 注意：默认使用 SemaphoreBulkhead ，需要外部提供 线程（池）来运行，相应的任务即便等待中，也仍然会占用一个线程。
     * <p>
     * 如果使用 FixedThreadPoolBulkhead, 可以避免创建额额外的线程，对应的线程都一致在运行业务逻辑，而不是在等并发许可。
     * 缺点就是对应的场景如果迟迟没有流量，其线程池中的线程则一直在等待任务。
     *
     * @see io.github.resilience4j.bulkhead.internal.SemaphoreBulkhead
     * @see io.github.resilience4j.bulkhead.internal.FixedThreadPoolBulkhead
     */
    @Test
    public void semaphoreBulkhead01() {

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(3)
                .maxWaitDuration(Duration.ofMillis(2000))
                .build();

        BulkheadRegistry registry = BulkheadRegistry.of(config);

        // 直接获取给定的实例，或者用默认配置创建后、注册、并返回
        Bulkhead bulkhead = registry.bulkhead("name1");

        List<Runnable> list = LongStream.range(0, 10)
                .boxed()
                .map(i -> new MyJob(i, "data" + i, 1000L))
                // 封装
                .map(runnable -> Bulkhead.decorateRunnable(bulkhead, runnable))
                .collect(Collectors.toList());

        // 用大线程池（8并发）去并发执行，预期实际上也只有3个在同时执行
        testWithCountDownLatch01(list);
    }


    @SneakyThrows
    public void testWithCountDownLatch01(List<Runnable> list) {

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("my-job-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                8,
                8,
                60000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                factory
        );

        long startTime = System.currentTimeMillis();
        int count = list.size();
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            Runnable job = list.get(i);
            executor.submit(() -> {
                try {
                    job.run();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(1 * count, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();
        long totalCost = endTime - startTime;
        System.out.println("ALL END, totalCost=" + totalCost);
    }


    /**
     * Retry 默认会尝试3次. 基于是否抛出异常判定为成功、失败。
     */
    @Test
    public void retry01() {
        AtomicLong c = new AtomicLong(0);
        Supplier<String> bizSupplier = () -> {
            long count = c.getAndAdd(1);
            if (count < 5) {
                throw new RuntimeException("err-" + count);
            }
            return "aaa";
        };

        Retry retry = Retry.ofDefaults("retry01");
        Supplier<String> decoratedSupplier = Retry.decorateSupplier(retry, bizSupplier);

        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery : " + throwable.getMessage()).get();
        System.out.println("result = " + result);
        assertTrue(result.contains("err-2"));
    }

    /**
     * Retry: 基于返回值判定是否成功
     */
    @Test
    public void retry02() {

        AtomicLong c = new AtomicLong(0);

        RetryConfig config = RetryConfig.<Long>custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(1000))
                .retryOnResult(l -> l < 2)
                //.retryOnException(e -> e instanceof WebServiceException)
                //.retryExceptions(IOException.class, TimeoutException.class)
                //.ignoreExceptions(BusinessException.class, OtherBusinessException.class)
                .build();
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("backendName");
        Supplier<Long> decoratedSupplier = Retry.decorateSupplier(retry, () -> c.getAndAdd(1));
        Long result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> 999L)
                .get();
        System.out.println("result = " + result);
        assertEquals(2, result);
    }

    protected String errToResult(Throwable e) {
        return "bbb";
    }


    @Test
    public void toCompletableFuture01() {
        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if (count < 5) {
                throw new RuntimeException("err-" + count);
            }
            return "aaa";
        };
        // Create a CircuitBreaker with default configuration
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendService");

        // You can also run the supplier asynchronously in a ThreadPoolBulkhead
        ThreadPoolBulkhead threadPoolBulkhead = ThreadPoolBulkhead
                .ofDefaults("backendService");

        // The Scheduler is needed to schedule a timeout
        // on a non-blocking CompletableFuture
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        TimeLimiter timeLimiter = TimeLimiter.of(Duration.ofSeconds(1));

        CompletableFuture<String> future = Decorators.ofSupplier(supplier)
                .withThreadPoolBulkhead(threadPoolBulkhead)
                .withTimeLimiter(timeLimiter, scheduledExecutorService)
                .withCircuitBreaker(circuitBreaker)
                .withFallback(
                        Arrays.asList(
                                TimeoutException.class,
                                CallNotPermittedException.class,
                                BulkheadFullException.class
                        ),
                        throwable -> "Hello from Recovery"
                )
                .get()
                .toCompletableFuture();
    }

    @Test
    public void y2() {

        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if (count < 5) {
                throw new RuntimeException("err-" + count);
            }
            return "aaa";
        };
        // Create a CircuitBreaker with default configuration
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendService");

        // Create a Bulkhead with default configuration
        Bulkhead bulkhead = Bulkhead.ofDefaults("backendService");


        // Create a Retry with default configuration
        // 3 retry attempts and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("backendService");

        Supplier<String> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withBulkhead(bulkhead)
                .withRetry(retry)
                .decorate();
    }

    @Test
    public void ddd() throws InterruptedException {

        log.info("aaaa");

        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if ((count >= 10 && count < 12) || (count >= 15 && count < 20)) {
                throw new RuntimeException("err-" + count);
            }
            return "aaa-" + count;
        };

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .permittedNumberOfCallsInHalfOpenState(5)

                // half-open -> open 等待的最长时间。 如果为0， 则下次请求时触发。
                // 注意：有可能会正好要转换状态时，就没有后续请求了
                .maxWaitDurationInHalfOpenState(Duration.ofSeconds(0))

                // true-由额外线程来监控并触发状态转换 : half-open -> open
                // false-当下次请求流入时触发状态转换 : half-open -> open
                .automaticTransitionFromOpenToHalfOpenEnabled(false)

                // 多长时间才从 open (完全拒绝请求） 转换为 half-open （接收少量请求）
                .waitDurationInOpenState(Duration.ofSeconds(1))

                // 特定的错误，不当做异常
                .ignoreException(e -> e instanceof IllegalArgumentException)

                // 特定的错误，才当做异常
                .recordException(e -> e instanceof RuntimeException)


                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                // 至少10个请求才开始计算错误率 （ 有可能 half-open 状态下 permittedNumberOfCallsInHalfOpenState 设置的比较小）
                .minimumNumberOfCalls(10)

                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.custom()
                //.withRegistryStore(new InMemoryRegistryStore())
                .withCircuitBreakerConfig(circuitBreakerConfig)
                .build();

        registry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    CircuitBreaker addedCircuitBreaker = entryAddedEvent.getAddedEntry();
                    System.out.println("CircuitBreaker " + addedCircuitBreaker.getName() + " added");
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    CircuitBreaker removedCircuitBreaker = entryRemovedEvent.getRemovedEntry();
                    System.out.println("CircuitBreaker " + removedCircuitBreaker.getName() + " removed");
                });


        CircuitBreaker circuitBreaker = CircuitBreaker.of("backendService", circuitBreakerConfig);

        circuitBreaker.getEventPublisher()
                .onSuccess(event -> System.out.println("CircuitBreakerEvent:onSuccess: " + Thread.currentThread().getName() + " : " + event))
                .onError(event -> System.out.println("CircuitBreakerEvent:onError: " + Thread.currentThread().getName() + " : " + event))
                .onIgnoredError(event -> System.out.println("CircuitBreakerEvent:onIgnoredError: " + Thread.currentThread().getName() + " : " + event))
                .onReset(event -> System.out.println("CircuitBreakerEvent:onReset: " + Thread.currentThread().getName() + " : " + event))
                .onReset(event -> System.out.println("CircuitBreakerEvent:onReset: " + Thread.currentThread().getName() + " : " + event))
                .onStateTransition(event -> System.out.println("CircuitBreakerEvent:onStateTransition: " + Thread.currentThread().getName() + " : " + event));

        Supplier<String> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .decorate();

        for (int i = 0; i < 32; i++) {
            String result = Try.ofSupplier(decoratedSupplier)
                    .recover(throwable -> "recover : " + Thread.currentThread().getName() + " : " + throwable.getMessage())
                    .get();

            System.out.println("i=" + i + " : " + Thread.currentThread().getName() + " : " + result);

            // 如果没有这个语句，将直接从 CLOSED -> OPEN
            // 有这个语句，将直接从 CLOSED -> OPEN
            if (i == 17) {
                Thread.sleep(3000);
            }
        }
    }

    /**
     * 测试case：允许 2qps, 3秒超时，则提交9个请求，会成功执行 2*(3+1)=8， 超时1个。
     * <p>
     * 注意: 并非匀速，瞬间全部消费, 那只能最小化设计 cycle ？
     * 比如 100 qps, 设计成 (limitForPeriod=1, limitRefreshPeriod= 0ms) ，
     * 而非 (limitForPeriod=100, limitRefreshPeriod=1s)
     *
     * @throws InterruptedException
     */
    @Test
    public void rateLimit01() throws InterruptedException {

        // 允许 qps 2：最长等待3秒，如果3秒后仍未获得令牌，则报错。
        RateLimiterConfig config = RateLimiterConfig.custom()
                // 多久一个循环周期
                .limitRefreshPeriod(Duration.ofSeconds(1))
                // 新的循环周期开始时，给配置多少个允许的请求额度，每请求一个减一
                .limitForPeriod(2)
                // 线程最长等待多久
                .timeoutDuration(Duration.ofSeconds(3))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        RateLimiter rateLimiter = rateLimiterRegistry
                .rateLimiter("name1");

        AtomicLong c = new AtomicLong(0);
        Runnable runnable = () -> {
            long count = c.getAndAdd(1);
            String result = "aaa-" + count;
            System.out.println(getTime() + " : " + getThreadName() + " : " + result);
        };

        CheckedRunnable restrictedCall = RateLimiter
                .decorateCheckedRunnable(rateLimiter, runnable::run);

        for (int i = 0; i < 9; i++) {
            new Thread(() -> {
                Try.run(restrictedCall)
                        .onFailure((err) -> System.out.println(getTime() + " : " + getThreadName() + " : onFailure : " + err.getMessage()));
            })
                    .start();
        }

        Thread.sleep(10 * 1000);
        System.out.println("===================== rand 2");
        for (int i = 0; i < 9; i++) {
            new Thread(() -> {
                Try.run(restrictedCall)
                        .onFailure((err) -> System.out.println(getTime() + " : " + getThreadName() + " : onFailure : " + err.getMessage()));
            })
                    .start();
        }
        Thread.sleep(10 * 1000);

    }

    protected String getTime() {
        return DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    protected String getThreadName() {
        return Thread.currentThread().getName();
    }

    @Test
    public void retry001() {
        RetryConfig config = RetryConfig.custom()

                // 失败的话，做多尝试几次
                .maxAttempts(2)

                // 每次尝试，应该间隔的固定时间
                .waitDuration(Duration.ofMillis(1000))

                // 如果要实现类似反抛物线式的 尝试时间 去尝试（即前几次间隔时间短，后面的尝试时间原来越长）
                // FIXME: 如何持久化保存？比如刚尝试了2次，就重启了。
                //.intervalFunction()

                // 判断怎样的业务结果 应当retry
                .retryOnResult(str -> str instanceof String && ((String) str).contains("success"))

                // 哪些异常应当忽略，不 retry
                .ignoreExceptions(ClassNotFoundException.class)

                // 判断怎样的异常 应当retry
                .retryOnException(e -> e instanceof IllegalArgumentException)

                // 应当retry 的异常列表，推荐 使用 retryOnException
                //.retryExceptions(IOException.class, TimeoutException.class)

                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("name1");


        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if (count == 0) {
                throw new RuntimeException("err-" + count);
            }
            return "aaa-" + count;
        };


        {
            CheckedFunction0<String> retryableSupplier = Retry.decorateCheckedSupplier(retry, () -> "aaa");

            Try<String> result = Try.of(retryableSupplier)
                    .recover((throwable) -> "Hello world from recovery function");

        }

    }


    @Test
    public void timeLimiter01() throws Exception {


        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if (count > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "aaa-" + count;
        };

        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .cancelRunningFuture(true)
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        TimeLimiterRegistry registry = TimeLimiterRegistry.of(config);

        TimeLimiter timeLimiter = registry.timeLimiter("name1");


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        {
            long start = System.currentTimeMillis();
            String result = timeLimiter.executeFutureSupplier(() -> CompletableFuture.supplyAsync(supplier));
            long end = System.currentTimeMillis();
            long cost = end - start;
            System.out.println("1. result = " + result + ",  cost=" + cost);
        }

        {
            long start = System.currentTimeMillis();
            // The non-blocking variant with a CompletableFuture
            CompletableFuture<String> future = timeLimiter.executeCompletionStage(
                            scheduler,
                            () -> CompletableFuture.supplyAsync(supplier)
                    )
                    .toCompletableFuture();

            Future<String> resultFuture = Future.fromCompletableFuture(future)
                    .onSuccess(v -> System.out.println("Successfully Completed - Result: " + v))
                    .onFailure(v -> System.out.println("Failed - Result: " + v))
                    .recover(err -> "recover_result_from-" + err.getMessage());


            resultFuture.onComplete(t -> {

                long end = System.currentTimeMillis();
                long cost = end - start;
                if (t.isSuccess()) {
                    System.out.println("2. |" + getThreadName() + "| result = " + t.get() + ",  cost=" + cost);
                } else {
                    System.out.println("2. |" + getThreadName() + "| error = " + t.getCause().getMessage() + ",  cost=" + cost);
                }


            });

            Thread.sleep(3000);
        }

    }

    /**
     * @see Caching.CachingProviderRegistry#getCachingProviders(java.lang.ClassLoader)
     * @see <a href="https://infinispan.org/tutorials/simple/jcache">Infinispan - JCache</a>
     */
    @Test
    public void cache01() {

        // 初始化好 infinispan
        {
            System.setProperty(Caching.JAVAX_CACHE_CACHING_PROVIDER, "org.infinispan.jcache.embedded.JCachingProvider");
//        System.setProperty(Caching.JAVAX_CACHE_CACHING_PROVIDER, "org.ehcache.jsr107.EhcacheCachingProvider");

            CachingProvider jcacheProvider = Caching.getCachingProvider();
            CacheManager cacheManager = jcacheProvider.getCacheManager();
            MutableConfiguration<String, String> configuration = new MutableConfiguration<>();
            configuration.setTypes(String.class, String.class);
            // create a cache using the supplied configuration
            javax.cache.Cache<String, String> cache = cacheManager.createCache("cacheName", configuration);
//        // Store a value
//        cache.put("key", "value");
//        // Retrieve the value and print it out
//        System.out.printf("key = %s\n", cache.get("key"));
//        // Stop the cache manager and release all resources
//        cacheManager.close();

        }

        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            return "aaa-" + count;
        };

        javax.cache.Cache<String, String> cacheInstance = Caching.getCache("cacheName", String.class, String.class);
        Cache<String, String> cacheContext = Cache.of(cacheInstance);

        // Decorate your call to BackendService.doSomething()
        CheckedFunction1<String, String> cachedFunction = Decorators
                .ofCheckedSupplier(supplier::get)
                .withCache(cacheContext)
                .decorate();

        {
            String result = Try.of(() -> cachedFunction.apply("cacheKey")).get();
            System.out.println("1. result = " + result);
        }
        {
            org.infinispan.jcache.embedded.JCachingProvider cd;
            String result = Try.of(() -> cachedFunction.apply("cacheKey")).get();
            System.out.println("2. result = " + result);
        }

    }
}
