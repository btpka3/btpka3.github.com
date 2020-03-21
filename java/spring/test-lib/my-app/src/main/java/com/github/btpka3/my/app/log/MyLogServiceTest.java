package com.github.btpka3.my.app.log;

import com.github.btpka3.my.lib.log.MyLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
public class MyLogServiceTest {

    public static void main(String[] args) {


//        testLog01();
        testNdc();

    }

    public static void testLog01() {
        try {
            MyLogService.testLog01();
            Logger log = LoggerFactory.getLogger(MyLogService.class);
            log.error("xxx", new RuntimeException("err001"));
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        }

    }

    protected static void xx(Logger log, int a, int b, int max) {

        int sum = a + b;
        log.info("new sum={}", sum);

        if (sum >= max) {
            return;
        } else {
            int newA = b;
            int newB = sum;
            MdcEx.withMdc("a", newA)
                    .withMdc("b", newB)
                    .run(() -> xx(log, newA, newB, max));
        }
    }

    public static void testNdc() {

        // 1: 1+2=3
        // 2: 2+3=5
        // 3: 3+5=8
        // 4: 5+8=13
        // 5: 8+13=21
        Logger log = LoggerFactory.getLogger(MyLogService.class);
        log.info("test started");
        try {
            int a = 1;
            int b = 2;
            int max = 20;
            MdcEx.withMdc("a", a)
                    .withMdc("b", b)
                    .run(() -> xx(log, a, b, max));
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        } finally {
            log.info("test ended");
        }
    }
}
