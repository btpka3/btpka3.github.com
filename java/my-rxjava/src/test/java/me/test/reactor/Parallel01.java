package me.test.reactor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * @date 2018-11-03
 */
public class Parallel01 {

    Logger log = LoggerFactory.getLogger(Parallel01.class);

    @Test
    public void test01() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, 10)

                .flatMap(count -> Mono.just(count)
                        .subscribeOn(Schedulers.parallel())
                        .map(i -> "a" + i)
                )
                .doAfterTerminate(latch::countDown)
                .subscribe(m -> System.out.println("Subscriber received - " + m + " on thread: " + Thread.currentThread().getName()));

        latch.await();
    }
}
