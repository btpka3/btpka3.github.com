package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class Inject03Test {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1(
                @Qualifier("pojo2") MyPojo myPojo
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            pojo.setMyPojo(myPojo);
            return pojo;
        }

        @Bean
        MyPojo pojo2(
                @Lazy @Qualifier("pojo1") MyPojo myPojo
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            pojo.setMyPojo(myPojo);
            return pojo;
        }
    }

    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;

    @Autowired
    ConfigurableApplicationContext ctx;

    @Test
    public void test() {
        System.out.println(getClass() + "#test start");

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            // 成功：相比 Inject01Test， 使用 @Lazy 后成功
            // 但：org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency 被调用了3次
            String nameStr = myPojo2.getNameStr();
            long endTime = System.nanoTime();
            long rt = endTime - startTime;
            System.out.printf("%3d : %15s : %9d%n", i, nameStr, rt);
        }

        System.out.println(getClass() + "#test start");


    }

    @Disabled
    @Test
    public void test02() {
        System.out.println("---------- test02 start");
        ctx.getBeanFactory().getBeanNamesIterator().forEachRemaining(bean -> {
            try {
                final BeanDefinition beanDefinition = ctx.getBeanFactory().getBeanDefinition(bean);
                System.out.println("Bean '" + bean + "' isLazyInit=" + beanDefinition.isLazyInit());
            } catch (NoSuchBeanDefinitionException e) {
            }
        });
        System.out.println("---------- test02 end");
    }

    @Disabled
    @Test
    public void test03() {
        System.out.println("---------- test03 start");
    }
}
