


# 目的
该工程主要测试：

* 使用Docker、RabbitMQ搭建 MQTT服务器
* 使用MQTTS
* MQTT 用户名、密码配置
* 特定Topic只有特定Cient端才能 publish 消息
* 特定Topic只有特定Cient端才能 subscribe 消息
* 使用 JavaScript + WebSocket 通信MQTT服务器。
* 使用 RabbitMQ 的client包通信。




# MQTT server


使用RabbitMQ搭建


## Dockerfile

```
FROM rabbitmq

RUN rabbitmq-plugins enable --offline rabbitmq_management
EXPOSE 15671 15672

RUN rabbitmq-plugins enable --offline rabbitmq_mqtt
EXPOSE 1883 8883

RUN echo =====RABBITMQ_VERSION: ${RABBITMQ_VERSION}
ADD https://dl.bintray.com/rabbitmq/community-plugins/rabbitmq_web_mqtt-3.6.x-14dae543.ez \
    /usr/lib/rabbitmq/lib/rabbitmq_server-$RABBITMQ_VERSION/plugins/
RUN chmod go+r /usr/lib/rabbitmq/lib/rabbitmq_server-$RABBITMQ_VERSION/plugins/rabbitmq_web_mqtt-3.6.x-14dae543.ez
EXPOSE 15675

RUN rabbitmq-plugins enable --offline rabbitmq_web_mqtt
```

## 构建docker image

```
mkdir /tmp/aaa
cd /tmp/aaa
touch Dockerfile  # 文件内容见后面
docker build -t btpka3/my-mq:1.0 .
docker images

docker run -d \
    --name mq \
    -p 4369:4369 \
    -p 5671:5671 \
    -p 5672:5672 \
    -p 25672:25672 \
    -p 15671:15671 \
    -p 15672:15672 \
    -p 1883:1883 \
    -p 8883:8883 \
    -p 15675:15675 \
    btpka3/my-mq:1.0

docker exec -it mq bash

# 启用 MQTT 插件
# rabbitmq-plugins enable rabbitmq_mqtt

# 显示默认配置
cat /etc/rabbitmq/rabbitmq.config
cat /etc/rabbitmq/enabled_plugins


# 通过浏览器访问
# http://localhost:15672
```

# RabbitMQ mqtt plugin

## 概念

参考： [AMQP 概念](http://www.rabbitmq.com/tutorials/amqp-concepts.html)

* connections: 代表一个客户端与服务器端的TCP链接(物理通道)。
* Channels: 在一个connection上的逻辑通道。（FIXME：能否物理通道上有多个逻辑通道？）
* Exchanges: 在服务器端内部，Channels的下一个通信对象。有四种类型：direct， fanout, topic, headers。
   exchange 按照其类型、消息的routing key，queue 在binding exchange时是声明的 routing key 将消息路由。
   注意：exchange可以像 queue 一样绑定到另外一个 exchange 上
* Queues: 代表要一个消息队列。可以绑定到多个exchange上


当PahoSubTest 启动时，会：

* 创建 Queue : mqtt-subscription-PahoSubTestqos1
    * durable: true， auto-delete: true
    * 并默认 binding 到 topic 类型的 "amq.topic" exchange 上， routing key = ".my.mqtt" (即 编程时 MQTT的 Topic 的值，并且: "/" -> ".")

# 代码说明

```
/src/main/groovy/me/test/paho       // 使用 Eclipse paho 的Java客户端订阅、发送。
/src/main/groovy/me/test/rabbitmq   // 使用 RabbitMq 客户端订阅、发送。（使用AMQP协议）
/src/main/groovy/me/test/spring     // 使用 Spring AMQP 客户端订阅、发送。（使用AMQP协议）
/src/main/javascript                // 使用 Eclipse paho 的JavaScript客户端订阅、发送。
```


## spring amqp

```
SimpleMessageListenerContainer#start()
    SimpleMessageListenerContainer#doStart()
        SimpleAsyncTaskExecutor#execute(AsyncMessageProcessingConsumer); 
        
AsyncMessageProcessingConsumer(BlockingQueueConsumer)
AsyncMessageProcessingConsumer#run()
    SimpleMessageListenerContainer#doReceiveAndExecute
        BlockingQueueConsumer#nextMessage(1000) # 每秒检查一次消息
            BlockingQueueConsumer.InternalConsumer#handleDelivery()
        AbstractMessageListenerContainer#executeListener
            AbstractMessageListenerContainer#invokeListener
        
1. 通过 BlockingQueueConsumer.InternalConsumer 将消息存储的 BlockingQueue 中
2. 通过 
```