package me.test.first.spring.boot.test.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/4/14
 * @see <a href="https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.relaxed-binding.environment-variables">Binding From Environment Variables</a>
 */
public class SystemEnvironmentPropertySourceTest {
    @Test
    public void x() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("A-a", "xxx");

        SystemEnvironmentPropertySource src = new SystemEnvironmentPropertySource("env", map);
        System.out.println(src.getProperty("a.a"));;

    }
}
