package com.github.btpka3.my.app.log;

import com.github.btpka3.my.lib.log.MyLogService;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
public class MyLogServiceTest {

    public static void main(String[] args) {
        testLog01();
    }

    public static void testLog01() {
        try {
            MyLogService.testLog01();
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        }

    }
}
