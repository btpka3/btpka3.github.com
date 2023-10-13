package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 用以定义 字段上  @Value 的值是在哪里 resolve 的.
 *
 * @author dangqian.zll
 * @date 2023/9/21
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#postProcessProperties
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency
 */
public class PlaceHolderValueFieldTest {


    @Component("p1")
    @Data
    public static class MyPojo {

        @Value("${a:default_a1}")
        private String a;

    }

    @Configuration
    @Import(MyPojo.class)
    @PropertySource(name = "myPs", value = "classpath:/me/test/first/spring/boot/test/PlaceHolderValueFieldTest.yaml")
    public static class AppCtxConf {

//        @Bean
//        MyPojo myPojo() {
//            return new MyPojo();
//        }
    }


    @Test
    public void test() {
        AnnotationConfigApplicationContext parentAppCtx = new AnnotationConfigApplicationContext();
        parentAppCtx.register(AppCtxConf.class);
        parentAppCtx.refresh();
        parentAppCtx.start();

        System.out.println("================= 111");
        System.out.println("MyPojo=" + JSON.toJSONString(parentAppCtx.getBean(MyPojo.class)));
    }
}
