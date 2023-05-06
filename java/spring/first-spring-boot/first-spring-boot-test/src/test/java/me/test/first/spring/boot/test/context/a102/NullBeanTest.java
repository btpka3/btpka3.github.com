package me.test.first.spring.boot.test.context.a102;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author dangqian.zll
 * @date 2022/12/20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class NullBeanTest {

    @Configuration
    public static class Conf {

        @Bean(name = "myAaa")
        String aaa() {
            return null;
        }

    }

    @Autowired
    protected ApplicationContext applicationContext;

    @Qualifier("myAaa")
    @Autowired
    protected String aaa;


    @SneakyThrows
    @Test
    public void test01() {
        System.out.println("====================");
        System.out.println("==================== aaa = " + this.aaa);
        System.out.println("==================== getBean('myAaa') = " + applicationContext.getBean("myAaa"));
    }
}
