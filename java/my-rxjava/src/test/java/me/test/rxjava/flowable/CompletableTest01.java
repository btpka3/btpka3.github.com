package me.test.rxjava.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2019-08-11
 * @see CompletableOnSubscribe
 * @see CompletableSource
 */

public class CompletableTest01 {
    Logger logger = LoggerFactory.getLogger(CompletableTest01.class);
    SecureRandom r = SecureRandom.getInstanceStrong();

    public CompletableTest01() throws NoSuchAlgorithmException {
    }


    @Test
    public void test01() {


        Map<String, String> m = new LinkedHashMap<>(8);


        Flowable<String> f = Flowable.fromArray("aa", "bb", "cc");


        Completable ccc = f.count()
                .flatMapCompletable(v -> {
                    m.put("count", Long.toString(v));
                    return Completable.complete();
                });

        Completable cc2 = f
                .flatMapCompletable(v -> {
                    logger.debug("subscribe : onNext : start : {}", v);

                    // sleep  1~3 秒
                    Thread.sleep(1000 + r.nextInt(2000));

                    m.put(v, v + "_01");
                    logger.debug("subscribe : onNext : end   : {}", v);
//                    if ("cc".equals(v)) {
//                        throw new RuntimeException("cc error");
//                    }
                    return Completable.complete();
                });

        Completable c = Completable.mergeArrayDelayError(ccc, cc2);
        c.blockingAwait();

        System.out.println(m);
    }
}
