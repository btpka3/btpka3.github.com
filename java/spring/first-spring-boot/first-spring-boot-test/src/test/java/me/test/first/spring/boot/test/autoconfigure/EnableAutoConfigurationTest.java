package me.test.first.spring.boot.test.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
//@ContextConfiguration
//@SpringJUnitConfig(
//        initializers = {ConfigDataApplicationContextInitializer.class}
//)
@SpringBootTest(
        classes = EnableAutoConfigurationTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class EnableAutoConfigurationTest {


    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    @Configuration
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(EnableAutoConfigurationTest.Conf.class, args);
        }

        @Bean
        public MyPerson wang5() {
            return MyPerson.builder()
                    .name("wang5")
                    .build();
        }

    }

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test1() {
        Map<String, MyPerson> map = applicationContext.getBeansOfType(MyPerson.class);
        System.out.println("====###====" + map);
        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("zhang3", map.get("zhang3").getName());
        Assertions.assertEquals("li4", map.get("li4").getName());
        Assertions.assertEquals("wang5", map.get("wang5").getName());
    }
}
