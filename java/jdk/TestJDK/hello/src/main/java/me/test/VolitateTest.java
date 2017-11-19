package me.test;

import java.util.concurrent.atomic.*;

public class VolitateTest {

    public static volatile int num1 = 0;
    public static AtomicInteger num2 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {


        Runnable run = () -> {
            for (int i = 0; i < 10000; i++) {
                // 该操作并不是原子性的，它等同于 先读取num 原有的值，在+1后重新赋值给num
                num1++;
                num2.addAndGet(1);
            }

            // 所以该 demo 中，两个线程输出的 num1 没有一个是 20000， 而 num2 （至少有一个线程打印的）的是。
            System.out.println(Thread.currentThread().getName() + " :  num1 = " + num1 + ", num2 = " + num2);
        };
        new Thread(run).start();
        new Thread(run).start();
    }

}
