package me.test.first.spring.boot.test.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author dangqian.zll
 * @date 2023/2/28
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class FactoryBeanTest implements ApplicationContextAware {


    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Import(XxxFactoryBean.class)
    @Configuration
    public static class Conf {

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void x() {
        Object bean = applicationContext.getBean("xxxList");
        assertTrue(bean instanceof List);
        List list = (List) bean;
        assertEquals(2, list.size());
        {
            Xxx xxx = (Xxx) list.get(0);
            assertEquals("aaa", xxx.getName());
        }
        {
            Xxx xxx = (Xxx) list.get(1);
            assertEquals("bbb", xxx.getName());
        }
    }

    @Component("xxxList")
    public static class XxxFactoryBean extends AbstractFactoryBean<List<Xxx>> {

        @SuppressWarnings("rawtypes")
        @Override
        public Class<List> getObjectType() {
            return List.class;
        }

        @Override
        protected List<Xxx> createInstance() throws Exception {
            return Arrays.asList(
                    Xxx.builder().name("aaa").build(),
                    Xxx.builder().name("bbb").build()
            );
        }
    }


    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Xxx {
        public String name;
    }
}
