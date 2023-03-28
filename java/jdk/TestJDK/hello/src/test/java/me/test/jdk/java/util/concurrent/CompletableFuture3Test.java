package me.test.jdk.java.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * 就是JavaScript 中的 Promise 嘛~
 */
public class CompletableFuture3Test {

    public static void main(String[] args) throws Exception {
        test01();
    }

    public static void test01() throws Exception {
        new CompletableFuture<Double>()



                .thenApplyAsync(d -> {

                    double newPrice = d * .8;
                    System.out.println(Thread.currentThread().getName() + " : 订单打8折 = " + newPrice);
                    return newPrice;
                }) .complete(100.0);

//        System.out.println(Thread.currentThread().getName() + " : 开始异步计算价格");

Thread.sleep(3000);

        System.out.println(Thread.currentThread().getName() + " : Done.");
    }
}
