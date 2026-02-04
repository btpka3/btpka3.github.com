package me.test.com.google.common.util.concurrent;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/8/6
 */
@Slf4j
public class ListeningExecutorServiceTest {
    public void logExecutorStatus(ListeningExecutorService executor, String tag) {
        log.info(
                "check executor status [{}] : isShutdown={}, isTerminated={}",
                tag,
                executor.isShutdown(),
                executor.isTerminated());
    }

    /**
     * 如果使用 CallerRunsPolicy， executor shutdown 后再submit 会触发 直接丢弃任务。
     */
    @SneakyThrows
    @Test
    public void testSubmitAfterShutdown() {
        MyJob job1 = new MyJob("1");
        MyJob job2 = new MyJob("2");

        ThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("my-job-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(
                16,
                128,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()));
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
