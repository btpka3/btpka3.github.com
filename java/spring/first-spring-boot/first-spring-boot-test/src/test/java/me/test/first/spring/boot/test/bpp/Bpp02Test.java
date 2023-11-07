package me.test.first.spring.boot.test.bpp;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 检查 BPP 数依赖注入 BeanFactory
 *
 * @author dangqian.zll
 * @date 2023/11/7
 */
@SpringBootTest(
        classes = {Bpp02Test.Conf.class, Bpp02Test.MyBfpp.class},

        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bpp02Test {


    @Configuration
    public static class Conf {

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


        /**
         * 通过构造函数依赖注入，提前初始化固定的一些 spring bean
         * @param beanRegistry
         * @param beanFactory1
         * @param applicationContext
         */
        public MyBfpp(
                ConfigurableListableBeanFactory beanRegistry,
                BeanFactory beanFactory1,
                ApplicationContext applicationContext
        ) {
            // 编程方式新注入一些 spring bean
            beanRegistry.registerSingleton("zhang3", MyPojo.builder()
                    .name("zhang3")
                    .build());
            Assertions.assertSame(beanRegistry, beanFactory1);

            // 针对不特定的bean，编程方式提前初始化。
            applicationContext.getBean("li4");
        }


    }


    @Autowired
    MyBfpp myBfpp;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test01() {
        MyPojo myPojo = (MyPojo) applicationContext.getBean("zhang3");
        Assertions.assertEquals("zhang3", myPojo.getName());
    }

}
