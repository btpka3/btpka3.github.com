package me.test.com.alibaba.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/3/20
 */
public class JsonTest {
    @Test
    public void testJGeneric01() {
        String jsonStr = "{\"111\":222}";
        Map map = JSON.parseObject(jsonStr, new TypeReference<Map<Long, Long>>() {
        });
        Assertions.assertEquals(222L, map.get(111L));
        Map.Entry entry = (Map.Entry) map.entrySet().toArray()[0];
        Assertions.assertEquals(Long.class, entry.getKey().getClass());
        Assertions.assertEquals(Long.class, entry.getValue().getClass());
        String newJsonStr = JSON.toJSONString(map, JSONWriter.Feature.WriteNonStringKeyAsString);
        Assertions.assertEquals(jsonStr, newJsonStr);
    }
}
