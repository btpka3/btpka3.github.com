package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * 验证 &lt;import&gt; 与 &lt;beans profile="xxx" &gt; 共同使用
 *
 * 注意: import 的 location 支持 ant 风格的 通配符。
 *
 * @author dangqian.zll
 * @date 2021/7/28
 * @see <a href="https://docs.spring.io/spring-framework/docs/5.3.39/reference/html/core.html#resources-app-ctx-wildcards-in-resource-paths">2.8.2. Wildcards in Application Context Constructor Resource Paths</a>
 */
@SpringBootTest
@ActiveProfiles({"p1", "p2", "xxx"})
@ContextConfiguration(classes = {
        Profile2Test.Conf.class,
})
public class Profile2Test {

    @Autowired
    ApplicationContext appCtx;

    @Autowired
    Environment env;

    @ImportResource("classpath*:me/test/first/spring/boot/test/ProfileTest2.xml")
    @Configuration
    public static class Conf {

    }

    @Test
    public void test01() {

        System.out.println("activeProfiles = " + Arrays.asList(env.getActiveProfiles()));

        List<String> beanNames = Arrays.asList(
                "xmlA", "xmlB", "xmlX"
        );

        for (String beanName : beanNames) {

            boolean contains = appCtx.containsBean(beanName);
            Object beanValue = null;
            if (contains) {
                beanValue = appCtx.getBean(beanName);
            }
            System.out.println("bean [" + beanName + "] contains=" + contains + ", beanValue=" + beanValue + " .");
        }

    }
}
