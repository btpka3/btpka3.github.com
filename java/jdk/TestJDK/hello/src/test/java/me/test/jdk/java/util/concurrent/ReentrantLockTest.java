package me.test.jdk.java.util.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2023/11/23
 */
public class ReentrantLockTest {
    final Lock lock = new ReentrantLock();

    @Test
    public void x() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(null, this::run, "t-" + i, 0).start();
        }
        Thread.sleep(15000);
    }

    public void run() {
        System.out.println(now() + " " + Thread.currentThread().getName() + " : start");
        boolean locked = false;
        try {
            locked = lock.tryLock();
            if (locked) {
                System.out.println(now() + " " + Thread.currentThread().getName() + " : run");
                Thread.sleep(5000);
            }
        } catch (Throwable e) {
            if (locked) {
                lock.unlock();
            }
        }
        System.out.println(now() + " " + Thread.currentThread().getName() + " : end");
    }

    protected String now() {
        return DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    }
}
