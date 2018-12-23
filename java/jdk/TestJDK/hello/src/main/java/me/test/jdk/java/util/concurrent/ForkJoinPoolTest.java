package me.test.jdk.java.util.concurrent;

import me.test.U;

import java.util.concurrent.*;

public class ForkJoinPoolTest {


    public static void main(String[] args) throws Exception {
        test01();
    }


    /**
     * 计算 从1加到40，每个任务 sleep 1秒.
     * <p>
     * 预计总共会有8个线程，耗时 10 秒
     */
    public static void test01() throws InterruptedException, ExecutionException {

        U.print("start", "");
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = pool.submit(new A(1, 40));

        do {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!task.isDone());
        pool.shutdown();
        U.print("done", "" + task.get());
    }

    public static class A extends RecursiveTask<Long> {

        /**
         * include
         */
        private long start;

        /**
         * include
         */
        private Long end;

        public A(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            // 任务已经分割好，就执行。
            if (end - start <= 10) {
                long sum = 0;
                for (long i = start; i <= end; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sum += i;
                }
                U.print("exec", "start = " + start
                        + ", end = " + end
                        + ", sum = " + sum
                );
                return sum;

                // 否则，执行切分逻辑
            } else {
                long mid = (start + end) / 2;
                A right = new A(start, mid);
                right.fork();

                A left = new A(mid + 1, end);
                left.fork();

                U.print("split", "mid = " + mid);


                long sum = right.join() + left.join();

                U.print("split", "mid = " + mid + ", sum = " + sum);

                return sum;
            }
        }
    }

}
