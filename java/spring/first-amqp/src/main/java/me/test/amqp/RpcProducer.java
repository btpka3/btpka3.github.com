package me.test.amqp;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class RpcProducer {
    private final static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRoutingKey(RpcBroker.QUEUE_NAME);

        int count = 10;
        long totalTime = 0;
        for (int i = 0; i < count; i++) {
            String msg = "Hello World " + counter.incrementAndGet();

            long start = System.currentTimeMillis();

            Object reply = template.convertSendAndReceive(msg);

            long end = System.currentTimeMillis();
            long time = end - start;
            totalTime += time;

            System.out.println("send msg :" + msg + ", reply = " + reply + ", using time = " + time
                    + " millis.");
        }

        System.out.println("Total time: " + totalTime + " millis, avg = " + (totalTime / count) + " millis.");

        connectionFactory.destroy();
    }

    @Scheduled(fixedRate = 3000)
    public void sendMessage() {

    }
}
