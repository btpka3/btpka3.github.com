package com.github.btpka3.first.spring.boot3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/4/14
 * @see StandardEnvironment#customizePropertySources(MutablePropertySources)
 * @see SystemEnvironmentPropertySource#resolvePropertyName(String)
 */
public class StandardEnvironmentTest {



    @Test
    public void testDot() {
        Map<String, Object> envMap = new HashMap<>();
        envMap.put("A_A", "xxx");
        StandardEnvironment env = new StandardEnvironment() {
            @Override
            public Map<String, Object> getSystemEnvironment() {
                return envMap;
            }
        };
        env.getProperty(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);
        Assertions.assertEquals("xxx", env.getProperty("a.a"));
        Assertions.assertEquals("xxx", env.getProperty("A.a"));
    }

    @Test
    public void testHyphens() {
        Map<String, Object> envMap = new HashMap<>();
        envMap.put("A_A", "xxx");
        StandardEnvironment env = new StandardEnvironment() {
            @Override
            public Map<String, Object> getSystemEnvironment() {
                return envMap;
            }
        };
        env.getProperty(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);
        Assertions.assertEquals("xxx", env.getProperty("a-a"));
        Assertions.assertEquals("xxx", env.getProperty("A-a"));
    }
}
