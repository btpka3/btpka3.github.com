package me.test.ons

import com.aliyun.openservices.ons.api.*

class OnsProducer {
    static void main(String[] args) {
        def producerId = "ddd"
        def accessKey = "1p4282qt4px8kfz8t4vga673"
        def secretKey = "FeHSGRK8iNU4RbfB924Wgf6Jgqc="
        def topic = "btpka3"
        def tag = "aaa"

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, producerId);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();

        //循环发送消息
        Message msg = new Message( //
                // Message Topic
                topic,
                // Message Tag,
                // 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
                tag,
                // Message Body
                // 任何二进制形式的数据， ONS不做任何干预，
                // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                "Hello ONS".getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_100 : " + new Date());
        // 发送消息，只要不抛异常就是成功
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);

        // 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
        println "done."
    }
}
