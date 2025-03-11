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
    // OK
    public static final String ENV_KEY_DASH_UPPER = "MY_A";

    // OK
    public static final String ENV_KEY_DASH_LOWER = "my_a";
    // OK
    public static final String ENV_KEY_DASH_MIX = "MY_a";
    // OK, 但shell环境下无法 export 成功
    public static final String ENV_KEY_DOT = "my.a";

    public static final String PROP_KEY = "my.a";

    @Configuration
    public static class Conf {

    }

    @Value("${" + PROP_KEY + "}")
    String a;

    @Autowired
    ConfigurableEnvironment env;


    @Test
    public void test01() {
        // 正常情况下， 句号 无法在shell环境中被 export。
        // spring boot 的 会
        // - 将 dot  `.` 替换成  underscores`_`
        // - 删除减号 dashes `-`
        // 转换成大写
        String ENV_KEY = ENV_KEY_DASH_MIX;

        String envA = System.getenv(ENV_KEY);
        System.out.println("ENV[" + ENV_KEY + "] = " + envA);
        System.out.println("a = " + a);
        Assertions.assertTrue(StringUtils.isNotBlank(envA),
                () -> "environment variable `" + ENV_KEY + "` is required");

        Assertions.assertEquals(envA, a);

        List<String> propertySourceNames = env.getPropertySources().stream()
                .map(PropertySource::getName)
                .collect(Collectors.toList());
        System.out.println("============ propertySourceNames : " + propertySourceNames);
    }
}
