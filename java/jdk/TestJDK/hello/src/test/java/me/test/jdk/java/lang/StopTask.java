package me.test.jdk.java.lang;

import java.io.IOException;
import java.util.Calendar;

public class StopTask implements Runnable {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new StopTask());
        t.start();
        System.out.println("Task is started.");
        Thread.sleep(1000);

        t.interrupt();
        System.out.println("Interrupting the task.");

        Thread.sleep(1000);
        if (t.isAlive()) {
            t.stop();
            System.out
                    .println("Task is not self stoped, try to force stopping it.");

            Thread.sleep(1000);
            if (t.isAlive()) {
                System.err.println("Task is not stopped");
            }
        }
    }

    /**
     * 假设作业是每天早上8点开始执行，然后循环进行某些IO操作。
     */
    public void run() {

        // 使用 InterruptedException 中止任务示例
        // 休眠至6点
        while (true) {
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 6) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Task is interrupted.");
                e.printStackTrace();
                return;
            }
        }

        // 使用 Thread.interrupted() 中止任务示例
        // 循环执行IO操作（假设没有sleep等会抛出InterruptedException）
        // 需要使用Thread.interrupted()来判断是否被中止。
        while (Thread.interrupted()) {
            try {
                // 避免直接调用阻塞的IO读取/写入方法
                // 否则可能即使调用Thread#stop()方法也停止不了该线程。
                // 方法一：先检查、再调用（参考下面的例子）
                // 方法二：设置超时时间
                if (System.in.available() > 0) {
                    char c = (char) System.in.read();
                    System.out.println("Char = " + c);
                }
            } catch (IOException e) {
                System.err.println("Exception occured, task is terminated.");
                e.printStackTrace();
                return;
            }

            // NOTICE: 这是示例代码，这里最好还是有sleep方法以便提高效率。
        }
    }
}
