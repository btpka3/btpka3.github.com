package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


@SpringBootTest(classes = TestYamlPropertySource2Test.Conf.class, webEnvironment = NONE)
@TestPropertySource(properties = {
        "spring.config.location=classpath:application.yaml,classpath:application-test.yaml"
})
public class TestYamlPropertySource2Test {

    @Configuration
    public static class Conf {
    }

    /**
     * 验证配置项
     */
    @Test
    void test01(
            @Autowired Environment env,
            @Value("${demo.key01:}") String value01,
            @Value("${demo.key02:}") String value02,
            @Value("${demo.key03:}") String value03,
            @Value("${demo.key11:}") String value11,
            @Value("${demo.key12:}") String value12,
            @Value("${demo.key13:}") String value13
    ) {
        assertEquals("value010", value01);
        assertEquals("value021", value02);
        assertEquals("value031", value03);
        assertEquals("value111", value11);
        assertEquals("value121", value12);
        assertEquals("", value13);
    }

}
