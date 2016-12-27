package me.test.conf

import org.h2.tools.Server
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.datasource.init.DatabasePopulator
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator

import javax.sql.DataSource

/**
 * 因为 BeanPostProcessor 类型的 bean 会在其他普通 bean 前先实例化，
 * 通过 @Autowired 可以在 DataSource 初始化前，先启动 H2 服务器
 */
@Configuration
class H2Conf implements BeanPostProcessor {

    @Bean(initMethod = "start", destroyMethod = "stop")
    static Server h2Server(@Value('${my.h2.tcp.conf}') String[] conf) {
        println("-------------------dddd : " + Arrays.toString(conf))
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
            DataSource dataSource = bean

            // 先创建所需的表结构
            DatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScripts(
                    resourceLoader.getResource("classpath:schema.sql"),
                    resourceLoader.getResource("classpath:data.sql"),
            )
            DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        }
        return bean
    }
}
