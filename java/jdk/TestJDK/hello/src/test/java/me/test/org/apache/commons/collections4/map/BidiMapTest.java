package me.test.org.apache.commons.collections4.map;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals("C", map.get("A"));
        Assertions.assertEquals("3", map.get("2"));

        BidiMap map2 = map.inverseBidiMap();
        Assertions.assertEquals("A", map2.get("C"));
        Assertions.assertEquals("2", map2.get("3"));
    }
}
