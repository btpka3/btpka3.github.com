package me.test.my.rocketmq.demo.remoting.my.producer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @see <a href="https://rocketmq.apache.org/zh/docs/4.x/producer/02message1">4.x : 普通消息发送</a>
 */
@Slf4j
public class DemoMqProducerService {

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
    public void sendSync01() {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer地址
        producer.setNamesrvAddr(nameServerAddr);
        // 启动producer
        producer.start();


        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
                Message msg = new Message(topic,
                        tag,
                        "Hello world".getBytes(StandardCharsets.UTF_8));
                // 异步发送消息, 发送结果通过callback返回给客户端
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                        countDownLatch.countDown();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        }
        //异步发送，如果要求可靠传输，必须要等回调接口返回明确结果后才能结束逻辑，否则立即关闭Producer可能导致部分消息尚未传输成功
        countDownLatch.await(5, TimeUnit.SECONDS);


        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }

}
