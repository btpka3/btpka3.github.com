package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ContextConfiguration("classpath:/me/test/first/spring/boot/test/context/Inject06Test.xml")
@ContextConfiguration
public class Inject06Test {

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

            MyPreloader.INSTANCE.addPreload(() -> myPojo.toString());

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

            MyPreloader.INSTANCE.addPreload(() -> myPojo.toString());


            pojo.setName("li4");
            pojo.setMyPojo(myPojo);
            map.put("pojo2", pojo);
            return pojo;
        }

        @Bean
        MyPreloader myPreloader() {
            return MyPreloader.INSTANCE;
        }
    }


    public static class MyPreloader {

        public List<Runnable> runnableList = new ArrayList<>(32);

        public static MyPreloader INSTANCE = new MyPreloader();

        public void addPreload(Runnable runnable) {
            runnableList.add(runnable);
        }

        @EventListener
        public void preload(ContextRefreshedEvent event) {
            System.out.println("ContextRefreshedEvent");
            System.out.println("preload start");
            for (int i = 0; i < runnableList.size(); i++) {
                Runnable runnable = runnableList.get(i);
                runnable.run();
            }
            System.out.println("preload end");
        }

        @EventListener
        public void started(ContextStartedEvent event) {
            System.out.println("ContextStartedEvent");
        }

        @EventListener
        public void started(ContextStoppedEvent event) {
            System.out.println("ContextStoppedEvent");
        }

    }


    @Test
    public void test() {
        // 成功：相比 Inject03Test
        // 使用 Spring Event，通过 ContextRefreshedEvent 提前触发找bean。
        // 假设这里是启动后要高QPS调用的业务代码，
        // debug 执行该语句时，观测 org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency 不会再执行了

        System.out.println(getClass() + "#test start");

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            // 成功：相比 Inject01Test， 使用 @Lazy 后成功
            // 但：org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency 被调用了3次
            String nameStr = map.get("pojo2").getNameStr();
            long endTime = System.nanoTime();
            long rt = endTime - startTime;
            System.out.printf("%3d : %15s : %9d%n", i, nameStr, rt);
        }

        System.out.println(getClass() + "#test start");
    }
}
