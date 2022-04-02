package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

/**
 * 测试目的：
 * 针对 @Configuration + @Bean + @Lazy ,
 * 通过 @EventListener 机制提前触发找bean，
 * 避免 服务 ready 后找bean而引发RT冲高后回落。
 * 该解法可行，但需要手动对每个 @Lazy注入的地方添加代码，太 ugly
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject06Test {

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
            return pojo;
        }

        @Bean
        MyPreloader myPreloader() {
            return MyPreloader.INSTANCE;
        }
    }


    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;


    @Test
    public void test() {

        System.out.println(getClass() + "#test start");

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            String nameStr = myPojo2.getNameStr();
            long endTime = System.nanoTime();
            long rt = endTime - startTime;
            System.out.printf("%3d : %15s : %9d%n", i, nameStr, rt);
        }

        System.out.println(getClass() + "#test start");
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
        public void stopped(ContextStoppedEvent event) {
            System.out.println("ContextStoppedEvent");
        }

    }
}
