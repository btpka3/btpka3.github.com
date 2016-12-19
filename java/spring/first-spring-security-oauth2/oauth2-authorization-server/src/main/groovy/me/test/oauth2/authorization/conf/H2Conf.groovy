package me.test.oauth2.authorization.conf

import org.h2.tools.Server
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 因为 BeanPostProcessor 类型的 bean 会在其他普通 bean 前先实例化，
 * 通过 @Autowired 可以在 DataSource 初始化前，先启动 H2 服务器
 */
@Configuration
class H2Conf implements BeanPostProcessor {

    @Bean(initMethod = "start", destroyMethod = "stop")
    static Server h2Server(@Value('${my.h2.tcp.conf}') String[] conf) {
        return Server.createTcpServer(conf)
    }

    @Autowired
    Server h2Server

    // ----------------------- 空白实现
    @Override
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean
    }

    @Override
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean
    }
}
