package me.test.rxjava.flowable;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timeout01 {

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