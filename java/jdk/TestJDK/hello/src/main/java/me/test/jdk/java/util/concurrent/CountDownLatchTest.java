package me.test.jdk.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 非常适合 一个定时任务扫码出符合条件的子任务，然后等待子任务完成。
 * <p>
 * Latch 是门闩的含义。就像一个大门，大门后面有N多个门闩，只有所有门闩都打开了，大门才能打开。
 * 不可重复使用。
 */
public class CountDownLatchTest {

    static boolean runError = false;

    public static void main(String[] args) throws InterruptedException {
        test01();
    }

    /**
     * 从 CountDownLatch 的 javadoc 复制的例子。
     */
    public static void test01() throws InterruptedException {
        int n = 10;

        // 等待消息，以便统一开始，防止下面的 for 循环中过早开始。
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(n);
        CountDownLatch commitSignal = new CountDownLatch(1);

        AtomicBoolean shouldCommit = new AtomicBoolean(false);

        List<Worker> taskList = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            taskList.add(new Worker(startSignal, doneSignal, commitSignal, shouldCommit));
        }

        taskList.forEach(task -> {
            new Thread(task).start();
        });

        // let all threads proceed
        startSignal.countDown();

        System.out.println("所有任务已经开始执行");
        // wait for all to finish
        doneSignal.await(10, TimeUnit.SECONDS);


        System.out.println("所有任务已经执行完毕");

        boolean allSuccess = taskList.stream().allMatch(task -> Objects.equals("handle_success", task.getStatus()));
        System.out.println("allSuccess = " + allSuccess);
        shouldCommit.set(allSuccess);

        // 通知所有子子任务都已经执行完毕，但不知道该提交还是该回滚。
        commitSignal.countDown();

    }

    public static class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;
        private final CountDownLatch commitSignal;
        private final AtomicBoolean shouldCommit;

        private String status;


        Worker(CountDownLatch startSignal, CountDownLatch doneSignal, CountDownLatch commitSignal, AtomicBoolean shouldCommit) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
            this.commitSignal = commitSignal;
            this.shouldCommit = shouldCommit;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public void run() {

            try {
                startSignal.await();

                doWork();

                status = "handle_success";

            } catch (Exception ex) {
                // NOOP
                status = "handle_failed";
            } finally {
                // 通知单个子任务完成
                doneSignal.countDown();
            }


            // 最多等待1分钟，等其他所有子任务完成
            try {
                commitSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldCommit.get()) {
                commit();
                status = "handle_commit";
            } else {
                rollback();
                status = "handle_rollback";
            }
        }


        void doWork() {
            if (runError) {
                if (Thread.currentThread().getName().contains("8")) {
                    throw new RuntimeException("demo exception");
                }
            }

            System.out.println("Thread.name = " + Thread.currentThread().getName() + " handle");
        }

        void commit() {
            System.out.println("Thread.name = " + Thread.currentThread().getName() + " commit");
        }

        void rollback() {
            System.out.println("Thread.name = " + Thread.currentThread().getName() + " rollback");
        }
    }
}
