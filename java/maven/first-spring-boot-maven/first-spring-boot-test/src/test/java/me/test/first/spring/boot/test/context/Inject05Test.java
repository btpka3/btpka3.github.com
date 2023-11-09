package me.test.first.spring.boot.test.context;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject05Test {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1(
                @Qualifier("pojo2") MyPojo myPojo
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            pojo.setMyPojo(myPojo);
            return pojo;
        }

        @Bean
        MyPojo pojo2(
                @Lazy @Qualifier("pojo1") MyPojo myPojo
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            pojo.setMyPojo(myPojo);
            return pojo;
        }

        @EventListener
        public void handEvent(ContextStartedEvent event) {
            System.out.println("Context Start Event received.");
        }

        @EventListener
        public void handEvent(ContextRefreshedEvent event) {
            MyPojo pojo1 = (MyPojo) event.getApplicationContext().getBean("pojo1");
            MyPojo pojo2 = (MyPojo) event.getApplicationContext().getBean("pojo2");
            System.out.println("pojo1.getNameStr() = " + pojo1.getNameStr());
            System.out.println("pojo2.getNameStr() = " + pojo2.getNameStr());
        }

    }

    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;

    @Test
    public void test() {
        // 成功：相比 Inject03Test， 确认 Spring Event
        System.out.println("myPojo2 = " + JSON.toJSONString(myPojo2));
    }
}
