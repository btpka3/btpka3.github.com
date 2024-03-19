package me.test.first.spring.boot.test;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@EnableConfigurationProperties
@SpringBootTest(classes = PlaceHolder007Test.Conf.class)
// 注意： @TestPropertySource 不设置值时，会默认查询与单测类同名的 properties 文件。
@TestPropertySource//(locations = "PlaceHolder007Test.properties")
public class PlaceHolder007Test {

    @Configuration
    public static class Conf {

        @Bean
        @ConfigurationProperties(prefix = "me.user")
        User user() {
            return new User();
        }
    }

    @Value("${a}")
    String a;

    @Autowired
    User user;

    @Test
    public void test01() {
        System.out.println("a = " + a);
        System.out.println("user = " + user);
    }

    @Data

    public static class User {
        private String name;
        private Integer age;
        private List<String> hobbies;
    }
}
