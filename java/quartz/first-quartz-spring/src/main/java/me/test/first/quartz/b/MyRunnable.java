package me.test.first.quartz.b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRunnable implements Runnable {

    Logger log = LoggerFactory.getLogger(MyRunnable.class);

    public void run() {
        log.info("Hello world~~~");
    }

}
