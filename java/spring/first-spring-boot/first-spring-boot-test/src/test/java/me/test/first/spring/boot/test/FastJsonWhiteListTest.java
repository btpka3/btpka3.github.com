package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class FastJsonWhiteListTest {

    @SneakyThrows
    @Test
    public void test01() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "a1");
        User user = User.builder().name("zhang3").o(jsonObject).o2(jsonObject).build();
        // TODO
    }


    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private JSONObject o;
        private Map<String, Object> o2;
        private List<User> children;
    }
}