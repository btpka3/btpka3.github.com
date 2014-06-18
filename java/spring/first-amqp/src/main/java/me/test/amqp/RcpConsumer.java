package me.test.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RcpConsumer {

    public static void main(String[] args) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RcpBroker.QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(new HelloWorldHandler()));
        container.start();
    }

    public static class HelloWorldHandler {
        public String handleMessage(String text) {
            System.out.println("Received: " + text);
            return text + "=333";
        }
    }
}
