package me.test.first.spring.boot.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证 使用来自env的 PropertySource
 */
@EnableConfigurationProperties
@SpringBootTest(classes = PlaceHolder008Test.Conf.class)

public class PlaceHolder008Test {
    public static final String ENV_KEY = "my.a";

    @Configuration
    public static class Conf {

    }

    @Value("${" + ENV_KEY + "}")
    String a;

    @Autowired
    ConfigurableEnvironment env;


    @Test
    public void test01() {

        String envA = System.getenv(ENV_KEY);
        Assertions.assertTrue(StringUtils.isNotBlank(envA),
                () -> "environment variable `" + ENV_KEY + "` is required");

        Assertions.assertEquals(envA, a);

        List<String> propertySourceNames = env.getPropertySources().stream()
                .map(PropertySource::getName)
                .collect(Collectors.toList());
        System.out.println("============ propertySourceNames : " + propertySourceNames);
    }
}
