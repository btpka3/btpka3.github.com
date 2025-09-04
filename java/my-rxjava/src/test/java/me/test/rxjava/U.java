package me.test.rxjava;


import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.*;
import java.time.format.*;
import java.util.concurrent.*;

/**
 * Created by zll on 02/09/2017.
 */
public class U {

    public static String now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        return zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    public static void print(String step, Object data) {
        System.out.printf("[%s][%4d - %30s] - %10s : %s%n",
                now(),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );
    }

    public static void print(String step, Object data, Throwable e) {
        print(step, data);
        if (e != null) {
            e.printStackTrace();
        }
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
