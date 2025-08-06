package me.test.jdk.java.util.concurrent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
@Slf4j
public class ThreadPoolExecutorTest {

    @Test
    public void testReject() throws InterruptedException {

        ThreadFactory threadFactory = new ThreadFactory() {
            final AtomicInteger threadNum = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("async-decision-service-thread" + threadNum.getAndIncrement());
                return thread;
            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2,
                1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2),
                threadFactory,
                // 使用CallerRunsPolicy作为拒绝策略，当线程池队列满时，主线程将负责实际的任务执行，确保任务不会被丢弃；
                // 理论上，在线程池队列满的情况下，实际的执行性能最差也能退化成原来非异步时的执行性能
                new ThreadPoolExecutor.CallerRunsPolicy() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(Thread.currentThread().getName() + " : ===rejectedExecution");
                        super.rejectedExecution(r, executor);
                    }
                }
        );


        for (int i = 0; i < 10; i++) {

            int jobId = i;
            executor.submit(() -> {
                //System.out.println(Thread.currentThread().getName() + ": i=" + jobId + " : start");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": i=" + jobId + " : end");
            });
        }

        Thread.sleep(10000);
        System.out.println("ALL END");
    }


    @Test
    public void testWithCountDownLatch01() throws InterruptedException {

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("my-job-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 3,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                factory
        );

        long startTime = System.currentTimeMillis();
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int jobId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": jobId=" + jobId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await(10, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();
        long totalCost = endTime - startTime;
        System.out.println("ALL END, totalCost=" + totalCost);
    }

    public void logExecutorStatus(ThreadPoolExecutor executor, String tag) {
        log.info("check executor status [{}] : isShutdown={}, isTerminating={},isTerminated={}",
                tag,
                executor.isShutdown(),
                executor.isTerminating(),
                executor.isTerminated()
        );
    }

    /**
     * 如果使用 CallerRunsPolicy， executor shutdown 后再submit 会触发 直接丢弃任务。
     */

    @Test
    public void testSubmitAfterShutdownWithDefaultCallerRunsPolicy() throws InterruptedException {
        MyJob job1 = new MyJob("1");
        MyJob job2 = new MyJob("2");

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("my-job-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 3,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                factory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        logExecutorStatus(executor, "AAA");
        executor.submit(job1);
        logExecutorStatus(executor, "BBB");

        Thread.sleep(1000);
        Assertions.assertTrue(job1.isRuned());
        executor.shutdown();
        logExecutorStatus(executor, "CCC");
        executor.submit(job2);

        logExecutorStatus(executor, "DDD");

        Thread.sleep(1000);
        Assertions.assertFalse(job2.isRuned());
        System.out.println("====Done.");
    }

    /**
     * 如果使用默认的 AbortPolicy， executor shutdown 后再submit 会触发 RejectedExecutionException 异常
     *
     * @see ThreadPoolExecutor.AbortPolicy
     */
    @Test
    public void testSubmitAfterShutdownWithDefaultAbortPolicy() throws InterruptedException {
        MyJob job1 = new MyJob("1");
        MyJob job2 = new MyJob("2");

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("my-job-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 3,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                factory
        );
        logExecutorStatus(executor, "AAA");
        executor.submit(job1);
        logExecutorStatus(executor, "BBB");

        Thread.sleep(1000);
        Assertions.assertTrue(job1.isRuned());

        executor.shutdown();
        logExecutorStatus(executor, "CCC");
        try {
            executor.submit(job2);
            Assertions.fail("shutdown后，应当拒绝提交新任务");
        } catch (RejectedExecutionException e) {
            log.error(e.getMessage(), e);
        }
        logExecutorStatus(executor, "DDD");

        Thread.sleep(1000);
        Assertions.assertFalse(job2.isRuned());
        System.out.println("====Done.");
    }

    public static class MyJob implements Runnable {

        String id;
        @Getter
        boolean runed;

        public MyJob(String id) {
            this.id = id;
        }


        @Override
        public void run() {
            log.info("===== JOB_RUNNING, id={}", id);
            this.runed = true;
        }

    }
}
