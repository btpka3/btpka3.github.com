package me.test.first.spring.boot.test;

import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.bfpp.Bfpp01Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

/**
 * @author dangqian.zll
 * @date 2023/11/7
 */

@SpringBootTest(
        classes = Bfpp01Test.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class InjectBfTest {


    @Configuration
    public static class Conf {

    }

    @Autowired
    ConfigurableListableBeanFactory beanFactory;

    @Test
    public void test() {
        Assertions.assertNotNull(beanFactory);
        Assertions.assertTrue(beanFactory instanceof DefaultListableBeanFactory);
    }
}
