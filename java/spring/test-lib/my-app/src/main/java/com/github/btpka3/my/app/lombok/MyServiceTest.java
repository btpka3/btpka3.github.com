package com.github.btpka3.my.app.lombok;

import com.github.btpka3.my.lib.lombok.MyService;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
public class MyServiceTest {

    public static void main(String[] args) {
        testException();
    }


    public static void testException() {
        try {
            MyService.testException();
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        }

    }
}
