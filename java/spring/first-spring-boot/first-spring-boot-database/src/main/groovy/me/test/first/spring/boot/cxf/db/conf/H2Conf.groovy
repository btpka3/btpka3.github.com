package me.test.first.spring.boot.cxf.db.conf

import org.h2.tools.Server
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.io.ResourceLoader

import javax.sql.DataSource

/**
 * 因为 BeanPostProcessor 类型的 bean 会在其他普通 bean 前先实例化，
 * 通过 @Autowired 可以在 DataSource 初始化前，先启动 H2 服务器.
 *
 * 参考 MethodSecurityMetadataSourceAdvisor#registerBeanPostProcessors() 的逻辑
 * 实现 Ordered 接口防止 "h2Server" 被 AOP 而造成初始化错误。
 */
@Configuration
class H2Conf implements BeanPostProcessor, Ordered {

    static final Logger log = LoggerFactory.getLogger(H2Conf)

    @Bean(initMethod = "start", destroyMethod = "stop")
    static Server h2Server(@Value('${my.h2.tcp.conf}') String[] conf) {
        log.debug("-------------------dddd : " + Arrays.toString(conf))
        return Server.createTcpServer(conf)
    }

    @Autowired
    Server h2Server

    @Autowired
    ResourceLoader resourceLoader

    // ----------------------- 空白实现
    @Override
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean
    }

    @Override
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {

            log.info("================ postProcess : DataSource" + bean)
//            DataSource dataSource = bean
//
//            // 先创建所需的表结构
//            DatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//            databasePopulator.addScripts(
//                    resourceLoader.getResource("classpath:schema.sql"),
//                    resourceLoader.getResource("classpath:data.sql"),
//            )
//            DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        }
        return bean
    }

    @Override
    int getOrder() {
        return 0
    }
}
