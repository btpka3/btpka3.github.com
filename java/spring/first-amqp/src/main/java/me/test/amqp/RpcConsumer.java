package me.test.amqp;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RpcConsumer {

    public static void main(String[] args) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RpcBroker.QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(new HelloWorldHandler()));
        container.start();
    }

    public static class HelloWorldHandler {
        private final AtomicInteger counter = new AtomicInteger();

        public String handleMessage(String text) {
            System.out.println("Received: " + text);
            return text + "=" + counter.incrementAndGet();
        }
    }
}
