package me.test.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author dangqian.zll
 * @date 2020-08-03
 */
public class TimeoutTest {

    Logger log = LoggerFactory.getLogger(TimeoutTest.class);

    @Test
    public void test01() throws InterruptedException {
        Mono.just("aaa")
                .map(s -> {
                    log.info("data={}", s);
                    try {
                        Thread.sleep(6 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "bbb";
                })

                .timeout(Duration.ofSeconds(3))
                .subscribe(
                        i -> log.debug("subscribe : onNext : {}", i),
                        err -> log.error("subscribe : onError", err),
                        () -> log.debug("subscribe : onComplete")
                );

        Thread.sleep(10000);
    }

}
