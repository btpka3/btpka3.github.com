package me.test.first.spring.boot.test;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

@SpringBootTest(
        classes = {FactoryBeanTest.Conf.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Component
@Slf4j
public class FactoryBeanTest {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo zhang3() {
            return MyPojo.builder()
                    .name("zhang3")
                    .build();
        }

        @Bean
        MyPojo li4() {
            return MyPojo.builder()
                    .name("li4")
                    .build();
        }

        @Bean
        MyFactoryBean myFactoryBean() {
            return new MyFactoryBean();
        }
    }

    @Autowired
    MyBean myBean;

    /**
     * 验证 通过 FactoryBean 创建的 bean 是普通 bean（非 BPP， BFPP），【不】可以： 自动 注入相关字段（Autowired, *Aware）
     * 请查看 FactoryBean 的注释，明确提到 :
     * "FactoryBean is a programmatic contract. Implementations are not supposed to rely on annotation-driven injection or other reflective facilities"
     */

    @Test
    public void test01() {
        Assertions.assertNotNull(myBean);
        Assertions.assertNull(myBean.getApplicationContext());
        Assertions.assertNull(myBean.getMyPojo());
    }


    public static class MyFactoryBean implements FactoryBean<MyBean> {

        @Override
        public MyBean getObject() throws Exception {
            return new MyBean();
        }

        @Override
        public Class<?> getObjectType() {
            return MyBean.class;
        }
    }

    @Data
    @Builder(toBuilder = true)
    public static class MyPojo {
        private String name;
    }


    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyBean implements ApplicationContextAware, ApplicationListener<ApplicationContextEvent> {

        ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Autowired
        @Qualifier("zhang3")
        MyPojo myPojo;

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            System.out.println("MyBean : onApplicationEvent : " + event);
        }
    }


}