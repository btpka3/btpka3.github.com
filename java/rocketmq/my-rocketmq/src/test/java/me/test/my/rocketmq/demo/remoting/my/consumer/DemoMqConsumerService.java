package me.test.my.rocketmq.demo.remoting.my.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @see <a href="https://rocketmq.apache.org/zh/docs/4.x/consumer/02push">4.x : Push消费</a>
 * @see <a href="https://rocketmq.apache.org/zh/docs/4.x/consumer/03pull">4.x : Pull消费</a>
 */
@Slf4j
public class DemoMqConsumerService {

    // 个人开发机本地
    String nameServerAddr = "localhost:9876";
    String topic = "TopicTest";
    String tag = "TagA";


    // k8s 环境
//    String nameServerAddr = "rocketmq-namesrv.default.svc.cluster.local:9876";
//    String topic = "TopicTest";
//    String tag = "TagA";

    @Test
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

        Thread.sleep(10 * 60 * 1000);
    }


    @SneakyThrows
    @Test
    public void pull01() {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.start();
        try {
            MessageQueue mq = new MessageQueue();
            mq.setQueueId(0);
            mq.setTopic(topic);
            mq.setBrokerName("jinrongtong-MacBook-Pro.local");
            long offset = 26;
            PullResult pullResult = consumer.pull(mq, tag, offset, 32);
            if (pullResult.getPullStatus().equals(PullStatus.FOUND)) {
                System.out.printf("%s%n", pullResult.getMsgFoundList());
                consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.shutdown();
    }
}
