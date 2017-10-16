package me.test.boot.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.*
import org.springframework.format.support.FormattingConversionService
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory

/**
 *
 */
@Configuration
class AmqpConf {


    @Bean
    DefaultMessageHandlerMethodFactory messageHandlerMethodFactory(
            @Qualifier("rabbitMqCs")
                    FormattingConversionService rabbitMqCs
    ) {
        DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();
        defaultFactory.setConversionService(rabbitMqCs);
        return defaultFactory;
    }

    @Bean(name = RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RabbitListenerAnnotationBeanPostProcessor rabbitListenerAnnotationProcessor(
            MessageHandlerMethodFactory handlerFac
    ) {
        RabbitListenerAnnotationBeanPostProcessor bpp = new RabbitListenerAnnotationBeanPostProcessor();
        bpp.setMessageHandlerMethodFactory(handlerFac);
        return bpp;
    }

    @Bean(name = RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME)
    public RabbitListenerEndpointRegistry defaultRabbitListenerEndpointRegistry() {
        return new RabbitListenerEndpointRegistry();
    }


    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(
            ObjectMapper objectMapper
    ) {

        ClassMapper cm = new ClassMapper() {
            @Override
            void fromClass(Class<?> clazz, MessageProperties properties) {
                // 比如：MessageProperties#getHeaders().put("__TypeId__", clazz.getName())
            }

            @Override
            Class<?> toClass(MessageProperties properties) {

                // 比如根据 MessageProperties#getHeaders().get("__TypeId__")
                // 条件的转为特定的Java类型。貌似只能统一转为一个类型。
                // 之后，会使用 Converter 实现类转为其他Java类型。
                // 参考： https://jira.spring.io/browse/AMQP-461
                return Map.class
            }
        }
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setJsonObjectMapper(objectMapper)
        return converter;
    }

}
