package me.test.first.spring.boot.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
@ContextConfiguration
@SpringJUnitConfig(
//        classes = { UserAccountPropertiesTest.TestConfig.class },
        initializers = {ConfigDataApplicationContextInitializer.class}
)
@SpringBootTest(
        classes = ConfigurationPropertiesTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:me/test/first/spring/boot/test/ConfigurationPropertiesTest.application.yaml"})
@ConfigurationPropertiesScan
@Slf4j
public class ConfigurationPropertiesTest {

    @Configuration
    public static class Conf {
        @Bean
        @ConfigurationProperties(prefix = "me.test")
        MyPojo myPojo() {
            return new MyPojo();
        }

//        @Bean
//        PropertiesPropertySource propertiesPropertySource(ApplicationContext applicationContext) {
//            Resource resource = applicationContext.getResource("classpath:me/test/first/spring/boot/test/ConfigurationPropertiesTest.application.yaml");
//
//            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//            factory.setResources(resource);
//
//            Properties properties = factory.getObject();
//
//            return new PropertiesPropertySource(resource.getFilename(), properties);
//        }
    }

    @Autowired
    MyPojo myPojo;

    @Value("${me.test.user-name}")
    String userName;

    @Value("${me.test.hobbies}")
    List<String> hobbies;

    @Value("${me.test.addresses}")
    List<String> addresses;

    @Test
    public void test01() {
        System.out.println("userName = " + userName);
        System.out.println("hobbies = " + hobbies);
        System.out.println("addresses = " + addresses);
        System.out.println("myojo = " + myPojo);
        Assertions.assertNotNull(myPojo);
        Assertions.assertEquals("zhang3", myPojo.getUserName());
        Assertions.assertEquals(38, myPojo.getAge());

        Assertions.assertEquals(1, hobbies.size());
        Assertions.assertTrue(hobbies.contains("${me.test.hobbies}"));
        Assertions.assertEquals(2, addresses.size());
        Assertions.assertTrue(addresses.contains("henan"));
        Assertions.assertTrue(addresses.contains("zhejiang"));
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPojo {
        private String userName;
        private Integer age;
        private Set<String> hobbies;

    }


}
