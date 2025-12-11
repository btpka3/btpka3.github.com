package com.github.btpka3.first.spring.boot4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@ActiveProfiles({"p1", "p2", "xxx"})
@ContextConfiguration(classes = {
        ProfileTest.Conf.class,
})
public class ProfileTest {

    @Autowired
    ApplicationContext appCtx;

    @Autowired
    Environment env;

    @ImportResource("classpath*:com/github/btpka3/first/spring/boot4/ProfileTest.xml")
    @Configuration
    public static class Conf {

        /**
         * 与
         */
        @Profile({"p1 & p2"})
        @Bean(name = "a")
        public String a() {
            return "aaa";
        }

        /**
         * 或
         */
        @Profile({"p1", "p2"})
        @Bean(name = "b")
        public String b() {
            return "bbb";
        }

        /**
         * 非
         */
        @Profile({"!p2"})
        @Bean(name = "c")
        public String c() {
            return "ccc";
        }
    }

    @Test
    public void test01() {

        System.out.println("activeProfiles = " + Arrays.asList(env.getActiveProfiles()));

        List<String> beanNames = Arrays.asList(
                "a", "b", "c",
                "xmlA", "xmlB", "xmlC"
        );

        for (String beanName : beanNames) {

            boolean contains = appCtx.containsBean(beanName);
            Object beanValue = null;
            if (contains) {
                beanValue = appCtx.getBean(beanName);
            }
            System.out.println("bean [" + beanName + "] contains=" + contains + ", beanValue=" + beanValue + " .");
        }

    }
}
