package me.test.rxjava.operators.filtering;

import io.reactivex.rxjava3.core.Flowable;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 *
 */
public class Sample {

    @Test
    public void sample01() throws InterruptedException {
        System.out.println("----------------------sample01");

        Flowable<Integer> f = Flowable.range(1, 1000000)
                .sample(1, TimeUnit.MILLISECONDS);

        f.subscribe(i -> U.print("s1.subscribe", i));
        Thread.sleep(3000);
        f.subscribe(i -> U.print("s2.subscribe ================= ", i));
    }


}
