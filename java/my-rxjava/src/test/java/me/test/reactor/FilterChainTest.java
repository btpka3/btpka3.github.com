package me.test.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @date 2020-01-01
 */

public class FilterChainTest {

    Logger log = LoggerFactory.getLogger(FilterChainTest.class);

//    @Test
//    public static <T> void print(FluxSink<T> fluxSink) {
//
//        Floab
//
//        System.out.println(" currentContext=" + fluxSink.currentContext()
//                + ", isCancelled=" + fluxSink.isCancelled()
//                + ", requestedFromDownstream=" + fluxSink.requestedFromDownstream()
//        );
//    }

    Optional<String> sync01() {
        log.info("check01 invoked");
        return Optional.of("aaa");
    }

    Optional<String> sync02() {
        log.info("check02 invoked");
        return Optional.empty();
    }

    void callback01(Consumer<String> c) {
        new Thread(() -> {
            log.info("callback01 invoked");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            c.accept("bbb");
        }).start();
    }


    @Test
    public void test01() throws InterruptedException {
        log.info("aaa");
        List<Callable<CompletableFuture<String>>> checkerList = Arrays.asList(
                () -> {
                    CompletableFuture<String> f = new CompletableFuture<>();
//                    f.complete(sync02().get());
                    f.complete(null);
                    return f;
                },
                () -> {
                    CompletableFuture<String> f = new CompletableFuture<>();
                    callback01(f::complete);
                    return f;
                }
        );
        Flux.fromIterable(checkerList)
                .map(c -> {
                    try {
                        return c.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Mono::fromFuture)
                .concatMap(Mono::flux)
                .take(1)
                .subscribe(
                        (result) -> log.error("success , result={}", result),
                        err -> log.error("Error occurred", err),
                        () -> log.info("Done.")
                );

        Thread.sleep(3000);

    }


}
