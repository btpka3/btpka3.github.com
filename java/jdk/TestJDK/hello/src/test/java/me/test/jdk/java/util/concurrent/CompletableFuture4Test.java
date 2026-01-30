package me.test.jdk.java.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import me.test.U;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 异常处理
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class CompletableFuture4Test {

    @Test
    public void complete01() throws ExecutionException, InterruptedException {

        CompletableFuture f = new CompletableFuture();
        Assertions.assertFalse(f.isDone());
        Assertions.assertFalse(f.isCancelled());
        Assertions.assertFalse(f.isCompletedExceptionally());

        boolean triggered = f.complete(null);
        Assertions.assertTrue(triggered);

        Assertions.assertTrue(f.isDone());
        Assertions.assertFalse(f.isCancelled());
        Assertions.assertFalse(f.isCompletedExceptionally());

        // 多次 complete 不会生效，以第一次complete的为准。
        triggered = f.complete("aaa");
        Assertions.assertFalse(triggered);

        Object result = f.get();
        Assertions.assertNull(result);
    }

    /**
     * 单个：正常case
     */
    @Test
    public void ok01() throws ExecutionException, InterruptedException {
        U.print("ok01", "start");
        String result = CompletableFuture
                .supplyAsync(new MyTask("aaa", false, 3000))
                .get();
        U.print("ok01", "end");
        Assertions.assertEquals("aaa", result);
    }

    @Test
    public void thenCompose01() throws ExecutionException, InterruptedException {
        U.print("thenCompose01", "start");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(new MyTask("aaa", false, 1000));
        CompletableFuture<String> f2 = f.thenCompose(s -> CompletableFuture.completedFuture(s + " bbb"));
        String result = f2.get();
        U.print("thenCompose01", "end");
        Assertions.assertEquals("aaa bbb", result);
    }

    @Test
    public void thenApply01() throws ExecutionException, InterruptedException {
        U.print("thenApply01", "start");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(new MyTask("aaa", false, 1000));
        CompletableFuture<String> f2 = f.thenApply(s -> s + "bbb");
        String result = f2.get();
        U.print("thenApply01", "end");
        Assertions.assertEquals("aaa bbb", result);
    }

    @Test
    public void exceptionally01() throws ExecutionException, InterruptedException {
        U.print("exceptionally01", "start");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(new MyTask("aaa", true, 1000));
        CompletableFuture<String> f2 = f.exceptionally(s -> "bbb");
        String result = f2.get();
        U.print("exceptionally01", "end");
        Assertions.assertEquals("bbb", result);
    }

    @Test
    public void exceptionallyCompose01() throws ExecutionException, InterruptedException {
        U.print("exceptionallyCompose01", "start");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(new MyTask("aaa", true, 1000));
        CompletableFuture<String> f2 = f.exceptionallyCompose(e -> CompletableFuture.completedStage("bbb"));
        String result = f2.get();
        U.print("exceptionallyCompose01", "end");
        Assertions.assertEquals("bbb", result);
    }

    @Test
    public void exceptionallyCompose02() throws ExecutionException, InterruptedException {
        U.print("exceptionallyCompose01", "start");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(new MyTask("aaa", false, 1000));
        CompletableFuture<String> f2 = f.exceptionallyCompose(e -> CompletableFuture.completedStage("bbb"));
        String result = f2.get();
        U.print("exceptionallyCompose01", "end");
        Assertions.assertEquals("aaa", result);
    }

    /**
     * 单个：异常case
     */
    @Test
    public void err01() throws ExecutionException, InterruptedException {
        U.print("err01", "start");
        try {
            String result = CompletableFuture
                    .supplyAsync(new MyTask("err001", true, 3000))
                    .get();
            Assertions.fail();
        } catch (Throwable e) {
            U.print("err01", "err", e);
            Assertions.assertTrue(e.getMessage().contains("err001"));
        }
    }

    /**
     * 多个：正常case
     */
    @Test
    public void ok02() throws ExecutionException, InterruptedException {
        U.print("ok02", "start");
        CompletableFuture<Void> all = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(new MyTask("data001", false, 3000)),
                CompletableFuture.supplyAsync(new MyTask("data002", false, 2000)),
                CompletableFuture.supplyAsync(new MyTask("data003", false, 1000))
        );
        Object result = all.get();
        U.print("ok02", "end : " + result);
    }

    /**
     * 多个：ALL: 正常+异常，总耗时=最长的，有任何一个异常就异常。
     */
    @Test
    public void allMixErrAndOk() throws ExecutionException, InterruptedException {
        U.print("allMixErrAndOk", "start");
        try {
            CompletableFuture<Void> all = CompletableFuture.allOf(
                    CompletableFuture.supplyAsync(new MyTask("data001", false, 3000)),
                    CompletableFuture.supplyAsync(new MyTask("data002", true, 2000)),
                    CompletableFuture.supplyAsync(new MyTask("data003", false, 1000))
            );
            all.get();

            Assertions.fail();
        } catch (Throwable e) {
            // ⭕️: 注意: 这里是所有任务都执行完了，即耗时为最长的3秒。
            U.print("allMixErrAndOk", "err", e);
            Assertions.assertTrue(e.getMessage().contains("data002"));
        }
    }


    /**
     * 多个：ALL: 正常+异常，总耗时=最长的，有任何一个异常就异常。
     */
    @Test
    public void allMixErrAndOk1() throws ExecutionException, InterruptedException {
        U.print("allMixErrAndOk", "start");
        CompletableFuture<String>[] tasks = new CompletableFuture[]{
                CompletableFuture.supplyAsync(new MyTask("data001", false, 3000)),
                CompletableFuture.supplyAsync(new MyTask("data002", true, 2000)),
                CompletableFuture.supplyAsync(new MyTask("data003", false, 1000))
        };
        try {
            CompletableFuture<Void> all = CompletableFuture.allOf(tasks);

            for (CompletableFuture<String> task : tasks) {
                task.whenComplete((r, e) -> {
                    if (e != null) {
                        all.completeExceptionally(e);
                    }
                });
            }
            all.get();
            Assertions.fail();
        } catch (Throwable e) {
            // ⭕️： 这里应该是两秒后打印
            U.print("allMixErrAndOk", "err", e);
            Assertions.assertTrue(e.getMessage().contains("data002"));
            Assertions.assertFalse(tasks[0].isDone());
            Assertions.assertTrue(tasks[1].isCompletedExceptionally());
            Assertions.assertTrue(tasks[2].isDone());
        }
    }


    public static class MyTask implements Supplier<String> {
        private String data;
        private boolean err;
        private long sleepMillis = 0;

        public MyTask(String data, boolean err, long sleepMillis) {
            this.data = data;
            this.err = err;
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
            if (this.err) {
                throw new RuntimeException(data);
            }
            return data;
        }
    }


}
