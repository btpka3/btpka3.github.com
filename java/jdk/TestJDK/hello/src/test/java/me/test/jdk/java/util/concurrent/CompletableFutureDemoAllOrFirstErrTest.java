package me.test.jdk.java.util.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * 演示：对多个 CompletableFuture ，fast failed
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class CompletableFutureDemoAllOrFirstErrTest {

    // -------------------------- 工具

    /**
     * 遇到第一个报错就立即返回，或者等待所有的任务都成功完成。
     */
    @SafeVarargs
    public static CompletableFuture<Void> allOrFirstErr(
            CompletableFuture<String>... futures
    ) {
        CompletableFuture<Void> all = CompletableFuture.allOf(futures);
        for (CompletableFuture<String> f : futures) {
            f.whenComplete((result, e) -> {
                // 这里通过是否有异常来判定是否执行报错。
                // 也可以根据正常返回值中的状态来判定是否执行报错
                if (e != null) {
                    // TIP: 重复 complete 不会生效，始终以第一次complete的为准。
                    all.completeExceptionally(e);
                }
            });
        }
        return all;
    }

    // -------------------------- 测试case

    @Test
    public void allSuccess() throws ExecutionException, InterruptedException {
        log("allSuccess", "start", null);
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(new MyTask("data000", false, 3000));
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new MyTask("data001", false, 2000));
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new MyTask("data002", false, 1000));

        CompletableFuture<Void> all = allOrFirstErr(f0, f1, f2);
        all.get();
        log("allSuccess", "end", null);

        Assertions.assertTrue(f0.isDone());
        Assertions.assertEquals("data000", f0.get());

        Assertions.assertTrue(f1.isDone());
        Assertions.assertEquals("data001", f1.get());

        Assertions.assertTrue(f2.isDone());
        Assertions.assertEquals("data002", f2.get());
    }

    @Test
    public void allErr() throws ExecutionException, InterruptedException {
        log("allErr", "start", null);
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(new MyTask("data000", true, 3000));
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new MyTask("data001", true, 2000));
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new MyTask("data002", true, 1000));
        try {
            CompletableFuture<Void> all = allOrFirstErr(f0, f1, f2);
            all.get();
            Assertions.fail();
        } catch (Throwable e) {
            log("allErr", "err", e);
            // f2 最先失败
            Assertions.assertTrue(e.getMessage().contains("DEMO_ERR:data002"));

            Assertions.assertFalse(f0.isDone());
            Assertions.assertFalse(f1.isDone());
            Assertions.assertTrue(f2.isCompletedExceptionally());
        }
    }

    @Test
    public void mixErrAndSuccess() throws ExecutionException, InterruptedException {
        log("mixErrAndSuccess", "start", null);
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(new MyTask("data000", false, 3000));
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new MyTask("data001", true, 2000));
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new MyTask("data002", false, 1000));
        try {
            CompletableFuture<Void> all = allOrFirstErr(f0, f1, f2);
            all.get();
            Assertions.fail();
        } catch (Throwable e) {
            log("mixErrAndSuccess", "err", e);
            // f1 最先失败
            Assertions.assertTrue(e.getMessage().contains("DEMO_ERR:data001"));

            Assertions.assertFalse(f0.isDone());

            Assertions.assertTrue(f1.isCompletedExceptionally());

            Assertions.assertTrue(f2.isDone());
            Assertions.assertEquals("data002", f2.get());
        }
    }


    // -------------------------- 测试用

    public static void log(String step, Object data, Throwable e) {
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.printf("[%s][%4d - %30s] - %10s : %s%n",
                zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );
        if (e != null) {
            e.printStackTrace();
        }
    }

    public static class MyTask implements Supplier<String> {
        private String data;
        private boolean throwErr;
        private long sleepMillis = 0;

        public MyTask(String data, boolean throwErr, long sleepMillis) {
            this.data = data;
            this.throwErr = throwErr;
            this.sleepMillis = sleepMillis;
        }

        @Override
        public String get() {
            if (sleepMillis > 0) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (this.throwErr) {
                throw new RuntimeException("DEMO_ERR:" + data);
            }
            return data;
        }
    }
}
