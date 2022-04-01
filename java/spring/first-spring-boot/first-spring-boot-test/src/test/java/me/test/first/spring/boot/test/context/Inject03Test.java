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
public class Inject03Test {

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
                @Lazy @Qualifier("pojo1") MyPojo myPojo
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
        // 成功：相比 Inject01Test， 使用 @Lazy 后成功
        System.out.println("myPojo2 = " + JSON.toJSONString(myPojo2));
    }
}
