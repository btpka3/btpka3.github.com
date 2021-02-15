package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-07-02
 */
public class CartesianProductTest {

    Logger logger = LoggerFactory.getLogger(CartesianProductTest.class);

    @Test
    public void test01() {
        Flowable<String> f1 = Flowable.fromArray("11", "22", "33");
        Flowable<String> f2 = Flowable.fromArray("aa", "bb", "cc");

        f1.flatMap(str1 -> f2.map(str2 -> Pair.of(str1, str2)))
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );
    }
}
