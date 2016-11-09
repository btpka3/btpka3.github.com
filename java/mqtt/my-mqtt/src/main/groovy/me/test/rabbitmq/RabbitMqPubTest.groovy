package me.test.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

import static me.test.MY.*

// 参考：http://www.rabbitmq.com/tutorials/tutorial-five-java.html
// 参考：http://www.rabbitmq.com/api-guide.html
// 参考：https://github.com/TuPengXiong/TuPengXiong.github.io/wiki/rabbitmq
class RabbitMqPubTest {

    static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(MQTT_USER);
        factory.setPassword(MQTT_PWD);
        factory.setVirtualHost(AMQP_VIRTUAL_HOST);
        factory.setHost(AMQP_HOST);
        factory.setPort(AMQP_PORT);
        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 获取预定义的 exchange（这里是默认的，配置值默认的必须保持一致）
        // channel.exchangeDeclare(AMQP_EXCHANGE_NAME, "topic", true);

        // 发送消息
        println "start sending msg."
        String routingKey = MQTT_TOPIC.replace('/', '.')
        MQTT_COUNT.times {
            String content = "RabbitMqPubTest msg[$it]"
            channel.basicPublish(AMQP_EXCHANGE_NAME, routingKey, null, content.getBytes("UTF-8"));
        }
        println "msg has been sent."

        channel.close();
        conn.close();
        println "connection closed."

    }

}