package me.test.com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(222L, map.get(111L));
        Map.Entry entry = (Map.Entry) map.entrySet().toArray()[0];
        assertEquals(Long.class, entry.getKey().getClass());
        assertEquals(Long.class, entry.getValue().getClass());
        String newJsonStr = JSON.toJSONString(map, SerializerFeature.WriteNonStringKeyAsString);
        assertEquals(jsonStr, newJsonStr);
    }

    @Test
    public void testByteArray() {

        Map map = new HashMap(8);
        map.put("a", "aaa");
        map.put("b", "HelloWorld".getBytes(StandardCharsets.UTF_8));

        String jsonStr = JSON.toJSONString(map, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.PrettyFormat);
        System.out.println(jsonStr);
    }

    @Test
    public void testNull() {
        Map map = new HashMap(8);
        map.put("a", "aaa");
        map.put("b", null);
        String str = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        assertEquals("{\"a\":\"aaa\",\"b\":null}", str);
    }

    @Test
    public void testPojo() {

        Student s = new Student();
        s.setName("zhang3");
        s.setClassRoom("classRoom1");
        Map m = new HashMap(4);
        m.put("p", s);
        String str = JSON.toJSONString(m,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteClassName,
                SerializerFeature.PrettyFormat
        );
        System.out.println(str);
    }

    public static class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Student extends Person {
        private String classRoom;

        public String getClassRoom() {
            return classRoom;
        }

        public void setClassRoom(String classRoom) {
            this.classRoom = classRoom;
        }
    }
}
