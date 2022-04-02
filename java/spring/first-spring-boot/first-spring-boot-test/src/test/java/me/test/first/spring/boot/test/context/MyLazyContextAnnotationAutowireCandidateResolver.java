package me.test.first.spring.boot.test.context;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author dangqian.zll
 * @date 2022/4/2
 * @see <a href="https://www.concretepage.com/spring/example_customautowireconfigurer_spring>Spring CustomAutowireConfigurer Example</a>
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency
 * @see AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)
 * @see ContextAnnotationAutowireCandidateResolver#getLazyResolutionProxyIfNecessary(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String)
 * @see CustomAutowireConfigurer
 * @see QualifierAnnotationAutowireCandidateResolver
 * @see ContextAnnotationAutowireCandidateResolver
 * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], org.springframework.cglib.proxy.MethodProxy)
 */
public class MyLazyContextAnnotationAutowireCandidateResolver extends ContextAnnotationAutowireCandidateResolver {

    private List<Object> list = new ArrayList<>(32);


    protected Object buildLazyResolutionProxy(final DependencyDescriptor descriptor, final @Nullable String beanName) {
        BeanFactory beanFactory = getBeanFactory();
        Assert.state(beanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        final DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;

        TargetSource ts = new TargetSource() {
            private Object resolvedTarget = null;

            @Override
            public Class<?> getTargetClass() {
                return descriptor.getDependencyType();
            }

            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public Object getTarget() {
                if (resolvedTarget != null) {
                    return resolvedTarget;
                }

                Set<String> autowiredBeanNames = (beanName != null ? new LinkedHashSet<>(1) : null);
                Object target = dlbf.doResolveDependency(descriptor, beanName, autowiredBeanNames, null);
                if (target == null) {
                    Class<?> type = getTargetClass();
                    if (Map.class == type) {
                        return Collections.emptyMap();
                    } else if (List.class == type) {
                        return Collections.emptyList();
                    } else if (Set.class == type || Collection.class == type) {
                        return Collections.emptySet();
                    }
                    throw new NoSuchBeanDefinitionException(descriptor.getResolvableType(),
                            "Optional dependency not present for lazy injection point");
                }
                if (autowiredBeanNames != null) {
                    for (String autowiredBeanName : autowiredBeanNames) {
                        if (dlbf.containsBean(autowiredBeanName)) {
                            dlbf.registerDependentBean(autowiredBeanName, beanName);
                        }
                    }
                }
                resolvedTarget = target;
                return resolvedTarget;
            }

            @Override
            public void releaseTarget(Object target) {
                // org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor 会每次目标被调用后，finally 调用该方法
                // 我们避免重置为空，防止每次都查找 bean
                //this.resolvedTarget = null;
            }
        };

        ProxyFactory pf = new ProxyFactory();
        pf.setTargetSource(ts);
        Class<?> dependencyType = descriptor.getDependencyType();
        if (dependencyType.isInterface()) {
            pf.addInterface(dependencyType);
        }
        Object obj = pf.getProxy(dlbf.getBeanClassLoader());
        if (obj != null) {
            list.add(obj);
        }
        return obj;
    }

    public void triggerPreload() {
        for (int i = 0; i < list.size(); i++) {
            triggerPreloadObject(list.get(i));
        }
    }

    public void triggerPreloadObject(Object obj) {
        if (obj == null) {
            return;
        }
        obj.toString();
    }
}
