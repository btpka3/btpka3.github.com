package me.test.com.esotericsoftware.kryo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/4/24
 */
public class MyKryoUtilsTest {

    @Test
    public void serialize01() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("a", "aaa");
    }

    @Test
    public void test() {
        byte b = (byte) (MyKryoUtils.VERSION | 0);
        System.out.println(b);
        Assertions.assertEquals(b, MyKryoUtils.VERSION);
    }
}
