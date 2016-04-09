package me.test.ons

import com.aliyun.openservices.ons.api.*
import groovy.json.JsonOutput
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OnsProducer {

    static Logger log = LoggerFactory.getLogger(OnsProducer)

    static void main(String[] args) {

        Properties props = new Properties()
        props.load(new FileInputStream(new File(System.getProperty("user.home"), ".aliyun")))

        def accessKeyId = props.accessKeyId
        def accessKeySecret = props.accessKeySecret

        def producerId = "p1"
        def topic = "btpka3"
        def tag = "a,b,c"

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, producerId);
        properties.put(PropertyKeyConst.AccessKey, accessKeyId);
        properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
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
        log.info "send OK : ${JsonOutput.toJson(sendResult)}"

        // 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
    }
}
