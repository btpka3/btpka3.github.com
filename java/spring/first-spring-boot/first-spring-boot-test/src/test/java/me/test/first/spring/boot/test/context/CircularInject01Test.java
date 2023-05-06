package me.test.first.spring.boot.test.context;

import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.context.circular01.Aaa;
import me.test.first.spring.boot.test.context.circular01.Bbb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dangqian.zll
 * @date 2022/10/17
 */
@SpringBootTest(
        classes = CircularInject01Test.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@EnableAutoConfiguration
//@TestPropertySource
@Slf4j
public class CircularInject01Test {

    @Configuration
    @ComponentScan(basePackageClasses = Aaa.class)
    public static class Conf {

    }

    @Autowired
    private Bbb bbb;
    @Autowired
    private Aaa aaa;

    @Autowired
    private ApplicationContext appCtx;

    @Test
    public void x() {
        System.out.println("aaa.name = " + aaa.getName() + ", aaa.bbb.name = " + (aaa.getBbb() != null ? aaa.getBbb().getName() : "-"));
        System.out.println("bbb.name = " + bbb.getName() + ", bbb.aaa.name = " + (bbb.getAaa() != null ? bbb.getAaa().getName() : "-"));

        Aaa aaa = (Aaa) appCtx.getBean("myAaa");
        System.out.println("appCtx.getBean(\"myAaa\") = " + aaa);
    }


}

