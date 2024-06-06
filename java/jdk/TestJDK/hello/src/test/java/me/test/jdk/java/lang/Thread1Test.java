package me.test.jdk.java.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

/**
 * 验证线程异常处理
 *
 * @author dangqian.zll
 * @date 2024/6/4
 */
public class Thread1Test {


    @Test
    public void test01() throws InterruptedException {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("DemoErr");
        }, "aaa");
        if (thread.getUncaughtExceptionHandler() == null) {
            thread.setUncaughtExceptionHandler((tread, throwable) -> {
                System.out.println("DemoErr" + tread.getName() + ": " + ExceptionUtils.getStackTrace(throwable));
            });
        }

        if (Thread.getDefaultUncaughtExceptionHandler() == null) {
            Thread.setDefaultUncaughtExceptionHandler((tread, throwable) -> {
                System.out.println("DemoErrDefault: " + tread.getName() + ": " + ExceptionUtils.getStackTrace(throwable));
            });
        }

        System.out.println("Thread.getDefaultUncaughtExceptionHandler() = " + Thread.getDefaultUncaughtExceptionHandler());
        thread.start();


        Thread.sleep(3000);
    }


}
