package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * 用以验证 在 @Bean 方法参数   @Value 的值是在哪里 resolve 的.
 *
 * @author dangqian.zll
 * @date 2023/9/21
 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#postProcessProperties
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency
 */
public class PlaceHolderValueAtBeanMethodTest {


    @Data
    public static class MyPojo {

        private String a;

    }

    @Configuration
    @Import(MyPojo.class)
    @PropertySource(name = "myPs", value = "classpath:/me/test/first/spring/boot/test/PlaceHolderValueFieldTest.yaml")
    public static class AppCtxConf {

        @Bean
        MyPojo myPojo(@Value("${a:default_a1}") String a) {
            MyPojo myPojo = new MyPojo();
            myPojo.setA(a);
            return myPojo;
        }
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
