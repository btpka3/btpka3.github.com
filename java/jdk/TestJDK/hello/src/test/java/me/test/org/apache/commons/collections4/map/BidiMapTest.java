package me.test.org.apache.commons.collections4.map;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dangqian.zll
 * @date 2020-06-19
 */
public class BidiMapTest {

    @Test
    public void test01() {
        BidiMap<Object, Object> map = new DualHashBidiMap<>();

        map.put("A", "B");
        // 会覆盖掉
        map.put("A", "C");

        map.put("1", "2");
        map.put("3", "2");


        Assert.assertEquals(2, map.size());
        Assert.assertEquals("C", map.get("A"));
        Assert.assertEquals("3", map.get("2"));

        BidiMap map2 = map.inverseBidiMap();
        Assert.assertEquals("A", map2.get("C"));
        Assert.assertEquals("2", map2.get("3"));

    }

}
