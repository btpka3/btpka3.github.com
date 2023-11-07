package me.test.first.spring.boot.test.bfpp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 帮定义构造函数依赖注入的实现代码
 *
 * @see AutowiredAnnotationBeanPostProcessor#determineCandidateConstructors
 */
@SpringBootTest(
        classes = {
                Bfpp02Test.Conf.class,
                Bfpp02Test.MyBfpp.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bfpp02Test {


    @Configuration
    public static class Conf {
    }


    @Test
    public void x() {
        System.out.println("aaa");
    }

    @Component
    public static class MyBfpp implements BeanFactoryPostProcessor {

        public MyBfpp(ConfigurableListableBeanFactory beanFactory) {

        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // FIXME: 如何过去对应的 spring profile.
            System.out.println("111222333");
            System.out.println(beanFactory);

        }
    }

}