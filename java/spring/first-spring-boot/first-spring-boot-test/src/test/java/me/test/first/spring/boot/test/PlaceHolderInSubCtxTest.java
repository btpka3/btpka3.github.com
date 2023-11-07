package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/9/14
 */
public class PlaceHolderInSubCtxTest {


    @Configuration
    @PropertySources(
            @PropertySource(name = "myParentPs", value = "classpath:/me/test/first/spring/boot/test/PlaceHolderInSubCtxTest.parent.yaml")
    )
    public static class ParentConf {


//        @Bean
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer1() {
//            return new PropertySourcesPlaceholderConfigurer();
//        }

        @Bean
        MyPojo p1(
                @Value("${a:parent_default_a1}") String a,
                @Value("${b:parent_default_b1}") String b,
                @Value("${c:parent_default_c1}") String c
        ) {
            return new MyPojo(a, b, c);
        }

    }

    @Configuration
    @PropertySources(
            @PropertySource(name = "myChildPs", value = "classpath:/me/test/first/spring/boot/test/PlaceHolderInSubCtxTest.child.yaml")
    )
    public static class ChildConf {

//        @Bean
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer1() {
//            PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//            c.setLocalOverride(true);
//            return c;
//        }

        @Bean
        MyPojo p2(
                @Value("${a:child_default_a2}") String a,
                @Value("${b:child_default_b2}") String b,
                @Value("${c:child_default_c2}") String c
        ) {
            return new MyPojo(a, b, c);
        }

    }


    @AllArgsConstructor
    @Data
    public static class MyPojo {

        private String a;
        private String b;
        private String c;

    }

    @Test
    public void test() {
        AnnotationConfigApplicationContext parentAppCtx = new AnnotationConfigApplicationContext();
        parentAppCtx.register(ParentConf.class);
        parentAppCtx.refresh();
        parentAppCtx.start();

        System.out.println("================= 111");
        // p1={"a":"a1","b":"b1","c":"parent_default_c1"}
        System.out.println("p1=" + JSON.toJSONString(parentAppCtx.getBean("p1")));

        AnnotationConfigApplicationContext childAppCtx = new AnnotationConfigApplicationContext();
        childAppCtx.setParent(parentAppCtx);
        childAppCtx.register(ChildConf.class);
        childAppCtx.refresh();
        childAppCtx.start();

        System.out.println("================= 222");
        // p1={"a":"a1","b":"b1","c":"parent_default_c1"}
        System.out.println("p1=" + JSON.toJSONString(childAppCtx.getBean("p1")));
        // p2={"a":"a1","b":"b1","c":"c2"}
        System.out.println("p2=" + JSON.toJSONString(childAppCtx.getBean("p2")));

        ConfigurableEnvironment parentEnv = parentAppCtx.getEnvironment();
        ConfigurableEnvironment childEnv = childAppCtx.getEnvironment();
        Assertions.assertNotSame(parentEnv, childEnv);


        Map<String, PropertyResourceConfigurer> placeholderConfigurer = childAppCtx.getBeansOfType(PropertyResourceConfigurer.class);
        System.out.println("placeholderConfigurer = " + placeholderConfigurer.keySet());


    }

}

