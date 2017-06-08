package me.test.boot.controller

import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.*

import java.lang.annotation.*

@Target([
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE
])
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(
                exclusive = "true",
                arguments = @Argument(
                        name = "x-message-ttl",
                        value = "10000",
                        type = "java.lang.Integer"
                )
        ),
        exchange = @Exchange(
                value = "z.topic",
                type = ExchangeTypes.FANOUT
        )
))
@interface MyMsgListener {

}