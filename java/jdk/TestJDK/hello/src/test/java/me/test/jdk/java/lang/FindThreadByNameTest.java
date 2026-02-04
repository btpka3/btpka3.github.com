package me.test.jdk.java.lang;

import java.util.Objects;

/**
 * @author dangqian.zll
 * @date 2023/12/19
 */
public class FindThreadByNameTest {

    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) {
                return t;
            }
        }
        return null;
    }

    public Thread getThreadByName2(String threadName) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        while (threadGroup != null) {
            Thread thread = getThread(threadGroup, threadName);
            if (thread != null) {
                return thread;
            }
            threadGroup = threadGroup.getParent();
        }
        return null;
    }

    public Thread getThread(ThreadGroup threadGroup, String threadName) {
        Thread[] activeThreads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(activeThreads);
        for (Thread t : activeThreads) {
            if (Objects.equals(threadName, t.getName())) {
                return t;
            }
        }
        return null;
    }
}
