package me.test.com.alibaba.fastjson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/3/20
 */
public class JsonTest {

    @Test
    public void testJGeneric01() {
        String jsonStr = "{\"111\":222}";
        Map map = JSON.parseObject(jsonStr, new TypeReference<Map<Long, Long>>() {});
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

        String jsonStr =
                JSON.toJSONString(map, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.PrettyFormat);
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
        String str = JSON.toJSONString(
                m,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteClassName,
                SerializerFeature.PrettyFormat);
        System.out.println(str);
    }

    @Test
    public void testNullOrEmptyStr() {
        Assertions.assertNull(JSON.parseObject(null, Student.class));
        Assertions.assertNull(JSON.parseObject("", Student.class));
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
                SerializerFeature.PrettyFormat);
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
            String str = JSON.toJSONString(s, SerializerFeature.PrettyFormat);
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
                JSON.toJSONString(s, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
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

    @Test
    public void testSortedMapKeys() {
        Map<String, String> m = new HashMap<>(8);
        m.put("zhang3", "333");
        m.put("li4", "444");
        m.put("wang5", "555");
        m.put("zhao6", "666");
        String jsonStr = JSON.toJSONString(m, SerializerFeature.PrettyFormat, SerializerFeature.MapSortField);
        System.out.println("========== jsonStr:");
        System.out.println(jsonStr);
    }

    // ====================

    @Test
    public void testCustomWriterAndReader() {
        MyObj obj = MyObj.builder().name("zhang3").type(MyType.AAA).build();
        String expectedJsonStr = "{\"name\":\"zhang3\",\"type\":\"aaa\"}";

        // 序列化
        {
            SerializeConfig config = new SerializeConfig();
            config.put(MyType.class, new MyTypeObjectCodec());
            Assertions.assertEquals(expectedJsonStr, JSON.toJSONString(obj, config));
        }

        // 反序列化
        {
            ParserConfig config = new ParserConfig();
            config.putDeserializer(MyType.class, new MyTypeObjectCodec());
            MyObj obj2 = JSON.parseObject(expectedJsonStr, MyObj.class, config);
            Assertions.assertEquals("zhang3", obj2.getName());
            Assertions.assertSame(MyType.AAA, obj2.getType());
        }
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyObj implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private MyType type;
    }

    public static class MyType {
        public static final MyType AAA = new MyType("aaa");
        public static final MyType BBB = new MyType("bbb");
        public static final MyType CCC = new MyType("ccc");

        private String value;

        private MyType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "MyType{" + "value='" + value + '\'' + '}';
        }
    }

    public static class MyTypeObjectCodec implements ObjectSerializer, ObjectDeserializer {

        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
                throws IOException {
            if (object == null) {
                serializer.writeNull();
                return;
            }
            serializer.write(((MyType) object).getValue());
        }

        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            String str = parser.getRawReader().read(String.class);
            if (str == null) {
                return null;
            }
            if ("aaa".equals(str)) {
                return (T) MyType.AAA;
            } else if ("bbb".equals(str)) {
                return (T) MyType.BBB;
            } else if ("ccc".equals(str)) {
                return (T) MyType.CCC;
            }
            throw new RuntimeException("not support MyType:" + str);
        }

        @Override
        public long getFeatures() {
            return 0L;
        }
    }
}
