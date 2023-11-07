package me.test.first.spring.boot.test.bpp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 检查构造函数依赖注入
 *
 * @author dangqian.zll
 * @date 2023/11/7
 */
@SpringBootTest(
        classes = {Bpp01Test.Conf.class, Bpp01Test.MyBfpp.class},

        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bpp01Test {


    @Configuration
    public static class Conf {

        @Bean
        MyPojo zhang3() {
            return MyPojo.builder()
                    .name("zhang3")
                    .build();
        }

        @Bean
        MyPojo li4() {
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

        @Getter
        private MyPojo myPojo;



        public MyBfpp(@Qualifier("zhang3") MyPojo myPojo) {
            this.myPojo = myPojo;
        }

        public MyBfpp(){
            System.out.println("no arg constructor");
        }


    }


    @Autowired
    MyBfpp myBfpp;

    @Test
    public void test01() {
        Assertions.assertNotNull(myBfpp);
        Assertions.assertNotNull(myBfpp.getMyPojo());
        Assertions.assertEquals("zhang3", myBfpp.getMyPojo().getName());
    }

}
