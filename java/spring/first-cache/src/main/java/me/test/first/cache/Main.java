
package me.test.first.cache;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Thread {

    private static Logger logger = LoggerFactory.getLogger(A.class);

    static class T extends Thread {

        private boolean stop = false;

        @Override
        public void run() {

            logger.info("Running... Press enter to stop.");

            while (!stop) {

                // 控制台如果有输入的话，也停止
                try {
                    if (System.in != null && System.in.available() != 0) {
                        logger.info("Stoping because user press enter.");
                        break;
                    }
                } catch (IOException e) {
                    // do nothing
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // 被强行终止
                    logger.warn("Stoping because InterruptedException.");
                    break;
                }
            }
        }

        /**
         * 通知该线程自行停止。
         */
        public void notifyStop() {

            stop = true;
        }

    }

    public static void main(String[] args) {

        logger.info("Starting...");

        final ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("db-context.xml");
        appCtx.registerShutdownHook();

        final T t = new T();
        t.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                logger.info("notify stop");
                t.notifyStop();
            }
        });

        try {
            t.join();
        } catch (InterruptedException e) {
        }

        if (appCtx.isRunning()) {
            appCtx.close();
        }
        logger.info("Stopped...");

    }
}
