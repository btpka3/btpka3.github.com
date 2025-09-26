package me.test.first.spring.boot.test.context;

import me.test.first.spring.boot.test.context.a.CccSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2019-06-28
 * @see StandardEnvironment
 */
public class ContextTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new MyClassPathXmlApplicationContext("a.xml");
        CccSerivce cccSerivce = applicationContext.getBean(CccSerivce.class);
        System.out.println("" + cccSerivce);
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        System.out.println("" + env);
        String propKey = "a.b.c";
        System.out.println(propKey + "=" + env.getProperty(propKey));
    }

    public static class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

        public MyClassPathXmlApplicationContext(String configLocation) throws BeansException {
            super(configLocation);
        }

        @Override
        protected void initPropertySources() {
            Map<String, Object> map = new HashMap<>(128);
            map.put("a.b.c", "999");
            MapPropertySource ps = new MapPropertySource("myPs", map);
            getEnvironment().getPropertySources().addFirst(ps);

            super.initPropertySources();
        }
    }
}
