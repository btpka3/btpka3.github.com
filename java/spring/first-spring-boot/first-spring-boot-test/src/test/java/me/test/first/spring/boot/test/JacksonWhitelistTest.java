package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * jackson 生成的 json 包含 '@class' 字段，且反序列化时进行class白名单检查。
 *
 * @author dangqian.zll
 * @date 2024/2/7
 */
public class JacksonWhitelistTest {

    ObjectMapper mapper = getObjectMapper();

    protected ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType("aa.bb.cc")
                .allowIfBaseType("me.test.first.spring.boot.test.")
                .allowIfBaseType("com.alibaba.fastjson")
                .build();
        mapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return mapper;
    }

    @SneakyThrows
    @Test
    public void test01() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "a1");
        User user = User.builder().name("zhang3").o(jsonObject).build();

        String jsonStr = mapper.writeValueAsString(user);
        System.out.println(jsonStr);
        Assertions.assertTrue(jsonStr.contains("class"));

        User newUser = mapper.readValue(jsonStr, User.class);
        System.out.println(newUser);
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private JSONObject o;
    }
}
