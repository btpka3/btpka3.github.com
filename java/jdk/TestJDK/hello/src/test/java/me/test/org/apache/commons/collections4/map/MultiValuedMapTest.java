package me.test.org.apache.commons.collections4.map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2020-06-19
 */
public class MultiValuedMapTest {

    @Test
    public void x() {
        MultiValuedMap m = new ArrayListValuedHashMap();

        m.put("aaa", "bbb");
        m.put("aaa", "ccc");

        m.get("aaa");
    }
}
