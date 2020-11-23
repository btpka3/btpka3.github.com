package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author dangqian.zll
 * @date 2019-12-04
 */
@SpringBootTest
@ContextConfiguration(classes = {
        PlaceHolder004Test.Conf.class,
        PlaceHolder004Test.Conf2.class
})
public class PlaceHolder004Test {

    public PlaceHolder004Test() {
        System.out.println(System.getProperty("xxx"));
    }


    @Configuration
    @PropertySource("classpath:${xxx:xxx.properties}")
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

    @Configuration
    @ImportResource(locations = "classpath*:/${p4:x.xml}")
    public static class Conf2 {


    }

    @Value("${a}")
    String a;

    @Autowired
    MyBean m;

    // bash: ./gradlew -i :first-spring-boot-test:test -Dxxx=PlaceHolder003Test.properties --tests me.test.first.spring.boot.test.PlaceHolder004Test

    @Test
    public void test01() {
        System.out.println("a = " + a + ", m=" + m);
    }
}
