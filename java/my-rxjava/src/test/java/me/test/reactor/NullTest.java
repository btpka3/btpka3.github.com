package me.test.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author dangqian.zll
 * @date 2020-01-02
 */
public class NullTest {

    Logger log = LoggerFactory.getLogger(NullTest.class);

    @Test
    public void test01() throws InterruptedException {
        // 构建时，有参数检查
        Flux.just("a", "b", null, "d")
                .subscribe(
                        (result) -> log.info("success, result={}", result),
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                );
        Thread.sleep(1000);
    }

    @Test
    public void test02() throws InterruptedException {

        // java.lang.NullPointerException: The mapper returned a null value.
        Flux.<Callable<String>>just(() -> "a", () -> "b", () -> null, () -> "d")
                .map(c -> {
                    try {
                        return c.call();
                    } catch (Exception e) {
                        return new RuntimeException(e);
                    }
                })
                .subscribe(
                        (result) -> log.info("success, result={}", result),
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                );
        Thread.sleep(1000);
    }

    @Test
    public void monoFromFuture01() throws InterruptedException {

        // java.lang.NullPointerException: future
        Mono.fromFuture((CompletableFuture) null)
                .subscribe(
                        (result) -> log.info("success, result={}", result),
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                );
        Thread.sleep(1000);
    }


    @Test
    public void monoFromFuture02() throws InterruptedException {

        CompletableFuture<String> future = new CompletableFuture<String>();
        new Thread((() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            future.complete("abc");
        })).start();

        Mono.fromFuture(future)
                .subscribe(
                        (result) -> log.info("success, result={}", result),
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                );
        Thread.sleep(3000);
    }


}
