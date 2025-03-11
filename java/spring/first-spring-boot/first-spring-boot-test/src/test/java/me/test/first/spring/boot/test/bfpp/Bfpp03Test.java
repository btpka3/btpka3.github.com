package me.test.first.spring.boot.test.bfpp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 验证 BeanFactoryPostProcessor 中访问 Environment
 *
 * @author dangqian.zll
 * @date 2025/3/6
 */
@SpringBootTest(
        classes = {
                Bfpp03Test.Conf.class,
                Bfpp03Test.MyBfpp.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bfpp03Test {
    @Configuration
    public static class Conf {
    }


    @Test
    public void x() {
        System.out.println("aaa");
    }


    @Component
    public static class MyBfpp implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            //Environment env = beanFactory.getBean(Environment.class);
            System.out.println("beanFactory.getClass()=" + beanFactory.getClass());
            Environment env = (Environment) beanFactory.getSingleton("environment");
            // org.springframework.boot.ApplicationEnvironment
            System.out.println("env.getClass()=" + env.getClass());
            System.out.println("env.getActiveProfiles() = " + Arrays.toString(env.getActiveProfiles()));
            System.out.println("os.name = " + env.getProperty("os.name"));

            AbstractEnvironment absEnv = (AbstractEnvironment) env;
            System.out.println("absEnv.getPropertySources().size() = " + absEnv.getPropertySources().size());
        }
    }
}
