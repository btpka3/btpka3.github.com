package me.test.jdk.java.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * 就是JavaScript 中的 Promise 嘛~
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {
        test01();
    }

    public static void test01() throws Exception {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        CountDownLatch c = new CountDownLatch(1);

        // 模拟打8折，这里忽略精度问题
        futurePrice
                .thenApply(d -> {
                    double newPrice = d + 5;
                    System.out.println(Thread.currentThread().getName() + " : 客服加运费 = " + newPrice);
                    return newPrice;
                })
                .thenApplyAsync(d -> {

                    double newPrice = d * .8;
                    System.out.println(Thread.currentThread().getName() + " : 订单打8折 = " + newPrice);
                    return newPrice;
                })
                .thenAccept(d ->
                        System.out.println(Thread.currentThread().getName() + " : 最终价格 = " + d)
                )
                .thenRunAsync(() ->
                        System.out.println(Thread.currentThread().getName() + " : 执行其他异步通知")
                )
                .whenComplete((d, err) -> {
                    System.out.println(Thread.currentThread().getName() + " : 价格计算完毕 ");
                    c.countDown();
                })
        ;

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double price = 100.0;
            System.out.println(Thread.currentThread().getName() + " : 订单原价 = " + price);
            futurePrice.complete(price);
        }).start();
        System.out.println(Thread.currentThread().getName() + " : 开始异步计算价格");

        // 等待 thenApplyAsync， thenRunAsync 等使用的系统默认线程 完成任务
        //Thread.sleep(5000);

        c.await();
        System.out.println(Thread.currentThread().getName() + " : Done.");
    }
}
