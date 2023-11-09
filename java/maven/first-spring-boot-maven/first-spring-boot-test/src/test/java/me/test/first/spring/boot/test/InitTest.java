package me.test.first.spring.boot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

        @Bean(initMethod = "afterPropertiesSet")
        Aaa aaa() {
            return new Aaa();
        }
    }

    @Autowired
    private Xxx xxx;
    @Autowired
    private Aaa aaa;

    @Test
    public void x() {
        System.out.println("xxx = " + xxx);
        System.out.println("aaa = " + aaa);
    }


    public static class Xxx {
        public void init() {
            System.out.println("-=----------------- init.");
        }

    }

    public static class Aaa implements InitializingBean {

        /**
         * 只会执行一次：
         * - 通过实现接口 InitializingBean
         * - 通过使用 @PostConstruct
         * - 通过设置 initMethod = "afterPropertiesSet"
         *
         * @throws Exception
         */
        @PostConstruct
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("========afterPropertiesSet");
        }
    }
}
