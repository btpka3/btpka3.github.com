package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/6/18
 * @see <a href="https://www.baeldung.com/java-thread-lifecycle">Life Cycle of a Thread in Java</a>
 */
public class Thread2Test {

    @Test
    public void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            // RUNNABLE
            printThread("bbb", Thread.currentThread());
            System.out.println("hello world");
        });
        // NEW
        printThread("aaa", thread);

        //        thread.getState();
        //        thread.getId();
        //        thread.getStackTrace();

        thread.start();

        Thread.currentThread().sleep(3000);

        // TERMINATED
        printThread("ccc", thread);
    }

    public void printThread(String prefix, Thread thread) {
        System.out.println(prefix + ": thread: id=" + thread.getId() + ", name=" + thread.getName() + ",state="
                + thread.getState());
    }
}
