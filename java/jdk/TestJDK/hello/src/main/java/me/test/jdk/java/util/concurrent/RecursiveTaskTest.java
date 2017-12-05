package me.test.jdk.java.util.concurrent;

import java.util.*;
import java.util.concurrent.*;

public class RecursiveTaskTest {

    public static void main(String[] args) throws Exception {
//        test00();
//        test01();
        test02();
    }

    public static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            Fibonacci f2 = new Fibonacci(n - 2);

            // 方式一：全同步递归
            // return f1.compute() + f2.compute();

            // 方式一：半同步半异步递归
            //return f1.fork().join() + f2.compute();

            // 方式二：全异步递归
            return f1.fork().join() + f2.fork().join();
        }
    }


    public static int fi(int n) {
        if (n <= 1) {
            return n;
        }
        return fi(n - 1) + fi(n - 2);
    }

    /**
     * `-Xss160k`
     */
    public static void test00() throws Exception {

        fi(100);
    }

    /**
     * 100
     * @throws Exception
     */
    public static void test01() throws Exception {

        ForkJoinPool pool = new ForkJoinPool();
        RecursiveTask<Integer> task = new Fibonacci(50);
        Future<Integer> result = pool.submit(task);

        // 只有当你调用join()或get()方法来等待任务的完成时, ForkJoinPool才能使用work-stealing算法
        System.out.println(result.get());


        do {
            System.out.printf("Thread Count/Steal/Parallelism = %d/%d/%d %n",
                    pool.getActiveThreadCount(),
                    pool.getStealCount(),
                    pool.getParallelism()
            );

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());


        pool.shutdown();
    }


    public static class A extends RecursiveTask<Long> {

        int lo, hi;
        long arr[];

        public A(long[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            for (int i = lo; i <= hi; i++) {
                sum += arr[lo];
            }
            return sum;
        }
    }

    /**
     * 能直接对任务记性 fork 么？——可以，内部使用 ForkJoinPool.common 。
     */
    public static void test02() throws Exception {
        final int COUNT = 100;
        long[] arr = new long[COUNT];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < COUNT; i++) {
            arr[i] = r.nextLong();
        }
        A a = new A(arr, 0, COUNT - 1);

//        ForkJoinPool pool = new ForkJoinPool();
//        Future<Long> result = pool.submit(a);
//        System.out.println(result.get());
        System.out.println(a.fork().join());
    }


}
