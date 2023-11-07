package me.test.first.spring.boot.test.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 示例的 Ordered+BeanPostProcessor 实现。
 */
public class DemoOrderedBpp implements BeanPostProcessor, Ordered {

    protected final AtomicBoolean hasProcessed = new AtomicBoolean(false);
    private ConfigurableListableBeanFactory beanRegistry;
    private ApplicationContext applicationContext;
    private List<String> earlyInitBeanNames;

    public DemoOrderedBpp(
            ConfigurableListableBeanFactory beanRegistry,
            ApplicationContext applicationContext,
            @Value("my.early.init.beans") List<String> earlyInitBeanNames
    ) {
        // 不要在构造函数内做业务逻辑，该构造函数的调用顺序不受 Ordered 影响。
        // 注意：构造函数的入参如果有其他 bean，这这些bean也会提前初始化。
        this.beanRegistry = beanRegistry;
        this.applicationContext = applicationContext;
        this.earlyInitBeanNames = earlyInitBeanNames;
    }

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (hasProcessed.compareAndSet(false, true)) {
            process();
        }
        return bean;
    }

    protected void process() {
        // 示例：编程方式新注入一些 spring bean
        beanRegistry.registerSingleton("xxxBean", "xxxStr");
        // 示例：编程方式 设置别名
        beanRegistry.registerAlias("xxxBean", "x1Bean");
        // 示例：针对不特定的bean，编程方式提前初始化。
        if (earlyInitBeanNames != null) {
            for (String earlyInitBeanName : earlyInitBeanNames) {
                applicationContext.getBean(earlyInitBeanName);
            }
        }
        // 示例：获取 spring 使用的 profile
        applicationContext.getEnvironment().getActiveProfiles();
    }
}
