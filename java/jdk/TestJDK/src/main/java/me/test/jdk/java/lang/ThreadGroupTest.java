package me.test.jdk.java.lang;

// Test ThreadGroup and UncaughtExceptionHandler 
public class ThreadGroupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {

        final int loopCount = 3;
        boolean usingParentThreadGroup = args.length == 0 ? false : true;
        ThreadGroup tg = null;
        if (usingParentThreadGroup) {
            // will upper to "system" ThreadGroup and ended without any error
            // output.
            tg = new ThreadGroup("MyWorkerRegistry");
        } else {

            tg = new ThreadGroup("MyWorkerRegistry") {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.err.println("ERROR : " + t.getName());
                    e.printStackTrace(System.err);
                }
            };
        }

        Thread t0 = new Thread(tg, new Runnable() {
            public void run() {
                for (int i = 0; i < loopCount; i++) {
                    Thread t = Thread.currentThread();
                    String threadName = t.getName();
                    System.out.println(threadName + " 000 ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }, "worker-0");
        t0.start();

        Thread t1 = new Thread(tg, new Runnable() {
            public void run() {
                for (int i = 0; i < loopCount * 2; i++) {
                    Thread t = Thread.currentThread();
                    String threadName = t.getName();
                    System.out.println(threadName + " 111 ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }, "worker-1");
        t1.start();

        Thread t2 = new Thread(tg, new Runnable() {
            public void run() {
                while (true) {
                    Thread t = Thread.currentThread();
                    String threadName = t.getName();
                    System.out.println(threadName + " 222 ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    } catch (ThreadDeath e) {
                        System.out.println("AAAAAAAAAAAAA");
                        throw e;
                    }
                }
            }
        }, "worker-2");
        t2.start();

        tg.list();

        Thread.sleep(1000);
        Thread.sleep(loopCount * 1000);
        tg.list();

        Thread.sleep(loopCount * 1000);
        tg.list();

        Thread.sleep(loopCount * 1000);
        Thread[] activeThreads = new Thread[tg.activeCount()];
        tg.enumerate(activeThreads);
        for (Thread t : activeThreads) {
            System.out.println("Stopping " + t);
            t.stop();
        }

        Thread.sleep(loopCount * 1000);
        tg.list();
    }
}
