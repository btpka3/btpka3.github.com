package me.test.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class RpcBroker {
    public static final String QUEUE_NAME = "my-rpc-queue";

    public static void main(String[] args) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setAddresses("192.168.101.85:5672,192.168.101.83:5672");

        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        Queue queue = new Queue(QUEUE_NAME);

        amqpAdmin.declareQueue(queue);
        System.out.println("queue declared.");

        connectionFactory.destroy();
    }
}
