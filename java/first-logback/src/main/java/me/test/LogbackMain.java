package me.test;

import java.sql.SQLException;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackMain {
    
    private static Logger logger = LoggerFactory.getLogger(LogbackMain.class);

    private final static PeriodFormatter fmt = new PeriodFormatterBuilder()
            .printZeroAlways()
            .appendHours()
            .appendSeparator(":")
            .appendMinutes()
            .appendSeparator(":")
            .appendSeconds()
            .appendSeparator(".")
            .appendMillis3Digit()
            .toFormatter();

    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        final long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.name = "me.test.worker_" + i;
            worker.count = 1000;
            worker.inteval = 0;
            new Thread(worker).start();
            // delay to start a new Thread
            // or, SLF4J: http://www.slf4j.org/codes.html#substituteLogger
//            try {
//                Thread.sleep(400);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                long endTime = System.currentTimeMillis();
                System.out.println("main : "
                        + fmt.print(new Period(endTime - beginTime)));
            }
        });
    }

    private static class Worker implements Runnable {

        private String name;
        private int count;
        // 间隔：毫秒
        private long inteval;

        public void run() {
            Logger logger = LoggerFactory.getLogger(name);
            long beginTime = System.currentTimeMillis();

            for (int i = 0; i < count; i++) {
                logger.error("hi_" + i, new RuntimeException("xxx", new RuntimeException("yyy")));
                if (inteval > 0) {
                    try {
                        Thread.sleep(inteval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            long endTime = System.currentTimeMillis();

            System.out.println(name + " : "
                    + fmt.print(new Period(endTime - beginTime)));
        }

    }

}
