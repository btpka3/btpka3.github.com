package me.test.rxjava.operators.filtering;

import io.reactivex.*;
import me.test.rxjava.*;

import java.util.concurrent.*;

/**
 *
 */
public class Sample {


    public static void main(String[] args) throws InterruptedException {

        sample01();

    }


    public static void sample01() throws InterruptedException {
        System.out.println("----------------------sample01");

        Flowable<Integer> f = Flowable.range(1, 1000000)
                .sample(1, TimeUnit.MILLISECONDS);

        f.subscribe(i -> U.print("s1.subscribe", i));
        Thread.sleep(3000);
        f.subscribe(i -> U.print("s2.subscribe ================= ", i));
    }


}
