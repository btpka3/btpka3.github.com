package me.test.rxjava;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.util.concurrent.*;

/**
 * Created by zll on 02/09/2017.
 */
public class U {
    public static void print(String step, Object data) {
        System.out.printf("[%4d - %30s] - %10s : %s%n",
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );
    }


    public static class MyThreadFactory implements ThreadFactory {

        private int i = 0;
        private String threadName = "";

        public MyThreadFactory(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, threadName + "-" + (i++));
        }
    }

    public static Scheduler getNamedScheduler(String name) {
        return Schedulers.from(Executors.newCachedThreadPool(new MyThreadFactory(name)));
    }
}
