package me.test.jdk.java.util.concurrent;

import java.util.concurrent.*;

/**
 * 非常适合 一个定时任务扫码出符合条件的子任务，然后等待子任务完成。
 *
 * Latch 是门闩的含义。就像一个大门，大门后面有N多个门闩，只有所有门闩都打开了，大门才能打开。
 * 不可重复使用。
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        test01();
    }

    /**
     * 从 CountDownLatch 的 javadoc 复制的例子。
     *
     */
    public static void test01() throws InterruptedException {
        int n = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(n);
        for (int i = 0; i < n; ++i) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        startSignal.countDown();      // let all threads proceed

        System.out.println("所有任务已经开始执行");
        doneSignal.await();           // wait for all to finish
        System.out.println("所有任务已经执行完毕");
    }

    public static class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException ex) {
            } // return;
        }

        void doWork() {
            System.out.println("Thread.name = " + Thread.currentThread().getName());
        }
    }
}
