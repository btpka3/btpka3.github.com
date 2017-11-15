package me.test.rxjava.operators.creating;

import io.reactivex.*;
import me.test.rxjava.*;

import java.util.concurrent.*;

/**
 * http://reactivex.io/documentation/operators/interval.html
 */
public class Interval {


    public static void main(String[] args) throws InterruptedException {

        interval01();


    }

    public static void interval01() throws InterruptedException {
        System.out.println("----------------------interval01");

        // 每过一定间隔，创建一个Long型消息（从0开始）。
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> U.print("subscribe", i));

        Thread.sleep(5000);
    }


}
