package me.test.first.spring.boot.test.context;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject01Test {

    @Configuration
    public static class Conf {

        @Lazy
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
                @Qualifier("pojo1") MyPojo myPojo
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            pojo.setMyPojo(myPojo);
            return pojo;
        }
    }

    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;

    @Test
    public void test() {
        // 失败：
        // 将运行失败 @Configuration + @Bean 等同于通过 constructor 进行注入，
        // 且 缺少基于 spring xml 注入相关机制，将触发: BeanCurrentlyInCreationException
        System.out.println("myPojo2 = " + JSON.toJSONString(myPojo2));
    }
}
