package me.test.first.spring.boot.test;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class CustomYamlPropertySource implements ApplicationContextInitializer , PriorityOrdered {


    @Override
    public void initialize(ConfigurableApplicationContext context) {
        //context.getEnvironment().getPropertySources().addFirst(myPropertySource);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
