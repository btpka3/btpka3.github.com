package me.test.first.spring.boot.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.WeakHashMap;

@Service
public class InitTimingBeanPostProcessor
    implements BeanPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(InitTimingBeanPostProcessor.class);

    private Map<Object, TimingItem> db = new WeakHashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        synchronized (db) {
            db.computeIfAbsent(bean, (b) -> {
                TimingItem i = new TimingItem();
                i.setBeanClass(bean.getClass());
                i.setBeanName(beanName);
                i.setStartTime(System.currentTimeMillis());
                i.setBeanHashCode(bean.hashCode());
                return i;
            });
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        TimingItem i = db.get(bean);
        if (i != null) {
            i.setEndTime(System.currentTimeMillis());
        }
        return bean;
    }

    @EventListener
    public void printOnSpringRefresh(ContextRefreshedEvent e) {
        printInitTop100();
    }


    public void printInitTop100() {

        String str = NewTimingBeanFactoryPostProcessor.top100(db.values());
        log.info("InitTop100\n" + str);
    }


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }


}
