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

            try {
                Class.forName("btpka3.NotExistedClass");
            } catch (Throwable e) {
                e.printStackTrace();
            }

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

    @Test
    public void testGetStackTrace() {
        System.out.println(Thread.currentThread().getStackTrace().length);
        System.out.println(Xxx.class.getName());
    }


    public static class Xxx {

    }

}
