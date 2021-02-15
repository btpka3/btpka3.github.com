package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-05-22
 */

public class Distinct01 {

    Logger logger = LoggerFactory.getLogger(Distinct01.class);


    // 自定义 distinct: 百位数值相同的就认为是相同的

    @Test
    public void test01() {
        logger.info("test01 started"+(111/100)+","+(151/100));

        Flowable.fromArray(103, 209, 101, 204, 203, 106, 333, 311, 321)
                .distinct((i) -> i / 100)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                )

        ;
    }
}
