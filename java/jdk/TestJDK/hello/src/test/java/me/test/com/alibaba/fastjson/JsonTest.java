package me.test.com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

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


    @Test
    public void testPojoList() {

        List<Student> list = new ArrayList<>(8);

        {
            Student s = new Student();
            s.setName("zhang3");
            s.setClassRoom("classRoom1");
            list.add(s);
        }
        {
            Student s = new Student();
            s.setName("li4");
            s.setClassRoom("classRoom2");
            list.add(s);
        }

        String jsonStr = JSON.toJSONString(
                list,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteClassName,
                SerializerFeature.PrettyFormat
        );
//        Object obj = JSON.parseObject(jsonStr);
//        List list2 = JSON.parseArray(jsonStr);
        List list2 = JSON.parseArray(jsonStr, Student.class);
        System.out.println(list2);
    }


    @SneakyThrows
    @Test
    public void testParseRef() {
        String jsonStr = IOUtils.toString(getClass().getResourceAsStream("JsonTest001.json"), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        System.out.println(jsonObject);
    }

    @Test
    public void testCircleReference() {
        Map<String, Object> f = new HashMap<>();
        f.put("name", "father001");
        Map<String, Object> s = new HashMap<>();
        s.put("name", "son001");

        f.put("son", s);
        s.put("father", f);
        // WORKS
        {
            String str = JSON.toJSONString(
                    s,
                    SerializerFeature.PrettyFormat
            );
            System.out.println("=========str:\n" + str);
            JSONObject newSon = JSON.parseObject(str);

            Assertions.assertEquals("son001", newSon.get("name"));
            JSONObject newFather = (JSONObject) newSon.get("father");
            Assertions.assertEquals("father001", newFather.get("name"));
            // ⭕️
            Assertions.assertNotSame(newSon, newFather.get("son"));
        }
        // ERROR
        {
            // 如果有循环依赖，且使用了 SerializerFeature.DisableCircularReferenceDetect 属性，则会
            // 抛出异常
            try {
                JSON.toJSONString(
                        s,
                        SerializerFeature.DisableCircularReferenceDetect,
                        SerializerFeature.PrettyFormat
                );
                Assertions.fail("should throw exception");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    @Test
    public void testPrettyArray() {
        User user = User.builder()
                .name("zhang3")
                .hobbies(Arrays.asList("aaa", "bbb", "ccc"))
                .build();

        String jsonStr = JSON.toJSONString(user, SerializerFeature.PrettyFormat);
        System.out.println(jsonStr);
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private String name;
        private Person father;
        private Person son;
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Student extends Person {
        private String classRoom;
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String name;
        private List<String> hobbies;
    }
}
