package me.test.reactor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

/**
 * @date 2018-11-03
 */
public class Merge01 {

    Logger log = LoggerFactory.getLogger(Merge01.class);

    @Test
    public void test01() {
        CountDownLatch latch = new CountDownLatch(1);

        Flux<Integer> f1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> f2 = Flux.just(4, 5, 6, 7, 8);

        f1.mergeWith(f2);
        f1
                .doOnComplete(latch::countDown)
                .subscribe(
                        d -> log.info("data = " + d),
                        e -> log.error(e.getMessage(), e),
                        () -> System.out.println("Done.")

                );

    }

    @Test
    public void test02() {
        CountDownLatch latch = new CountDownLatch(1);

        Flux<Integer> f1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> f2 = Flux.just(4, 5, 6, 7, 8);


        Flux.merge(f1, f2)
                .distinct()
                .doOnComplete(latch::countDown)
                .subscribe(
                        d -> log.info("data = " + d),
                        e -> log.error(e.getMessage(), e),
                        () -> System.out.println("Done.")

                );

    }
}
