package me.test.first.spring.boot.test.context.a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger("spring");


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        Stream.of(beanFactory.getBeanDefinitionNames()).forEach(beanDefName -> {
            System.out.println("MyBeanFactoryPostProcessor--------" + beanDefName);
        });

    }

}
