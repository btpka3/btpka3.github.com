package me.test.first.spring.boot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dangqian.zll
 * @date 2020/9/18
 */
@SpringBootTest(
        classes = InitTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@EnableAutoConfiguration
//@TestPropertySource
@Slf4j
public class InitTest {

    @Configuration
    public static class Conf {
        @Bean
        Xxx xxx() {
            return new Xxx();
        }
    }

    @Autowired
    private Xxx xxx;

    @Test
    public void x() {
        System.out.println("xxx = " + xxx);
    }

    public static class Xxx {
        public void init() {
            System.out.println("-=----------------- init.");
        }

    }
}
