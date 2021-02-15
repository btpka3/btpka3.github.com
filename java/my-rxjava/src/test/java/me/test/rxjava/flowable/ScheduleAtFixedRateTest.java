package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author dangqian.zll
 * @date 2019-07-01
 */
public class ScheduleAtFixedRateTest {

    Logger logger = LoggerFactory.getLogger(ScheduleAtFixedRateTest.class);

    @Test
    public void test01() {
        logger.info("test01 started");

//        Subscription subscription = Flowable.interval(1,TimeUnit.SECONDS)
//                .subscribe();

        Flowable.fromArray("aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj")
                .observeOn(Schedulers.trampoline())
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
