package me.test.jdk.java.util.concurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class RecursiveActionTest {

    public static void main(String[] args) throws Exception {
        test01();
    }

    public static class MergeSortTask extends RecursiveAction {
        // implementation details follow:
        static final int THRESHOLD = 1000;
        final long[] array;
        final int lo, hi;

        MergeSortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        MergeSortTask(long[] array) {
            this(array, 0, array.length);
        }

        @Override
        protected void compute() {
            if (hi - lo < THRESHOLD) {
                sortSequentially(lo, hi);
            } else {
                int mid = (lo + hi) >>> 1;
                invokeAll(new MergeSortTask(array, lo, mid), new MergeSortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }

        void sortSequentially(int lo, int hi) {
            Arrays.sort(array, lo, hi);
        }

        void merge(int lo, int mid, int hi) {
            long[] buf = Arrays.copyOfRange(array, lo, mid);
            for (int i = 0, j = lo, k = mid; i < buf.length; j++) {
                array[j] = (k == hi || buf[i] < array[k]) ? buf[i++] : array[k++];
            }
        }
    }

    /**
     *
     */
    public static void test01() throws Exception {

        ForkJoinPool pool = new ForkJoinPool();
        final int COUNT = 1000000;
        long[] arr = new long[COUNT];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < COUNT; i++) {
            arr[i] = r.nextLong();
        }

        RecursiveAction task = new MergeSortTask(arr);
        Future<Void> result = pool.submit(task);

        // 只有当你调用join()或get()方法来等待任务的完成时, ForkJoinPool才能使用work-stealing算法
        result.get();
        System.out.println("执行完毕");

        do {
            System.out.printf(
                    "Thread Count/Steal/Parallelism = %d/%d/%d %n",
                    pool.getActiveThreadCount(), pool.getStealCount(), pool.getParallelism());

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        pool.shutdown();
    }
}
