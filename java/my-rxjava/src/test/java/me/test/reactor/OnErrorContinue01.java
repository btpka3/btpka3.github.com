package me.test.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @date 2018-11-03
 */
public class OnErrorContinue01 {

    Logger log = LoggerFactory.getLogger(OnErrorContinue01.class);

    @Test
    public void test01() {
        Flux.just("a", "b", "c")
                .flatMap(s -> {
                    if (Objects.equals(s, "b")) {
                        return Mono.error(new RuntimeException());
                    } else {
                        return Flux.just(s + "1", s + "2");
                    }
                })
                .onErrorContinue((err, data) -> {
                    System.out.println("~~~~ err=" + err + ", data = " + data);
                })
                .log()
                .subscribe(
                        d -> log.info("Date : {}", d),
                        e -> log.error(e.getMessage(), e),
                        () -> log.info("Done.")
                );

    }
}
