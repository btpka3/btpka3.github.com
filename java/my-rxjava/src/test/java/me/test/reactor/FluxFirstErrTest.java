package me.test.reactor;

import me.test.rxjava.U;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class FluxFirstErrTest {

    @Test
    public void allSuccess() {
        U.print("allSuccess", "start");
        List<CompletableFuture<String>> futureList = Arrays.asList(
                CompletableFuture.supplyAsync(new MyTask("data000", false, 3000)),
                CompletableFuture.supplyAsync(new MyTask("data001", false, 2000)),
                CompletableFuture.supplyAsync(new MyTask("data002", false, 1000))
        );
        AtomicReference<Throwable> errRef = new AtomicReference<>();
        Object result = Flux.fromIterable(futureList)
                .flatMap(Mono::fromFuture)
                .doOnError(errRef::set)
                .onErrorComplete()
                .blockLast();
        U.print("allSuccess", "done");
        Assertions.assertEquals("data000", result);
        Assertions.assertNull(errRef.get());
    }

    @Test
    public void allErr() {
        U.print("allErr", "start");
        List<CompletableFuture<String>> futureList = Arrays.asList(
                CompletableFuture.supplyAsync(new MyTask("data000", true, 3000)),
                CompletableFuture.supplyAsync(new MyTask("data001", true, 2000)),
                CompletableFuture.supplyAsync(new MyTask("data002", true, 1000))
        );
        AtomicReference<Throwable> errRef = new AtomicReference<>();
        Object result = Flux.fromIterable(futureList)
                .flatMap(Mono::fromFuture)
                .doOnError(errRef::set)
                .onErrorComplete()
                .blockLast();
        U.print("allErr", "done");
        Assertions.assertNull(result);
        Assertions.assertNotNull(errRef.get());
        // 第一个失败的case
        Assertions.assertTrue(errRef.get().getMessage().contains("DEMO_ERR:data002"));
    }

    @Test
    public void firstErr() {
        U.print("firstErr", "start");
        List<CompletableFuture<String>> futureList = Arrays.asList(
                CompletableFuture.supplyAsync(new MyTask("data000", false, 3000)),
                CompletableFuture.supplyAsync(new MyTask("data001", true, 2000)),
                CompletableFuture.supplyAsync(new MyTask("data002", false, 1000))
        );
        AtomicReference<Throwable> errRef = new AtomicReference<>();
        Object result = Flux.fromIterable(futureList)
                .flatMap(Mono::fromFuture)
                .doOnError(errRef::set)
                .onErrorComplete()
                .blockLast();
        U.print("firstErr", "done");
        // 失败前最后一个成功的结果
        Assertions.assertEquals("data002", result);
        Assertions.assertNotNull(errRef.get());
        Assertions.assertTrue(errRef.get().getMessage().contains("DEMO_ERR:data001"));

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
            U.print("mixErrAndSuccess", "err");
            if (this.throwErr) {
                throw new RuntimeException("DEMO_ERR:" + data);
            }
            return data;
        }
    }
}
