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
public class OnErrorMap01 {

    Logger log = LoggerFactory.getLogger(OnErrorMap01.class);

    @Test
    public void test01() {
        Flux.just("a", "b", "c")
                .flatMap(s -> {
                    if (Objects.equals(s, "b")) {
                        return Mono.error(new RuntimeException("MockError"));
                    } else {
                        return Flux.just(s + "1", s + "2");
                    }
                })
                .onErrorMap(
                        (err) -> {
                            return new RuntimeException("!!!!@@@" + err.getMessage(), err);
                        }
                )
                .log()
                .subscribe(
                        d -> log.info("Date : {}", d),
                        e -> log.error(e.getMessage(), e),
                        () -> log.info("Done.")
                );

    }
}
