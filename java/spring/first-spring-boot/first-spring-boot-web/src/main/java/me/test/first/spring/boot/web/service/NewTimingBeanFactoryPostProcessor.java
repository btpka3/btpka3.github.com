package me.test.first.spring.boot.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.support.InstantiationStrategy;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NewTimingBeanFactoryPostProcessor
    implements Ordered, BeanFactoryPostProcessor,InstantiationStrategy {

    private static final Logger log = LoggerFactory.getLogger(NewTimingBeanFactoryPostProcessor.class);

    private Map<String, TimingItem> db = new WeakHashMap<>();

    private InstantiationStrategy delegate = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner) throws BeansException {
        TimingItem i = new TimingItem();
        i.setBeanClass(bd.getBeanClass());
        i.setBeanName(beanName);
        i.setStartTime(System.currentTimeMillis());

        synchronized (db) {
            db.putIfAbsent(beanName, i);
        }
        Object obj = delegate.instantiate(bd, beanName, owner);

        i.setBeanHashCode(obj.hashCode());
        i.setEndTime(System.currentTimeMillis());
        return obj;

    }

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner, Constructor<?> ctor, Object... args) throws BeansException {


        TimingItem i = new TimingItem();
        i.setBeanClass(bd.getBeanClass());
        i.setBeanName(beanName);
        i.setStartTime(System.currentTimeMillis());

        synchronized (db) {
            db.putIfAbsent(beanName, i);
        }
        Object obj = delegate.instantiate(bd, beanName, owner, ctor, args);

        i.setBeanHashCode(obj.hashCode());
        i.setEndTime(System.currentTimeMillis());
        return obj;
    }

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner, Object factoryBean, Method factoryMethod, Object... args) throws BeansException {
        TimingItem i = new TimingItem();
        i.setBeanClass(bd.getBeanClass());
        i.setBeanName(beanName);
        i.setStartTime(System.currentTimeMillis());

        synchronized (db) {
            db.putIfAbsent(beanName, i);
        }
        Object obj = delegate.instantiate(bd, beanName, owner, factoryBean, factoryMethod, args);

        i.setBeanHashCode(obj.hashCode());
        i.setEndTime(System.currentTimeMillis());
        return obj;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // class com.alibaba.citrus.springext.support.context.InheritableListableBeanFactory

        if (configurableListableBeanFactory instanceof AbstractAutowireCapableBeanFactory) {
            AbstractAutowireCapableBeanFactory fac = (AbstractAutowireCapableBeanFactory) configurableListableBeanFactory;
            fac.setInstantiationStrategy(this);
        }
    }

    @EventListener
    public void printOnSpringRefresh(ContextRefreshedEvent e) {
        printNewTop100();
    }


    public void printNewTop100() {
        String str = top100(db.values());
        log.info("NewTop100\n" + str);
    }


    public static String top100(Collection<TimingItem> items){
        StringBuilder buf = new StringBuilder();
        AtomicInteger idx = new AtomicInteger(0);
        items.stream()

            // 按照初始化执行的时间逆序排序（耗时越长，排名越靠前）
            .sorted(Comparator.comparingLong((TimingItem i) -> {
                if (i.getStartTime() == null || i.getEndTime() == null) {
                    return 0L;
                }
                return i.getEndTime() - i.getStartTime();
            }).reversed())

            // 只要前 100 名
            .limit(100)
            .forEach(i -> {
                idx.addAndGet(1);
                String s = String.format(
                    "%5d | %7d | %s | %d | %s%n",
                    idx.get(),
                    i.getEndTime() - i.getStartTime(),
                    i.getBeanClass().getName(),
                    i.getBeanHashCode(),
                    i.getBeanName()
                );
                buf.append(s);
            });
        return buf.toString();
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }


}
