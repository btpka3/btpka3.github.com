package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.comparator.Comparators;

/**
 * @author dangqian.zll
 * @date 2019-05-22
 */

public class GroupMax01 {

    Logger logger = LoggerFactory.getLogger(GroupMax01.class);


    // 自定义 distinct: 百位数值相同的就认为是相同的

    @Test
    public void test01() {
        logger.info("test01 started" + (111 / 100) + "," + (151 / 100));

        Flowable.fromArray(103, 204, 101, 209, 203, 106, 333, 311, 321)
                .groupBy((i) -> i / 100)
                .flatMap(f -> {  // GroupedObservable<Integer, List<Integer>>


                    return f.sorted(Comparators.comparable().reversed())
                            .limit(1);

                })
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                )
        ;
    }
}
