package me.test.reactor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

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
                    log.info("data={}", s)
                    Thread.sleep(6 * 1000);
                    return s + "bbb";
                })

                .timeout(3, TimeUnit.SECOND)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );

        Thread.sleep(10000);
    }

}
