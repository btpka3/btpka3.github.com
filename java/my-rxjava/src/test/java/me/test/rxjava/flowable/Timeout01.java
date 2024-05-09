package me.test.rxjava.flowable;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Timeout01 {

    Logger log = LoggerFactory.getLogger(Timeout01.class);

    @Test
    public void test01() {

        Flowable.just("aaa")

                .map(s -> {
                    log.info("data={}", s);
                    Thread.sleep(6 * 1000);
                    return s + "bbb";
                })

                .timeout(3, TimeUnit.SECONDS)
                .subscribe(
                        i -> log.debug("subscribe : onNext : {}", i),
                        err -> log.error("subscribe : onError", err),
                        () -> log.debug("subscribe : onComplete")
                );


    }


}