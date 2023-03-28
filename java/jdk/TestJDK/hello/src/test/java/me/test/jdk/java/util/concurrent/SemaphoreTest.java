package me.test.jdk.java.util.concurrent;


import java.util.concurrent.*;

/**
 * 信号量。一种加锁机制。
 *
 * 一个消费者可以使用多个信号量，可以多次归还。
 */
public class SemaphoreTest {

    public static void main(String[] args) {

        test01();

    }

    public static void test01() {


        String[] machines = new String[]{
                "machine-1",
                "machine-2",
                "machine-3"
        };
        String[] users = new String[]{
                "User-1",
                "User-2",
                "User-3",
                "User-4",
                "User-5",
                "User-6",
        };

        Pool pool = new Pool(machines);

        for (String user : users) {
            new Thread(() -> {
                System.out.println("" + Thread.currentThread().getName() + " : " + user + " : 准备租借设备");
                Object machine = pool.borrowItem();
                System.out.println("" + Thread.currentThread().getName() + " : " + user + " : 借到设备[" + machine + "]， 开始工作");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("" + Thread.currentThread().getName() + " : " + user + " : 停电了，中止工作");
                    throw new RuntimeException(e);
                } finally {
                    System.out.println("" + Thread.currentThread().getName() + " : " + user + " : 工作完成，开始归还设备");
                    pool.returnItem(machine);
                }


            }).start();
        }

    }


    public static class Pool {
        private int maxAvailable = 100;
        private Semaphore available;

        // Not a particularly efficient data structure; just for demo
        protected Object[] items = null;
        protected boolean[] used = new boolean[maxAvailable];


        public Pool(Object[] items) {
            this.maxAvailable = items.length;
            this.items = items;
            this.used = new boolean[this.maxAvailable];
            this.available = new Semaphore(this.maxAvailable, true);
        }

        public Object borrowItem() {
            try {
                available.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return getNextAvailableItem();
        }

        public void returnItem(Object x) {
            if (markAsUnused(x)) {
                available.release();
            }
        }


        protected synchronized Object getNextAvailableItem() {
            for (int i = 0; i < maxAvailable; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null; // not reached
        }

        protected synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < maxAvailable; ++i) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }
}
