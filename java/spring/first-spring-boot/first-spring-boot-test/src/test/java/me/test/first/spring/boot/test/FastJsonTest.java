package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/2/27
 */
public class FastJsonTest {

    @Test
    public void testGeneric01() {
        TypeReference<HttpResult<User>> typeRef = new TypeReference<>() {
        };
        String jsonStr = "{\"status\":200,\"result\":{\"name\":\"zhang3\"}}";
        HttpResult<User> result = JSON.parseObject(jsonStr, typeRef);
        Assertions.assertEquals(200, result.getStatus());
        Assertions.assertEquals("zhang3", result.getResult().getName());
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HttpResult<T> {
        int status;
        T result;
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private JSONObject o;
        private Map<String, Object> o2;
        private List<JacksonWhitelistTest.User> children;
    }
}
