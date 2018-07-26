package com.github.btpka3.demo.war.service;

import org.junit.Assert;
import org.junit.Test;

public class BbbTest {

    Bbb bbb = new Bbb();

    @Test
    public void testPlus01() {
        Assert.assertEquals(10003, bbb.plus(1, 2));
    }
}
