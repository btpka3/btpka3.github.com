package me.test.ex;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jpa.event.spi.JpaIntegrator;
import org.hibernate.service.ServiceRegistry;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

/**
 * 测试代码。学习、研究使用。请忽略。
 * 
 * FIXME 使用Hibernate4的SessionFactory，无法触发实体的{@code @}PrePersist、{@code @}PreUpdate 方法。
 * 参考：http://forum.spring.io/forum/spring-projects/data/46235-hibernate-3-2-persistent-lifecycle-annotations-not-work
 * 
 * @author zll
 *
 */
public class LocalSessionFactoryBeanEx extends LocalSessionFactoryBean {

    @Override
    protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
        final BootstrapServiceRegistryBuilder bootstrapServiceRegistryBuilder = new BootstrapServiceRegistryBuilder();
        bootstrapServiceRegistryBuilder.with(new JpaIntegrator());
        BootstrapServiceRegistry bootstrapServiceRegistry = bootstrapServiceRegistryBuilder.build();
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder(
                bootstrapServiceRegistry);
        // serviceRegistryBuilder.applySettings( configurationValues );
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        return sfb.buildSessionFactory(serviceRegistry);
    }

}
