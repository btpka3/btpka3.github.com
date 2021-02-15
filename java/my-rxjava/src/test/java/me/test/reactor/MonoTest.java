package me.test.reactor;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2020/11/23
 */
public class MonoTest {

    @Test
    public void ss() {


        Mono<Long> m = Mono.defer(() -> Mono.just(RandomUtils.nextLong()))
                .cache();

        m.subscribe(d -> System.out.println("result1 = " + d));
        m.subscribe(d -> System.out.println("result2 = " + d));
    }

    @Test
    public void tryCache01() {

        AtomicLong count = new AtomicLong(0);
        Function<ContextView, Mono<Long>> supplier = (context) -> {
            long c = count.addAndGet(1);
            if (c % 2 == 1) {
                throw new IllegalArgumentException("aaa");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Mono.just(c);
        };


        Mono<Long> m = Mono.deferContextual(supplier)
                .contextWrite(context -> context.put("startTime", System.currentTimeMillis()));


        m.subscribe(d -> System.out.println("result1 = " + d));
        m.subscribe(d -> System.out.println("result2 = " + d));
    }

    protected <T> Supplier<Tuple2<T, Long>> withRt(Supplier<T> supplier) {
        return () -> {
            long startTime = System.currentTimeMillis();
            T t = null;
            try {
                t = supplier.get();
                long endTime = System.currentTimeMillis();
                return Tuples.of(t, endTime - startTime);
            } catch (Throwable e) {
                // FIXME
                return null;
            }
        };


    }


}
