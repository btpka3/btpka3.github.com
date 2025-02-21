package me.test.com.fasterxml.jackson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import me.test.com.alibaba.fastjson.JsonTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/11/21
 */
public class JacksonTest {
    @SneakyThrows
    @Test
    public void testByteArray() {
        Map m1 = new HashMap(8);
        m1.put("a", "a01");
        m1.put("b", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println("======m1:\n" + m1);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(m1);
        System.out.println("======jsonStr:\n" + jsonStr);
        Map m2 = mapper.readValue(jsonStr, Map.class);
        System.out.println("======m2:\n" + m2);
    }

    @SneakyThrows
    @Test
    public void testPrettyArray() {
        JsonTest.User user = JsonTest.User.builder()
                .name("zhang3")
                .hobbies(Arrays.asList("aaa", "bbb", "ccc"))
                .build();
        ObjectMapper mapper = new ObjectMapper();

        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        mapper.setDefaultPrettyPrinter(prettyPrinter);

        String jsonStr = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(user);
        System.out.println("======jsonStr:\n" + jsonStr);
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
