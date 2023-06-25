package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 验证多个placeHolder。
 * 结论：允许重复定义，但对于不全的配置项的properties 需要设置 ignore-unresolvable=true
 * 多个 placeholder 都匹配的话，以第一个为准。
 *
 * @see org.springframework.context.config.ContextNamespaceHandler
 * @see org.springframework.context.support.PropertySourcesPlaceholderConfigurer
 */
public class PlaceHolder006Test {


    @Test
    public void testImport01() {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("PlaceHolder006Test.xml");
        appCtx.start();

        List<String> configurerBeanNames = Arrays.asList(appCtx.getBeanNamesForType(PropertySourcesPlaceholderConfigurer.class));
        System.out.println("PropertySourcesPlaceholderConfigurer beans = " + configurerBeanNames);
        Assertions.assertEquals(2, configurerBeanNames.size());

        {
            String myVar = (String) appCtx.getBean("myVarA");
            Assertions.assertEquals("___a001___", myVar);
        }
        {
            String myVar = (String) appCtx.getBean("myVarB");
            Assertions.assertEquals("___b001___", myVar);
        }
        {
            String myVar = (String) appCtx.getBean("myVarC");
            Assertions.assertEquals("___c002___", myVar);
        }

    }
}
