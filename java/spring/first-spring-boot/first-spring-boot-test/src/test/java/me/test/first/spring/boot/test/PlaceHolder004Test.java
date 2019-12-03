package me.test.first.spring.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dangqian.zll
 * @date 2019-12-04
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                PlaceHolder004Test.Conf.class,
        }
)
public class PlaceHolder004Test {

    @Configuration
    public static class Conf {

        @Bean
        public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:PlaceHolder003Test.properties");
            PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
            c.setLocations(resource);
            return c;
        }
    }

    @Value("${a}")
    String a;

    @Test
    public void test01() {
        System.out.println("a = " + a);
    }
}
