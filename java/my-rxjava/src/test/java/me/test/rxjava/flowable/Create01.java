package me.test.rxjava.flowable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Flowable;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2019-05-22
 */

public class Create01 {

    Logger logger = LoggerFactory.getLogger(Create01.class);


    /**
     * 模拟数据库分页查询
     */


    public static class S {
        Logger logger = LoggerFactory.getLogger(S.class);

        int pageSize = 10;
        int nextPageNum = 0;
        boolean loaded = false;
        List<Integer> list;
        int nextListIndex = 0;
        int loadCount = 0;


        Integer next() {


            // 模拟加载完了
            if (!loaded) {

                list = loadNextPageList(nextPageNum, pageSize);
                nextListIndex = 0;
                nextPageNum++;
                loaded = true;

                if (list == null || list.isEmpty()) {
                    return null;
                }
            }

            Integer nextItem = list.get(nextListIndex);
            nextListIndex++;
            if (nextListIndex >= list.size()) {
                loaded = false;
            }
            return nextItem;
        }

        public List<Integer> loadNextPageList(int pageNum, int pageSize) {

            if (loadCount >= 7) {
                return null;
            }

            List<Integer> list = new ArrayList<>(pageSize);
            for (int i = 0; i < pageSize; i++) {
                list.add(RandomUtils.nextInt(0, 50));
            }

            logger.info("------- loading [{}]: {}", pageNum, list);

            loadCount++;


            return list;
        }
    }

    @Test
    public void test01() {
        logger.info("test01 started");

        Flowable.generate(
                () -> new S(),
                (S s, Emitter<Integer> emitter) -> {
                    try {
                        Integer i = s.next();
                        if (i == null) {
                            emitter.onComplete();
                        }
                        emitter.onNext(i);
                    } catch (Throwable err) {
                        emitter.onError(err);
                    }
                }
        )
                .filter(i -> i % 3 == 0)
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {}", i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                )

        ;
    }
}
