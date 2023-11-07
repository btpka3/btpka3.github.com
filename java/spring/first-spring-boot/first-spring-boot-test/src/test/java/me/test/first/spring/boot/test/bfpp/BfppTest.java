package me.test.first.spring.boot.test.bfpp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootTest(
        classes = BfppTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class BfppTest {


    @Configuration
    public static class Conf {
        @Bean
        MyBfpp xxx() {
            return new MyBfpp();
        }

    }


    @Test
    public void x() {
        System.out.println("aaa");
    }

    public static class MyBfpp implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // FIXME: 如何过去对应的 spring profile.
            System.out.println("111222333");
            System.out.println(beanFactory);

        }
    }

}