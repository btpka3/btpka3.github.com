package me.test.jdk.java.lang;

import org.junit.Test;

/**
 * @author dangqian.zll
 * @date 2020/11/2
 */
public class LongTest {

    @Test
    public void x() {
        Long l = 4159777755L;
        System.out.println(String.valueOf(l));
        System.out.println(l.toString());
    }

    @Test
    public void y() {
        Number l = 4159777755.0;
        System.out.println(String.valueOf(l));
        System.out.println(l.toString());
    }
}
