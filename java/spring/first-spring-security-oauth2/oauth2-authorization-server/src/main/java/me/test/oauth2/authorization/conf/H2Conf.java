package me.test.oauth2.authorization.conf;

import org.h2.tools.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;

import java.sql.*;

/**
 * 因为 BeanPostProcessor 类型的 bean 会在其他普通 bean 前先实例化，
 * 通过 @Autowired 可以在 DataSource 初始化前，先启动 H2 服务器
 */
@Configuration
class H2Conf implements BeanPostProcessor {


    // H2ConsoleAutoConfiguration
    @Bean(initMethod = "start", destroyMethod = "stop")
    public static Server h2Server(@Value("${my.h2.tcp.conf}") String[] conf) throws SQLException {
        return Server.createTcpServer(conf);
    }

    @Autowired
    private Server h2Server;

    // ----------------------- 空白实现
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
