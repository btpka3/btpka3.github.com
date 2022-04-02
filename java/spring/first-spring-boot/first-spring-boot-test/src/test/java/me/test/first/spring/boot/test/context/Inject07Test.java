package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试目的：针对 @Configuration + @Bean + @Lazy ,
 * 通过 BeanFactoryPostProcessor + 自定义 ContextAnnotationAutowireCandidateResolver + @EventListener
 * 更 优雅、无代码侵入 地 提前触发找bean，
 * 避免 服务 ready 后找bean而引发RT冲高后回落。
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject07Test {

    /**
     * 通过静态机制，减少spring 找bean，以方便观测注入详情。
     */
    static Map<String, MyPojo> map = new HashMap<>(4);

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1(
                @Qualifier("pojo2") MyPojo myPojo
        ) {
            System.out.println("pojo1");
            MyPojo pojo = new MyPojo();

            pojo.setName("zhang3");
            pojo.setMyPojo(myPojo);

            map.put("pojo1", pojo);
            return pojo;
        }

        @Bean
        MyPojo pojo2(
                @Lazy @Qualifier("pojo1") MyPojo myPojo
        ) {
            System.out.println("pojo2");
            MyPojo pojo = new MyPojo();

            pojo.setName("li4");
            pojo.setMyPojo(myPojo);
            map.put("pojo2", pojo);
            return pojo;
        }


        @Bean
        MyLazyBeanFactoryPostProcessor myLazyBeanFactoryPostProcessor() {
            MyLazyBeanFactoryPostProcessor postProcessor = new MyLazyBeanFactoryPostProcessor();
            return postProcessor;
        }
    }

    @Test
    public void test() {
        System.out.println(getClass() + "#test start");

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            String nameStr = map.get("pojo2").getNameStr();
            long endTime = System.nanoTime();
            long rt = endTime - startTime;
            System.out.printf("%3d : %15s : %9d%n", i, nameStr, rt);
        }

        System.out.println(getClass() + "#test end");
    }
}
