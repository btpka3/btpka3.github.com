package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 验证 BPP 及其 依赖的 Bean 的 字段上  @Value 的值的resolve。。
 *
 * @author dangqian.zll
 * @date 2023/9/21
 */
public class PlaceHolderValueAtBppFieldTest {


    /**
     * 自定的初始化动作 在构造函数内完成，故 BeanPostProcessor 的相关接口实现保持默认即可。
     */
    @Component("p1")
    @Data
    public static class MyPojo implements BeanPostProcessor, ApplicationContextAware {


        @Value("${a:default_a1}")
        private String a;

        public MyPojo(@Value("${a:default_a2}") String str, BeanFactory beanFactory) {
            System.out.println("================= 003");
            System.out.println("MyPojo.init() : str=" + str);
            System.out.println("MyPojo.init() : beanFactory=" + beanFactory.getClass().getName());
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

        /**
         * 如果不声明 place holder, 则 AbstractApplicationContext#finishBeanFactoryInitialization 会在 所有bean
         * 都初始化完成之后 才兜底将 Environment 作为 EmbeddedValueResolver，但此时就太晚了。
         *
         * @return
         */
        @Bean
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer1() {
            return new PropertySourcesPlaceholderConfigurer();
        }
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
