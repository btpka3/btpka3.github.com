package me.test.ons

import com.aliyun.openservices.ons.api.*

class OnsConsumer {
    static void main(String[] args) {
        def consumerId = "c1"
        def accessKey = "1p4282qt4px8kfz8t4vga673"
        def secretKey = "FeHSGRK8iNU4RbfB924Wgf6Jgqc="
        def topic = "btpka3"
        def tag = "*"

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, consumerId);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, tag, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
