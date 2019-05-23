package me.test.rxjava.flowable;

import io.reactivex.Flowable;
import org.junit.Test;
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
 *
 * @see <a href-"https://www.cnblogs.com/assassin-l/p/5101549.html">递归实现</a>
 */
public class Recursion01 {

    Logger logger = LoggerFactory.getLogger(Recursion01.class);
    private Map<String, List<String>> dataMap = new HashMap<>(4);
    private AtomicInteger counter = new AtomicInteger(0);

    public Recursion01() {
        dataMap.put("1000", Arrays.asList("1100", "1200"));
        dataMap.put("1100", Arrays.asList("1110", "1120", "1130"));
        dataMap.put("1200", Arrays.asList("1210", "1220"));

        dataMap.put("1130", Arrays.asList("1131"));
        dataMap.put("1220", Arrays.asList("1221", "1222"));
    }

    /**
     * 深度优先
     */
    @Test
    public void test01() {
        getSelfAndChild("1000")
                .subscribe(
                        i -> logger.debug("subscribe : onNext : {} : {}", counter.getAndAdd(1), i),
                        err -> logger.error("subscribe : onError", err),
                        () -> logger.debug("subscribe : onComplete")
                );
    }

    Flowable<String> getSelfAndChild(String cur) {

        // 连接自己 和 子元素
        return Flowable.just(cur)
                .concatWith(getChild(cur).concatMap(this::getSelfAndChild));

    }

    Flowable<String> getChild(String cur) {
        List<String> childrenList = dataMap.get(cur);
        if (childrenList == null || childrenList.isEmpty()) {
            return Flowable.empty();
        }
        return Flowable.fromIterable(childrenList);
    }


}
