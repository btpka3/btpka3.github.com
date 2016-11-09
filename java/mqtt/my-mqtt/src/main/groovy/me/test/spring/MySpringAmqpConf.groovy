package me.test.spring

import me.test.MY
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MySpringAmqpConf {

// # RABBIT (RabbitProperties)
// # org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration

//    @Bean
//    ConnectionFactory connectionFactory() {
//        ConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUsername(MY.MQTT_USER);
//        factory.setPassword(MY.MQTT_PWD);
//        factory.setVirtualHost(MY.AMQP_VIRTUAL_HOST);
//        factory.setHost(MY.AMQP_HOST);
//        factory.setPort(MY.AMQP_PORT);
//        // factory.setApplicationContext(appContext)
//        return factory
//    }
//    @Bean
//    AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        println "--------11112222222222"
//        return new RabbitTemplate(connectionFactory);
//    }
//    @Bean
//    AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }

    @Bean
    MySpringAmqpListener mySpringAmqpListener() {
        return new MySpringAmqpListener()
    }

    @Bean
    AAA aaa() {
        new AAA()
    }

}