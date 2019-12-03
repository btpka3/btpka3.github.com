package me.test.first.spring.boot.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-09-20
 */
public class LoggerTest {

    @Test
    public void test01() {
        A a = new B();
        Assert.assertEquals("me.test.first.spring.boot.test.LoggerTest$B", a.getLogger().getName());
    }

    public static class A {


        private Logger logger = LoggerFactory.getLogger(getClass());

        public Logger getLogger() {
            return logger;
        }
    }

    public static class B extends A {


        private Logger logger = LoggerFactory.getLogger(B.class);


    }
}
