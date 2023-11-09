package me.test.first.spring.boot.test;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * @author dangqian.zll
 * @date 2023/7/10
 * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
 * @see javax.annotation.Resource
 * @see javax.annotation.PostConstruct
 * @see javax.annotation.PreDestroy
 * @see javax.annotation.Resources
 */
@SpringBootTest
@ContextConfiguration
public class Jsr250Test {


    @Configuration
    public static class Conf {

        @Bean
        Jsr250Test.Aaa aaa() {
            Aaa aaa = new Aaa();
            aaa.setName("a001");
            return aaa;
        }

//        @Bean
//        Jsr250Test.Bbb bbb() {
//            Bbb bbb = new Bbb();
//            bbb.setName("b001");
//            return bbb;
//        }
    }

    @Autowired
    Jsr250Test.Aaa aaa;


    @Test
    public void test01() {
        Assertions.assertNotNull(aaa);
        Assertions.assertEquals("a001", aaa.getName());
        Assertions.assertNotNull(aaa.getBbb());
        Assertions.assertEquals("b001", aaa.getBbb().getName());
    }


    @Data
    public static class Aaa {
        private String name;

        @Resource(name = "bbb")
        private Bbb bbb;
    }

    @Data
    public static class Bbb {
        private String name;
    }

}
