package me.test.jdk.java.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

/**
 * 就是JavaScript 中的 Promise 嘛~
 */
public class CompletableFuture2Test {

    /**
     * 同线程执行+报错
     */
    @Test
    public void test01() {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        AtomicReference<RuntimeException> exceptRef = new AtomicReference<>();
        // 模拟打8折，这里忽略精度问题
        CompletableFuture<Void> v = futurePrice
                .thenApply(d -> {
                    double newPrice = d + 5;
                    System.out.println(Thread.currentThread().getName() + " : 客服加运费 = " + newPrice);
                    return newPrice;
                })
                .thenApply(d -> {
                    double newPrice = d * .8;
                    System.out.println(Thread.currentThread().getName() + " : 订单打8折 = " + newPrice);
                    return newPrice;
                })
                .thenAccept(d -> {
                    if (true) {
                        throw new RuntimeException("aaa");
                    }
                    System.out.println(Thread.currentThread().getName() + " : 最终价格 = " + d);
                })
                .thenRun(() -> System.out.println(Thread.currentThread().getName() + " : 执行其他异步通知"))
                .whenComplete((d, err) -> {
                    System.out.println(Thread.currentThread().getName() + " : 价格计算完毕 ");
                })
                .exceptionally(e -> {
                    System.out.println(Thread.currentThread().getName() + " : 出错啦 ");
                    exceptRef.set(e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e));
                    throw new RuntimeException(e);
                });

        double price = 100.0;

        System.out.println(Thread.currentThread().getName() + " : 开始计算价格");
        System.out.println(Thread.currentThread().getName() + " : 订单原价 = " + price);
        futurePrice.complete(price);
        if (v.isCompletedExceptionally()) {
            System.out.println(Thread.currentThread().getName() + " : ERROR = " + exceptRef.get());
        }

        System.out.println(Thread.currentThread().getName() + " : Done.");
    }
}
