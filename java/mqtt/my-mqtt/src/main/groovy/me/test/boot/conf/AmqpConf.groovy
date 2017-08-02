package me.test.boot.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
class AmqpConf {

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
