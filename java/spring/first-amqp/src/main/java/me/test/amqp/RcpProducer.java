package me.test.amqp;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class RcpProducer {
    private final static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRoutingKey(RcpBroker.QUEUE_NAME);

        for (int i = 0; i < 3; i++) {
            String msg = "Hello World " + counter.incrementAndGet();
            Object reply = template.convertSendAndReceive(msg);
            System.out.println("send msg :" + msg + ", reply = " + reply);
        }

        connectionFactory.destroy();
    }

    @Scheduled(fixedRate = 3000)
    public void sendMessage() {

    }
}
