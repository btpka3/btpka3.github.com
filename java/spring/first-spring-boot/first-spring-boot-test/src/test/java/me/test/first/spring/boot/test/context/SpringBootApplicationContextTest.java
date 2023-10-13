package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringValueResolver;

/**
 * @author dangqian.zll
 * @date 2023/9/21
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {
        SpringBootApplicationContextTest.Conf.class,
})
public class SpringBootApplicationContextTest {

    @Configuration
    @Import(MyPojo.class)
    public static class Conf {


    }

    @Component
    public static class MyPojo implements
            ApplicationContextAware,
            EnvironmentAware,
            EmbeddedValueResolverAware,
            ResourceLoaderAware,
            ApplicationEventPublisherAware,
            MessageSourceAware,
            ApplicationStartupAware,
            BeanNameAware,
            BeanClassLoaderAware,
            BeanFactoryAware,
            ImportAware {

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            System.out.println("======MyPojo#setApplicationContext() : applicationContext=" + applicationContext.getClass().getName());
        }

        @Override
        public void setEnvironment(Environment environment) {
            System.out.println("======MyPojo#setEnvironment() : environment=" + environment.getClass().getName());
        }

        @Override
        public void setEmbeddedValueResolver(StringValueResolver resolver) {
            System.out.println("======MyPojo#setEmbeddedValueResolver() : resolver=" + resolver.getClass().getName());
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            System.out.println("======MyPojo#setResourceLoader() : resourceLoader=" + resourceLoader.getClass().getName());
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            System.out.println("======MyPojo#setBeanFactory() : beanFactory=" + beanFactory.getClass().getName());
        }

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            System.out.println("======MyPojo#setApplicationEventPublisher() : applicationEventPublisher=" + applicationEventPublisher.getClass().getName());
        }

        @Override
        public void setApplicationStartup(ApplicationStartup applicationStartup) {
            System.out.println("======MyPojo#setApplicationStartup() : applicationStartup=" + applicationStartup.getClass().getName());
        }

        @Override
        public void setMessageSource(MessageSource messageSource) {
            System.out.println("======MyPojo#setMessageSource() : messageSource=" + messageSource.getClass().getName());
        }

        @Override
        public void setImportMetadata(AnnotationMetadata importMetadata) {
            System.out.println("======MyPojo#setImportMetadata() : importMetadata=" + importMetadata.getClass().getName());
        }

        @Override
        public void setBeanClassLoader(ClassLoader classLoader) {
            System.out.println("======MyPojo#setBeanClassLoader() : classLoader=" + classLoader.getClass().getName());
        }

        @Override
        public void setBeanName(String name) {
            System.out.println("======MyPojo#setBeanName() : name=" + name);
        }
    }

    @Test
    public void test() {
        System.out.println("=====done.");
    }

    /* Output: (webEnvironment = SpringBootTest.WebEnvironment.MOCK)

======MyPojo#setBeanName() : name=me.test.first.spring.boot.test.context.SpringBootApplicationContextTest$MyPojo
======MyPojo#setBeanClassLoader() : classLoader=sun.misc.Launcher$AppClassLoader
======MyPojo#setBeanFactory() : beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory
======MyPojo#setEnvironment() : environment=org.springframework.boot.web.servlet.context.ApplicationServletEnvironment  # ⭕️
======MyPojo#setEmbeddedValueResolver() : resolver=org.springframework.beans.factory.config.EmbeddedValueResolver
======MyPojo#setResourceLoader() : resourceLoader=org.springframework.web.context.support.GenericWebApplicationContext  # ⭕️
======MyPojo#setApplicationEventPublisher() : applicationEventPublisher=org.springframework.web.context.support.GenericWebApplicationContext
======MyPojo#setMessageSource() : messageSource=org.springframework.web.context.support.GenericWebApplicationContext
======MyPojo#setApplicationStartup() : applicationStartup=org.springframework.core.metrics.DefaultApplicationStartup
======MyPojo#setApplicationContext() : applicationContext=org.springframework.web.context.support.GenericWebApplicationContext
======MyPojo#setImportMetadata() : importMetadata=org.springframework.core.type.StandardAnnotationMetadata
=====done.

     */

    /* Output: (webEnvironment = SpringBootTest.WebEnvironment.NONE)

======MyPojo#setBeanName() : name=me.test.first.spring.boot.test.context.SpringBootApplicationContextTest$MyPojo
======MyPojo#setBeanClassLoader() : classLoader=sun.misc.Launcher$AppClassLoader
======MyPojo#setBeanFactory() : beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory
======MyPojo#setEnvironment() : environment=org.springframework.boot.ApplicationEnvironment                                 # ⭕️
======MyPojo#setEmbeddedValueResolver() : resolver=org.springframework.beans.factory.config.EmbeddedValueResolver
======MyPojo#setResourceLoader() : resourceLoader=org.springframework.context.annotation.AnnotationConfigApplicationContext # ⭕️
======MyPojo#setApplicationEventPublisher() : applicationEventPublisher=org.springframework.context.annotation.AnnotationConfigApplicationContext
======MyPojo#setMessageSource() : messageSource=org.springframework.context.annotation.AnnotationConfigApplicationContext
======MyPojo#setApplicationStartup() : applicationStartup=org.springframework.core.metrics.DefaultApplicationStartup
======MyPojo#setApplicationContext() : applicationContext=org.springframework.context.annotation.AnnotationConfigApplicationContext
======MyPojo#setImportMetadata() : importMetadata=org.springframework.core.type.StandardAnnotationMetadata
=====done.

    */


}
