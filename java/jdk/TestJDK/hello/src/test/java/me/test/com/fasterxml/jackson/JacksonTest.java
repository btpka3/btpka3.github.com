package me.test.com.fasterxml.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
}
