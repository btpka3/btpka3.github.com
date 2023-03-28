package me.test.jdk.java.util.concurrent;


import java.util.*;
import java.util.concurrent.*;

/**
 * 可重用。
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        test01();
    }

    public static void test01() throws InterruptedException {
        new Solver();
    }

    public static class Solver {

        public Solver() throws InterruptedException {

            int n = 3;
            // 当大家都到这里了(调用 barrier.await() 3 次)，就开始执行这个 Runnable
            Runnable barrierAction = () -> System.out.println("------------ barrierAction : 队员到齐。");
            CyclicBarrier barrier = new CyclicBarrier(n, barrierAction);

            List<Thread> threads = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                Thread thread = new Thread(new Worker(i, barrier));
                threads.add(thread);
                thread.start();
            }

            // wait until done
            for (Thread thread : threads) {
                thread.join();
            }
        }
    }

    public static class Worker implements Runnable {
        int myRow;
        private final CyclicBarrier barrier;

        Worker(int row, CyclicBarrier barrier) {
            myRow = row;
            this.barrier = barrier;
        }


        @Override
        public void run() {

            try {
                System.out.println("" + Thread.currentThread().getName() + " 到达目的地");
                barrier.await();
                System.out.println("" + Thread.currentThread().getName() + " 开始自行离开");

            } catch (InterruptedException ex) {
                return;
            } catch (BrokenBarrierException ex) {
                return;
            }
        }
    }
}
