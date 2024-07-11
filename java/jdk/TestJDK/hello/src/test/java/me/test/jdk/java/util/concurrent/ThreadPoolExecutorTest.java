package me.test.jdk.java.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
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
}
