package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.stereotype.Component;

/**
 * 定位为何 BFPP 的，以及依赖的 spring bean 的 @Value 为何无法被 resolve。
 * <p>
 * 字段里的 @Value 依赖 AutowiredAnnotationBeanPostProcessor，
 * 通过 @Bean/@Component 初始化带参数的 构造函数的bean ，也依赖 AutowiredAnnotationBeanPostProcessor。
 * 而 AutowiredAnnotationBeanPostProcessor 是 BPP,
 * 其初始化和注册是在 BFPP 之后，故在初始化 BFPP 时其不可用，
 * <p>
 * 即便 让 BFPP 上 通过 @DependsOn 传递初始化 AutowiredAnnotationBeanPostProcessor， 但仍未将其注册，故仍然不可用。
 *
 * @author dangqian.zll
 * @date 2023/9/21
 */
public class PlaceHolderValueAtBfppFieldTest {


    @DependsOn(org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Component("p1")
    @Data
    public static class MyPojo implements BeanFactoryPostProcessor, ApplicationContextAware {


        @Value("${a:default_a1}")
        private String a;

        public MyPojo(@Value("${a:default_a2}") String str) {
            System.out.println("================= 003");
            System.out.println("MyPojo.init() : str=" + str);

        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            System.out.println("================= 001");
            // DefaultListableBeanFactory
            System.out.println("beanFactory.getClass()=" + beanFactory.getClass().getName());
            if (beanFactory instanceof EnvironmentCapable) {
                Environment environment = ((EnvironmentCapable) beanFactory).getEnvironment();

                System.out.println("beanFactory.environment.getProperty(\"a\") = " + environment.getProperty("a"));
            }
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            System.out.println("================= 002");
            System.out.println("applicationContext.getClass()=" + applicationContext.getClass().getName());
            Environment environment = applicationContext.getEnvironment();
            System.out.println("applicationContext.environment.getProperty(\"a\") = " + environment.getProperty("a"));
        }
    }

    @Configuration
    @Import(MyPojo.class)
    @PropertySource(name = "myPs", value = "classpath:/me/test/first/spring/boot/test/PlaceHolderValueFieldTest.yaml")
    public static class AppCtxConf {

//        @Bean
//        MyPojo myPojo() {
//            return new MyPojo();
//        }
    }


    @Test
    public void test() {
        AnnotationConfigApplicationContext parentAppCtx = new AnnotationConfigApplicationContext();
        parentAppCtx.register(AppCtxConf.class);

        parentAppCtx.refresh();
        parentAppCtx.start();

        System.out.println("================= 111");
        System.out.println("MyPojo=" + JSON.toJSONString(parentAppCtx.getBean(MyPojo.class)));
    }
}
