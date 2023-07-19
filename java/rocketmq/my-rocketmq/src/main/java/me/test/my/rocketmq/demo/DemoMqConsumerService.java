package me.test.my.rocketmq.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @see <a href="https://rocketmq.apache.org/zh/docs/4.x/consumer/02push">4.x : Push消费</a>
 * @see <a href="https://rocketmq.apache.org/zh/docs/4.x/consumer/03pull">4.x : Pull消费</a>
 */
@Component("demoMqConsumerService")
@Slf4j
public class DemoMqConsumerService {

    // 个人开发机本地
//    String nameServerAddr = "localhost:9876";
//    String topic = "TopicTest";
//    String tag = "TagA";


    // k8s 环境
    String nameServerAddr = "rocketmq-namesrv.default.svc.cluster.local:9876";
    String topic = "TopicTest";
    String tag = "TagA";

    @PostConstruct
    public void init() {
        push01();
    }

    @SneakyThrows
    public void push01() {


        // 初始化consumer，并设置consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // 设置NameServer地址
        consumer.setNamesrvAddr(nameServerAddr);
        //订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
        consumer.subscribe(topic, tag);
        //注册回调接口来处理从Broker中收到的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 返回消息消费状态，ConsumeConcurrentlyStatus.CONSUME_SUCCESS为消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动Consumer
        consumer.start();
        System.out.printf("Consumer Started.%n");

    }


}
