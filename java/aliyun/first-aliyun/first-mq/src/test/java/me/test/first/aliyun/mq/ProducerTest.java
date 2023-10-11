package me.test.first.aliyun.mq;

import com.aliyun.openservices.ons.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2019-10-22
 */
public class ProducerTest {

    @Configuration
    public static class Conf {


        @Bean(initMethod = "start", destroyMethod = "shutdown")
        Producer producer() {

            Properties properties = new Properties();
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, "XXX");
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, "XXX");
            //设置发送超时时间，单位毫秒
            properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
            // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
            properties.put(PropertyKeyConst.NAMESRV_ADDR, "XXX");

            return ONSFactory.createProducer(properties);
        }

    }

    @Autowired
    Producer producer;

    @Test
    public void testSendSync01() {
        Message msg = new Message(

                // Message 所属的 Topic
                "TopicTestMQ",
                // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在消息队列 MQ 的服务器过滤
                "TagA",
                // Message Body 可以是任何二进制形式的数据， 消息队列 MQ 不做任何干预，
                // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                "Hello MQ".getBytes()
        );

        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("messageKey_");

        SendResult sendResult = producer.send(msg);
        // 同步发送消息，只要不抛异常就是成功
        if (sendResult != null) {
            System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
        }

    }

    @Test
    public void testSendAsync01() {
        Message msg = new Message(
                // Message 所属的 Topic
                "TopicTestMQ",
                // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在消息队列 MQ 的服务器过滤
                "TagA",
                // Message Body 可以是任何二进制形式的数据， 消息队列 MQ 不做任何干预，
                // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                "Hello MQ".getBytes()
        );

        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("messageKey_");

        producer.sendAsync(msg, new SendCallback() {
            @Override
            public void onSuccess(final SendResult sendResult) {
                // 消费发送成功
                System.out.println("send message success. topic=" + sendResult.getTopic() + ", msgId=" + sendResult.getMessageId());
            }
            @Override
            public void onException(OnExceptionContext context) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                System.out.println("send message failed. topic=" + context.getTopic() + ", msgId=" + context.getMessageId());
            }
        });

    }
}
