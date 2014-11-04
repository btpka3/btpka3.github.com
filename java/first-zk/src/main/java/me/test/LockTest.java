package me.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class LockTest {

    static StringBuffer buf = new StringBuffer();

    public static void main(String[] args) {

        final int THREAD_COUNT = 10;

        // 创建一个全局的client
        CuratorFramework client = CuratorFrameworkFactory.newClient(""
                + "127.0.0.1:2110,"
                + "127.0.0.1:2120,"
                + "127.0.0.1:2130",
                new ExponentialBackoffRetry(1000, 3));
        // 启动Client
        client.start();

        try {
            List<Thread> list = new ArrayList<Thread>();
            // 创建并启动线程
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = new Thread(new MyTask(client, i));
                t.start();
                list.add(t);
            }

            // 等待线程结束
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = list.get(i);
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            // 关闭Client
            CloseableUtils.closeQuietly(client);
        }
    }

    public static class MyTask implements Runnable {
        private CuratorFramework client;
        private int taskId;

        public MyTask(CuratorFramework client, int taskId) {
            this.client = client;
            this.taskId = taskId;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                doWork(client);
            }
        }

        private void doWork(CuratorFramework client) {

            InterProcessMutex lock = new InterProcessMutex(client, "/myTask");

            // 获取锁
            boolean locked = false;
            try {
                locked = lock.acquire(3, TimeUnit.SECONDS);
            } catch (Exception e) {
            }
            if (!locked) {
                String msg = taskId + " xxx";
                System.err.println(msg);
                return;
            }
            try {
                String msg = taskId + " >>>";
                System.out.println(msg);
                // 模拟执行业务逻辑
                Thread.sleep((long) (5000 * Math.random()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {

                // 释放锁
                try {
                    if (lock.isAcquiredInThisProcess()) {
                        lock.release();
                        String msg = taskId + " <<<";
                        System.out.println(msg);
                    } else {
                        System.out.println(taskId + " ???");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
