package me.test.rxjava.flowable;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2019-05-24
 */
public class Recursion02 {

    Logger logger = LoggerFactory.getLogger(Recursion02.class);
    private Map<String, List<String>> dataMap = new HashMap<>(4);
    private AtomicInteger counter = new AtomicInteger(0);

    public Recursion02() {
        dataMap.put("1000", Arrays.asList("1100", "1200"));
        dataMap.put("1100", Arrays.asList("1110", "1120", "1130"));
        dataMap.put("1200", Arrays.asList("1210", "1220"));

        dataMap.put("1130", Arrays.asList("1131"));
        dataMap.put("1220", Arrays.asList("1221", "1222"));
    }


    /**
     * 广度优先
     */
    @Test
    public void test02() {
        getSelfAndChild("1000")
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {} : {}", counter.getAndAdd(1), i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );
    }
    //

    Flowable<String> getSelfAndChild(String cur) {

        return getAll(Flowable.just(cur), Flowable.just(cur));

    }

    Flowable<String> getAll(
            Flowable<String> all,
            Flowable<String> n
    ) {
        // 下一层级的数据
        Flowable<String> n1 = n.flatMap(this::getChild);
        return n1.isEmpty()
                .flatMapPublisher(isEmpty -> isEmpty ? all : getAll(all.concatWith(n1), n1));
    }


    /**
     * 获取给定目标 的下级元素
     *
     * @param cur
     * @return
     */
    Flowable<String> getChild(String cur) {
        List<String> childrenList = dataMap.get(cur);
        if (childrenList == null || childrenList.isEmpty()) {
            return Flowable.empty();
        }
        return Flowable.fromIterable(childrenList);
    }


}
