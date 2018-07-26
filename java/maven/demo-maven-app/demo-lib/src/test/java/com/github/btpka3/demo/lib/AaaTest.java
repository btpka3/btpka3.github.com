package com.github.btpka3.demo.lib;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AaaTest {

    @Test
    public void testToList01() {

        Aaa aaa = new Aaa();
        List<String> list = aaa.toList("aa", "bb");
        Assert.assertEquals("aa", list.get(0));
        Assert.assertEquals("bb", list.get(1));
        Assert.assertEquals(2, list.size());
    }
}
