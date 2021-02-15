package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-05-24
 */
public class TakeVsFirst01 {

    Logger logger = LoggerFactory.getLogger(TakeVsFirst01.class);


    @Test
    public void test() {
        Flowable.empty()
                .take(1)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );

        Flowable.empty()
                .limit(1)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );
    }
}
