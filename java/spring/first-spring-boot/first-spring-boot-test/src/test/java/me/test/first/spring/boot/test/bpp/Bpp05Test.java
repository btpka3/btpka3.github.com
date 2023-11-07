package me.test.first.spring.boot.test.bpp;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 确认 ApplicationContext#getBean 只会提前初始化给定的bean。
 *
 * @author dangqian.zll
 * @date 2023/11/7
 */
@SpringBootTest(
        classes = {Bpp05Test.Conf.class, Bpp05Test.MyBfpp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bpp05Test {


    @Configuration
    public static class Conf {

        @Bean
        MyPojo zhang3() {

            System.out.println("============ zhang3 created");

            return MyPojo.builder()
                    .name("zhang3")
                    .build();
        }

        @Bean
        MyPojo li4() {
            System.out.println("============ li4 created");
            return MyPojo.builder()
                    .name("li4")
                    .build();
        }

    }


    @Data
    @Builder(toBuilder = true)
    public static class MyPojo {
        private String name;
    }


    @Component
    public static class MyBfpp implements BeanPostProcessor {
        public MyBfpp(
                ApplicationContext applicationContext
        ) {
            applicationContext.getBean("zhang3");
            System.out.println("============ MyBfpp created");
        }
    }


    @Autowired
    MyBfpp myBfpp;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test01() {
        System.out.println("============ hello world.");

        /* output :
============ zhang3 created
============ MyBfpp created
============ li4 created
============ hello world.
         */
    }

}
