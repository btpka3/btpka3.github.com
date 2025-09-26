package me.test.first.spring.boot.test.context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/26
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
@Slf4j
public class BeanInitOrderTest {

    static AtomicInteger seq = new AtomicInteger(0);

    static void log(String msg) {
        log.info("========= INIT_ORDER[{}] : {}", seq.getAndIncrement(), msg);
    }

    @Configuration
    public static class MyConf {
        @Bean
        public MyPojo myPojo() {
            return new MyPojo();
        }

        @Bean(name = "myBean", initMethod = "initMethod", destroyMethod = "destroyMethod")
        public MyBean myBean() {
            return new MyBean();
        }

    }

    public static class MyPojo {

    }

    public static class MyBean implements
            EnvironmentAware, ResourceLoaderAware, EmbeddedValueResolverAware, ApplicationEventPublisherAware, MessageSourceAware, ApplicationStartupAware, ApplicationContextAware {

        private MyPojo myPojo;

        @Autowired
        public void setMyPojo(MyPojo myPojo) {
            this.myPojo = myPojo;
            log("setMyPojo");
        }

        public void initMethod() {
            log("initMethod");
        }

        public void destroyMethod() {
            log("destroyMethod");
        }

        /**
         * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
         */
        @PostConstruct
        public void postConstruct() {
            log("@PostConstruct");
        }

        /**
         * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
         */
        @PreDestroy
        public void preDestroy() {
            log("@PreDestroy");
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            log("setApplicationContext");
        }

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            log("setApplicationEventPublisher");
        }

        @Override
        public void setApplicationStartup(ApplicationStartup applicationStartup) {
            log("setApplicationStartup");
        }

        @Override
        public void setEmbeddedValueResolver(StringValueResolver resolver) {
            log("setEmbeddedValueResolver");
        }

        @Override
        public void setEnvironment(Environment environment) {
            log("setEnvironment");
        }

        @Override
        public void setMessageSource(MessageSource messageSource) {
            log("setMessageSource");
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            log("setResourceLoader");
        }
    }


    @Autowired
    MyBean myBean;

    @Test
    public void test() {
        System.out.println("myBean = " + myBean);
    }

    /*
========= INIT_ORDER[0] : setMyPojo
========= INIT_ORDER[1] : setEnvironment
========= INIT_ORDER[2] : setEmbeddedValueResolver
========= INIT_ORDER[3] : setResourceLoader
========= INIT_ORDER[4] : setApplicationEventPublisher
========= INIT_ORDER[5] : setMessageSource
========= INIT_ORDER[6] : setApplicationStartup
========= INIT_ORDER[7] : setApplicationContext
========= INIT_ORDER[8] : @PostConstruct
========= INIT_ORDER[9] : initMethod
========= INIT_ORDER[10] : @PreDestroy
========= INIT_ORDER[11] : destroyMethod
     */
}
