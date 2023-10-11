package me.test.first.aliyun.mq;

import com.alibaba.fastjson2.JSON;
import com.aliyun.openservices.ons.api.*;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2023/10/10
 * @see <a href="https://help.aliyun.com/zh/apsaramq-for-rocketmq/cloud-message-queue-rocketmq-4-x-series/developer-reference/subscribe-to-messages-2">Pull方式</a>
 */
public class PullTest {

    static String nameServerAddr = "http://MQ_INST_1401685646033801_BcowDDgE.mq-internet-access.mq-internet.aliyuncs.com:80";
    String topic = "test_topic";
    String tag = "*";
    String consumerGroup = "GID_test_tcp";

    @SneakyThrows
    protected AkSkConf getAkSkConf() {
        String akSkConfFile = System.getProperty("user.home") + "/Documents/data/AkSkConf.csis-cloud.test.json";
        String akSkConfJson = IOUtils.toString(new FileInputStream(akSkConfFile), StandardCharsets.UTF_8);
        return JSON.parseObject(akSkConfJson, AkSkConf.class);
    }

    public PullConsumer getPullConsumer() {
        AkSkConf akSkConf = getAkSkConf();

        Properties properties = new Properties();
        // 您在消息队列RocketMQ版控制台创建的Group ID。
        properties.setProperty(PropertyKeyConst.GROUP_ID, consumerGroup);
        // 请确保环境变量ALIBABA_CLOUD_ACCESS_KEY_ID、ALIBABA_CLOUD_ACCESS_KEY_SECRET已设置。
        // AccessKey ID，阿里云身份验证标识。
        properties.put(PropertyKeyConst.AccessKey, akSkConf.getAccessKeyId());
        // AccessKey Secret，阿里云身份验证密钥。
        properties.put(PropertyKeyConst.SecretKey, akSkConf.getAccessKeySecret());
        // 设置TCP接入域名，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, nameServerAddr);
        PullConsumer consumer = ONSFactory.createPullConsumer(properties);
        consumer.start();
        return consumer;
    }

    @Test
    public void testPull() {

        PullConsumer consumer = getPullConsumer();
        Set<TopicPartition> topicPartitions = consumer.topicPartitions(topic);
        // 指定需要拉取消息的分区。
        consumer.assign(topicPartitions);


        List<Message> messages = consumer.poll(10 * 1000);
        System.out.printf("Received message: %s %n", messages.size());
        System.out.println("Done.");
    }
}
