package me.test.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

import javax.net.ssl.SSLContext

import static me.test.MY.*

// 参考：http://www.rabbitmq.com/tutorials/tutorial-five-java.html
// 参考：http://www.rabbitmq.com/api-guide.html
// 参考：https://github.com/TuPengXiong/TuPengXiong.github.io/wiki/rabbitmq

class RabbitMqSubTest {

    static void main(String[] args) {

        SSLContext sslContext = getClientSslContext(true)

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(MQTT_USER);
        factory.setPassword(MQTT_PWD);
        factory.setVirtualHost(AMQP_VIRTUAL_HOST);
        factory.setHost(AMQP_HOST);
        factory.setPort(AMQP_PORT);
        //factory.useSslProtocol("TLSv1.2");
        //factory.useSslProtocol();
        factory.useSslProtocol(sslContext)
        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 获取预定义的 exchange（这里是默认的，配置值默认的必须保持一致）
        // channel.exchangeDeclare(AMQP_EXCHANGE_NAME, "topic", true);

        String queueName = channel.queueDeclare().getQueue();
        println "queue [$queueName] has been declared."

        String routingKey = MQTT_TOPIC.replace('/', '.')
        channel.queueBind(queueName, AMQP_EXCHANGE_NAME, routingKey);

        // 发送消息
        println "start listening."
        int i = -1;
        Thread mainThread = Thread.currentThread();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("RabbitMqSubTest Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                i++
                if (i >= MQTT_COUNT - 1) {
                    synchronized (mainThread) {
                        mainThread.notify();
                    }
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);

        synchronized (mainThread) {
            mainThread.wait();
        }

        channel.close();
        conn.close();
        println "connection closed."
    }
}