package me.test.first.spring.boot.test.configuration;

import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.autoconfigure.EnableAutoConfigurationTest;
import me.test.first.spring.boot.test.configuration.pkg1.MyPerson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证 @Configuration 类上有 interface 时，该interface 的相关 findResource 情况。
 *
 * @author dangqian.zll
 * @date 2026/1/15
 * @see org.springframework.context.annotation.ConfigurationClassParser#processInterfaces
 * @see org.springframework.core.type.classreading.MetadataReaderFactory
 */
@SpringBootTest(
        classes = Configuration2Test.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class Configuration2Test {

    public static interface Xxx {
        default void xxx() {
        }
    }

    @Configuration("conf")
    public static class Conf implements Xxx {
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


    @Test
    public void test1() {
        // 请 debug ConfigurationClassParser#processInterfaces
        System.out.println("====###=== OK=");
    }
}
