package me.test.rxjava.flowable;

import io.reactivex.Maybe;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-05-24
 */
public class Maybe01 {

    Logger logger = LoggerFactory.getLogger(Maybe01.class);


    @Test
    public void test01() {
        Object result = Maybe.empty()
                .blockingGet();
        logger.info("result = " + result);
    }
}
