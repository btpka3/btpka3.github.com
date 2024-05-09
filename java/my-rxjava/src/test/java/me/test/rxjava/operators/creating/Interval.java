package me.test.rxjava.operators.creating;

import io.reactivex.rxjava3.core.Flowable;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * http://reactivex.io/documentation/operators/interval.html
 */
public class Interval {

    @Test
    public void interval01() throws InterruptedException {
        System.out.println("----------------------interval01");

        // 每过一定间隔，创建一个Long型消息（从0开始）。
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> U.print("subscribe", i));

        Thread.sleep(5000);
    }


}
