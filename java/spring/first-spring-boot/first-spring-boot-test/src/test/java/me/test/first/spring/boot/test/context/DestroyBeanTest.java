package me.test.first.spring.boot.test.context;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

/**
 * 验证重新创建bean.
 *
 * @author dangqian.zll
 * @date 2025/10/20
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class DestroyBeanTest {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1(
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            pojo.setMyPojo(null);
            return pojo;
        }

        @Bean
        BeanDefinitionRegistryPostProcessor pojo2BPP() {
            return new BeanDefinitionRegistryPostProcessor() {
                @Override
                public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                    BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(MyPojo.class);
                    bdb.addPropertyValue("name", "li4");
                    bdb.addPropertyReference("myPojo", "pojo1");
                    BeanDefinition bd = bdb.getBeanDefinition();
                    registry.registerBeanDefinition("pojo2", bd);
                }

                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                    // noop
                }
            };
        }

        @Bean
        MyEventListener myEventListener() {
            return new MyEventListener();
        }
    }

    @Autowired
    MyPojo pojo1;
    @Autowired
    MyPojo pojo2;

    @Autowired
    DefaultListableBeanFactory beanFactory;
    @Autowired
    ApplicationContext applicationContext;


    @Test
    public void test() {
        log.info("====== xxxxx getDependenciesForBean beans :{}", Arrays.toString(beanFactory.getDependenciesForBean("xxxxx")));
        log.info("====== pojo1 getDependenciesForBean beans :{}", Arrays.toString(beanFactory.getDependenciesForBean("pojo1")));
        log.info("====== pojo2 getDependenciesForBean beans :{}", Arrays.toString(beanFactory.getDependenciesForBean("pojo2")));
        log.info("====== xxxxx dependent beans :{}", Arrays.toString(beanFactory.getDependentBeans("xxxxx")));
        log.info("====== pojo1 dependent beans :{}", Arrays.toString(beanFactory.getDependentBeans("pojo1")));
        log.info("====== pojo2 dependent beans :{}", Arrays.toString(beanFactory.getDependentBeans("pojo2")));


        Assertions.assertTrue(beanFactory.containsSingleton("pojo1"));
        Assertions.assertTrue(beanFactory.containsSingleton("pojo2"));
        Assertions.assertTrue(beanFactory.containsBean("pojo1"));
        Assertions.assertTrue(beanFactory.containsBean("pojo2"));

        int p1HashCode = System.identityHashCode(pojo1);
        int p2HashCode = System.identityHashCode(pojo2);
        log.info("====== pojo1:{}, hashCode={}", JSON.toJSON(pojo1), p1HashCode);
        log.info("====== pojo2:{}, hashCode={}", JSON.toJSON(pojo2), p2HashCode);
        log.info("====== beanFactory:{}", beanFactory);
        beanFactory.destroySingleton("xxxxx");
        beanFactory.destroySingleton("pojo1");
        //beanFactory.removeBeanDefinition("pojo1");


        log.info("====== beanFactory:containsBeanDefinition(\"pojo1\")={}", beanFactory.containsBeanDefinition("pojo1"));
        log.info("====== beanFactory:containsBeanDefinition(\"pojo2\")={}", beanFactory.containsBeanDefinition("pojo2"));

        Assertions.assertFalse(beanFactory.containsSingleton("pojo1"));
        Assertions.assertFalse(beanFactory.containsSingleton("pojo2"));
        // 注意：containsBean 也会判断 是否包含 containsBeanDefinition，故这里是 true
        Assertions.assertTrue(beanFactory.containsBean("pojo1"));
        Assertions.assertTrue(beanFactory.containsBean("pojo2"));
        Assertions.assertTrue(beanFactory.containsBeanDefinition("pojo1"));
        Assertions.assertTrue(beanFactory.containsBeanDefinition("pojo2"));


        // 由于没有删除相关bean定义，所以再次获取时，会重新创建，且是新对象
        Object o2 = beanFactory.getBean("pojo2");
        int o2HashCode = System.identityHashCode(o2);
        log.info("====== beanFactory.getBean(\"pojo2\"):{}, hashCode={}", JSON.toJSON(o2), o2HashCode);
        Assertions.assertTrue(beanFactory.containsSingleton("pojo1"));
        Assertions.assertTrue(beanFactory.containsSingleton("pojo2"));
        Assertions.assertTrue(beanFactory.containsBean("pojo1"));
        Assertions.assertTrue(beanFactory.containsBean("pojo2"));


        Object o1 = beanFactory.getBean("pojo1");
        int o1HashCode = System.identityHashCode(o1);
        // FIXME: 期望报错，或者是null，实际 仍然能获取，且只不变。
        log.info("====== beanFactory.getBean(\"pojo1\"):{}, hashCode={}", JSON.toJSON(o1), o1HashCode);

        Assertions.assertNotEquals(p1HashCode, o1HashCode);
        Assertions.assertNotEquals(p2HashCode, o2HashCode);

        log.info("====== applicationContext#id={}", applicationContext.getId());

    }


    /**
     *
     */
    public static class MyEventListener {
        @EventListener
        public void onEvent(ContextClosedEvent event) {
            Exception e = new RuntimeException("JUST CHECK STACK TRACE");
            log.info("@EventListener,ContextClosedEvent", e);
        }
    }
}
