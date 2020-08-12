package me.test.org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dangqian.zll
 * @date 2020-06-18
 */
public class MultiKeyMapTest {

    @Test
    public void test01() {


        MultiKeyMap<Object, String> map = new MultiKeyMap<>();

        map.put("aaa", 111, "xxx");

        Assert.assertEquals("xxx", map.get("aaa", 111));
        Assert.assertNull(map.get("aaa"));
        Assert.assertNull(map.get("111"));

    }
}
