package me.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Iteratable2Flux {

    @Test
    public void test01() {

        List<String> result = getPageData(Arrays.asList("a", "b"), 2, 10);

        System.out.println(result);
    }

    <T> List<T> getPageData(Iterable<T> it, int pageNum, int pageSize) {
        return Flux.fromIterable(it)
                //.filter(...) // TODO acl 过滤
                .skip(pageSize * pageNum)
                .take(pageNum)
                .collectList()
                .block();
    }
}
