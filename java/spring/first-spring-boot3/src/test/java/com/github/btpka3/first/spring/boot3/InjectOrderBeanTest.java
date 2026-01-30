package com.github.btpka3.first.spring.boot3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/4
 */
@SpringBootTest
@ContextConfiguration
public class InjectOrderBeanTest {
    @Configuration
    public static class Conf1 {
        @Bean
        @Order(2)
        MyPojo myPojo() {
            return MyPojo.builder()
                    .name("zhang3")
                    .build();
        }
    }

    @Configuration
    public static class Conf2 {
        @Bean
        @Order(1)
        MyPojo myPojo() {
            return MyPojo.builder()
                    .name("li4")
                    .build();
        }
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPojo {
        String name;
    }

    @Autowired
    MyPojo myPojo;

    @Test
    public void test() {
        Assertions.assertEquals("li4", myPojo.getName());

    }

}
