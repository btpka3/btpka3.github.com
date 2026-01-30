package com.github.btpka3.first.spring.boot3.bean.factory;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @date 2025/11/26
 */
@SpringBootTest(
        classes = InstantiationAwareBeanPostProcessorTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class InstantiationAwareBeanPostProcessorTest {


    @Configuration
    public static class Conf {

        @Bean
        Person zhang3() {
            return new Person("zhang3", null);
        }

        @Bean
        Person li4(
                Person zhang3
        ) {
            return new Person("li4", zhang3);
        }

        @Bean
        Person wang5(
                Person li4
        ) {
            return new Person("wang5", li4);
        }

        @Bean
        MyIabpp myIabpp() {
            return new MyIabpp();
        }

    }

    @Autowired
    Person wang5;

    @Test
    public void test01() {
        log.info("=============== test01 : wang5={}", wang5);
    }


    @Component
    public static class MyIabpp implements InstantiationAwareBeanPostProcessor {

        Set<String> NAMES = new HashSet<>(Arrays.asList("zhang3", "li4", "wang5"));

        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
            if (NAMES.contains(beanName)) {
                log.info("=============== postProcessBeforeInstantiation : beanName={}", beanName);
            }

            return null;
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) {
            if (NAMES.contains(beanName)) {
                log.info("=============== postProcessAfterInstantiation : beanName={}", beanName);
            }

            return true;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (NAMES.contains(beanName)) {
                log.info("=============== postProcessBeforeInitialization : beanName={}", beanName);
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) {
            if (NAMES.contains(beanName)) {
                log.info("=============== postProcessAfterInitialization : beanName={}", beanName);
            }
            return bean;
        }

    }

    public static class Person {
        public String name;
        public Person father;

        public Person(String name, Person father) {
            this.name = name;
            this.father = father;
        }

        @PostConstruct
        public void init() {
            log.info("=============== Person#init : name={}", name);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", father=" + father +
                    '}';
        }
    }
}
