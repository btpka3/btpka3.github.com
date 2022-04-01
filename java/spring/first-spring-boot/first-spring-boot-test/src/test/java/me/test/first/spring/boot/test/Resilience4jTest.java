package me.test.first.spring.boot.test;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * bulkhead : 用于控制程序的并发执行量，比如：有容量很大的线程池给所有用户执行任务，但每个用户只允许最多4个任务同时执行。
 *
 * @author dangqian.zll
 * @date 2020/11/1
 * @see <a href="https://resilience4j.readme.io/docs">Resilience4j user guide</a>
 */
@Slf4j
public class Resilience4jTest {

    @Test
    public void x() {

        AtomicLong c = new AtomicLong(0);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("name");

        Retry retry = Retry.ofDefaults("backendName");

        // Decorate your call to BackendService.doSomething() with a CircuitBreaker
        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    long count = c.getAndAdd(1);
                    if (count < 5) {
                        throw new RuntimeException("err-" + count);
                    }
                    return "aaa";
                });

        // Decorate your call with automatic retry
        decoratedSupplier = Retry.decorateSupplier(retry, decoratedSupplier);

        // Execute the decorated supplier and recover from any exception
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery : " + throwable.getMessage()).get();
        System.out.println("result = " + result);
    }

    protected String errToResult(Throwable e) {
        return "bbb";
    }


    @Test
    public void y() {

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

        // Create a Retry with default configuration
        // 3 retry attempts and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("backendService");

        // Create a Bulkhead with default configuration
        Bulkhead bulkhead = Bulkhead.ofDefaults("backendService");

        // Decorate your call to backendService.doSomething()
        // with a Bulkhead, CircuitBreaker and Retry
        // **note: you will need the resilience4j-all dependency for this
        Supplier<String> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withBulkhead(bulkhead)
                .withRetry(retry)
                .decorate();

        // Execute the decorated supplier and recover from any exception
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();
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
     * 注意: 并非匀速，瞬间全部消费, 那只能最小化设计 cycle ？
     * 比如 100 qps, 设计成 limitForPeriod =1 + limitRefreshPeriod = 10ms ，
     * 而非 limitForPeriod =100 + limitRefreshPeriod = 1s
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
