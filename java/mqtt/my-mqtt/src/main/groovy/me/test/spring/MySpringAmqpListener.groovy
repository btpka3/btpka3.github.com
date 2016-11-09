package me.test.spring

import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware

import static me.test.MY.MQTT_COUNT

class MySpringAmqpListener implements MessageListener, ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher
    int i = -1;

    @Override
    void onMessage(Message message) {
        println "------------- MySpringAmqpListener received " + new String(message.body)
        i++
        if (i >= MQTT_COUNT - 1) {
            // 手动停止该线程
            //Thread.currentThread().interrupt()

            applicationEventPublisher.publishEvent(new SubFinishedEvent())
        }
    }

    @Override
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

    static class SubFinishedEvent {

    }
}