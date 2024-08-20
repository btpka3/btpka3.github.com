package me.test.first.spring.boot.test.jasypt;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * @author dangqian.zll
 * @date 2024/8/19
 */
@SpringBootTest
@ContextConfiguration(classes = {
        PasswordEncTest.Conf.class,
        //EnableEncryptablePropertiesConfiguration.class
})
@Import({
        EnableEncryptablePropertiesConfiguration.class
})
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/jasypt/PasswordEncTest-01.yaml"
})
public class PasswordEncTest {

    @Value("${my.demo.key}")
    private String key;


    @Configuration
    //@EnableEncryptableProperties
    public static class Conf {

    }

    @Test
    public void test01() {
        System.out.println("key=" + key);
        Assertions.assertEquals("123456", key);
    }
}
