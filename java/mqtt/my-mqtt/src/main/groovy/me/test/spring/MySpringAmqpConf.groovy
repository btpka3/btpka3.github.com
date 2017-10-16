package me.test.spring

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.*
import org.springframework.amqp.rabbit.retry.MessageRecoverer
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
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

//    @Bean
//    AAA aaa() {
//        new AAA()
//    }

//    // 所有异常都认为是严重异常，不重复执行。
//    @Autowired
//    void confSimpleMessageListenerContainer(SimpleRabbitListenerContainerFactory   c) {
//
////        FatalExceptionStrategy s = new FatalExceptionStrategy() {
////
////            @Override
////            boolean isFatal(Throwable t) {
////                return true
////            }
////        }
////        ConditionalRejectingErrorHandler errorHandler =new ConditionalRejectingErrorHandler(s);
////        c.setErrorHandler(errorHandler)
//        c.setDefaultRequeueRejected(false);
//    }



//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer,
//            ConnectionFactory connectionFactory
//    ) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//
//        factory.setDefaultRequeueRejected(false);
//        return factory;
//    }


//
//    @Bean
//    @ConditionalOnMissingBean
//    public SimpleRabbitListenerContainerFactoryConfigurer rabbitListenerContainerFactoryConfigurer(
//            ObjectProvider<MessageConverter> messageConverter,
//            ObjectProvider<MessageRecoverer> messageRecoverer,
//            RabbitProperties properties
//    ) {
//
//        FatalExceptionStrategy s = new FatalExceptionStrategy() {
//
//            @Override
//            boolean isFatal(Throwable t) {
//                return true
//            }
//        }
//        ConditionalRejectingErrorHandler errorHandler = ConditionalRejectingErrorHandler(s);
//
//
//        SimpleRabbitListenerContainerFactoryConfigurer configurer = new SimpleRabbitListenerContainerFactoryConfigurer(){
//            public void configure(
//                    SimpleRabbitListenerContainerFactory factory,
//                                  ConnectionFactory connectionFactory) {
//                super.configure(factory,connectionFactory);
//                factory.setErrorHandler(errorHandler)
//            }
//        }
//        configurer.setMessageConverter(messageConverter.getIfUnique());
//        configurer.setMessageRecoverer(messageRecoverer.getIfUnique());
//        configurer.setRabbitProperties(properties);
//        configurer.configure()
//        return configurer;
//    }
//
//    @Bean
//    SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer(){
//
//        SimpleRabbitListenerContainerFactoryConfigurer c = new SimpleRabbitListenerContainerFactoryConfigurer();
//
//    }

}