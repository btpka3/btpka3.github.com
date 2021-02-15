package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;


public class Window01 {

    Logger logger = LoggerFactory.getLogger(Window01.class);


    @Test
    public void test01() {
        logger.info("test01 started");

        Flowable.fromArray("aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj")
                // 1秒处理2个
                .window(1, TimeUnit.SECONDS, 2)
                //.flatMap(f->f)
                //.flatMap(Function::identity)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                )

        ;
    }
}
