package me.test.first.spring.boot.test.configuration;

import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.autoconfigure.EnableAutoConfigurationTest;
import me.test.first.spring.boot.test.configuration.pkg1.Conf1;
import me.test.first.spring.boot.test.configuration.pkg1.MyPerson;
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
@SpringBootTest(
        classes = ConfigurationTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ConfigurationTest {


    @SpringBootApplication(
            scanBasePackageClasses = Conf1.class,
            exclude = {
                    DataSourceAutoConfiguration.class,
            })
    @Configuration
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(EnableAutoConfigurationTest.Conf.class, args);
        }

        @Bean
        public MyPerson confWang5() {
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
        Assertions.assertEquals(2, map.size());
        Assertions.assertNull(map.get("confZhang3"));
        Assertions.assertEquals("li4", map.get("confLi4").getName());
        Assertions.assertEquals("wang5", map.get("confWang5").getName());
    }
}
