package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject04Test {

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
                @Lazy @Qualifier("pojo1") MyPojo myPojo,
                ApplicationContext applicationContext
        ) {
            // FIXME
            System.out.println("pojo1 = " + applicationContext.getBean("pojo1").getClass().getName());

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
        // 失败：相比 Inject03Test，在初始化过程中，不能通过 ApplicationContext 获取 bean
        // 否则会触发: BeanCurrentlyInCreationException
    }
}
