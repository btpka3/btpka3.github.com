package me.test.first.spring.boot.test.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * @author dangqian.zll
 * @date 2022/4/2
 * @see <a href="https://www.concretepage.com/spring/example_customautowireconfigurer_spring>Spring CustomAutowireConfigurer Example</a>
 */
public class MyLazyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactoryArg) throws BeansException {
        beanFactory = beanFactoryArg;
        DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactoryArg;
        if (!(dlbf.getAutowireCandidateResolver() instanceof MyLazyContextAnnotationAutowireCandidateResolver)) {
            dlbf.setAutowireCandidateResolver(new MyLazyContextAnnotationAutowireCandidateResolver());
        }
    }

    @EventListener
    public void preload(ContextRefreshedEvent event) {

        System.out.println("ContextRefreshedEvent");
        System.out.println("preload start");
        if (beanFactory == null) {
            return;
        }

        DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
        AutowireCandidateResolver resolver = dlbf.getAutowireCandidateResolver();
        if (!(resolver instanceof MyLazyContextAnnotationAutowireCandidateResolver)) {
            return;
        }

        MyLazyContextAnnotationAutowireCandidateResolver myLazyResolver = (MyLazyContextAnnotationAutowireCandidateResolver) resolver;
        myLazyResolver.triggerPreload();
        System.out.println("preload end");
    }
}
