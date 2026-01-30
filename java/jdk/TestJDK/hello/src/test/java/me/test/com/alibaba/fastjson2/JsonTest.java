package me.test.com.alibaba.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.alibaba.fastjson2.writer.ObjectWriterProvider;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
        Map map = JSON.parseObject(jsonStr, new TypeReference<Map<Long, Long>>() {
        });
        Assertions.assertEquals(222L, map.get(111L));
        Map.Entry entry = (Map.Entry) map.entrySet().toArray()[0];
        Assertions.assertEquals(Long.class, entry.getKey().getClass());
        Assertions.assertEquals(Long.class, entry.getValue().getClass());
        String newJsonStr = JSON.toJSONString(map, JSONWriter.Feature.WriteNonStringKeyAsString);
        Assertions.assertEquals(jsonStr, newJsonStr);
    }

    @Test
    public void testByteArray() {
        Map m1 = new HashMap(8);
        m1.put("a", "a01");
        m1.put("b", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println("======m1:\n" + m1);
        String jsonStr = JSON.toJSONString(m1);
        System.out.println("======jsonStr:\n" + jsonStr);
        Map m2 = JSON.parseObject(jsonStr, Map.class);
        System.out.println("======m2:\n" + m2);
    }

    @Test
    public void testArray01() {
        Object obj = JSON.parse("{\"a\":[\"a1\",\"a2\"]}");
        Assertions.assertEquals("com.alibaba.fastjson2.JSONObject", obj.getClass().getName());
        Map map = (Map) obj;
        Object a = map.get("a");
        Assertions.assertEquals("com.alibaba.fastjson2.JSONArray", a.getClass().getName());
    }

    @Test
    public void testArray02() {
        Object obj = JSON.parseObject("{\"a\":[\"a1\",\"a2\"]}", Map.class);
        Assertions.assertEquals("java.util.HashMap", obj.getClass().getName());
        Map map = (Map) obj;
        Object a = map.get("a");
        Assertions.assertEquals("com.alibaba.fastjson2.JSONArray", a.getClass().getName());
    }


    @Test
    public void testCustomWriterAndReader() {
        MyObj obj = MyObj.builder()
                .name("zhang3")
                .type(MyType.AAA)
                .build();
        String expectedJsonStr = "{\"name\":\"zhang3\",\"type\":\"aaa\"}";

        // 序列化
        {
            ObjectWriterProvider provider = new ObjectWriterProvider();
            provider.register(MyType.class, new MyTypeObjectWriter());
            JSONWriter.Context context = new JSONWriter.Context(provider);
            Assertions.assertEquals(expectedJsonStr, JSON.toJSONString(obj, context));
        }

        // 反序列化
        {
            ObjectReaderProvider provider = new ObjectReaderProvider();
            provider.register(MyType.class, new MyTypeObjectReader());
            JSONReader.Context context = new JSONReader.Context(provider);
            MyObj obj2 = JSON.parseObject(expectedJsonStr, MyObj.class, context);
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
            return "MyType{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    public static class MyTypeObjectWriter implements ObjectWriter<MyType> {

        @Override
        public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            if (object == null) {
                jsonWriter.writeNull();
                return;
            }
            jsonWriter.writeString(((MyType) object).getValue());
        }
    }

    public static class MyTypeObjectReader implements ObjectReader<MyType> {
        @Override
        public MyType readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
            String str = jsonReader.readString();
            if (str == null) {
                return null;
            }
            if ("aaa".equals(str)) {
                return MyType.AAA;
            } else if ("bbb".equals(str)) {
                return MyType.BBB;
            } else if ("ccc".equals(str)) {
                return MyType.CCC;
            }
            throw new RuntimeException("not support MyType:" + str);
        }
    }

    @Test
    public void testSortedMapKeys() {
        Map<String, String> m = new HashMap<>(8);
        m.put("zhang3", "333");
        m.put("li4", "444");
        m.put("wang5", "555");
        m.put("zhao6", "666");
        String jsonStr = JSON.toJSONString(m, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.SortMapEntriesByKeys);
        System.out.println("========== jsonStr:");
        System.out.println(jsonStr);
    }
}
