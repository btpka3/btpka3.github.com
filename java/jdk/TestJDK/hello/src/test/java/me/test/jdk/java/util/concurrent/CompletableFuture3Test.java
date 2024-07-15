package me.test.jdk.java.util.concurrent;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * 就是JavaScript 中的 Promise 嘛~
 */
public class CompletableFuture3Test {


    /**
     * 这里的 "订单打8折" 并未输出。正确的代码请参考 test02.
     * <p>
     * 解释:
     * `new CompletableFuture<Double>()` 和 `.thenApplyAsync` 是各自有对应的 CompletableFuture 实例。
     * 但最后的 `.complete(100.0)` 相当于直接让最后的 CompletableFuture 直接使用给定的值。
     * 应该让 `new CompletableFuture<Double>()` 对应的 CompletableFuture 实例来 进行 complete 操作。
     * 故这种级联`.` 操作时需要注意当前的操作对象是谁。
     */
    @SneakyThrows
    @Test
    public void test01() {
        boolean result = new CompletableFuture<Double>()
                .thenApplyAsync(d -> {
                    double newPrice = d * .8;
                    System.out.println(Thread.currentThread().getName() + " : 订单打8折 = " + newPrice);
                    return newPrice;
                })
                .complete(100.0);

        System.out.println(Thread.currentThread().getName() + " : 开始异步计算价格, result=" + result);

        Thread.sleep(3000);

        System.out.println(Thread.currentThread().getName() + " : Done.");
    }


    @SneakyThrows
    @Test
    public void test02() {
        CompletableFuture<Double> f = new CompletableFuture<Double>();
        CompletableFuture<Double> f2 = f.thenApplyAsync(d -> {
            double newPrice = d * .8;
            System.out.println(Thread.currentThread().getName() + " : 订单打8折 = " + newPrice);
            return newPrice;
        });
        boolean result = f.complete(100.0);

        System.out.println(Thread.currentThread().getName() + " : 开始异步计算价格, result=" + result);

        Thread.sleep(3000);

        System.out.println(Thread.currentThread().getName() + " : Done.");
    }
}
