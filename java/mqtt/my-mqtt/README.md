


# 目的
该工程主要测试：

* 使用Docker、RabbitMQ搭建 MQTT服务器
* 使用MQTTS
* MQTT 用户名、密码配置
* 特定Topic只有特定Cient端才能 publish 消息
* 特定Topic只有特定Cient端才能 subscribe 消息
* 使用 JavaScript + WebSocket 通信MQTT服务器。
* 使用 RabbitMQ 的client包通信。


```bash
cd src/main/docker
docker-compose up

docker-compose stop
```



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

mkdir -p ~/tmp/mq/conf/
mkdir -p ~/tmp/mq/data/
echo '[ { rabbit, [ { loopback_users, [ ] } ] } ].' > ~/tmp/mq/conf/rabbitmq.config
touch ~/tmp/mq/conf/rabbitmq-env.conf

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
    -p 1561567575:15675 \
    -v ~/tmp/mq/data/:/var/lib/rabbitmq \
    -v ~/tmp/mq/conf/rabbitmq.config:/etc/rabbitmq/rabbitmq.config \
    -v ~/tmp/mq/conf/rabbitmq-env.conf:/etc/rabbitmq/rabbitmq-env.conf \
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


# SSL 证书


## openssl.cnf


```
[ req ]
distinguished_name = root_ca_distinguished_name

[ root_ca_distinguished_name ]
commonName = hostname

[ root_ca_extensions ]
basicConstraints = CA:true
keyUsage = keyCertSign, cRLSign

[ client_ca_extensions ]
basicConstraints = CA:false
keyUsage = digitalSignature
extendedKeyUsage = 1.3.6.1.5.5.7.3.2

[ server_ca_extensions ]
basicConstraints = CA:false
keyUsage = keyEncipherment, digitalSignature
extendedKeyUsage = 1.3.6.1.5.5.7.3.1
```

## 自签名的 CA 证书

```sh
openssl req \
    -x509 \
    -newkey rsa:2048 \
    -days 3650 \
    -sha256 \
    -config openssl.cnf \
    -extensions root_ca_extensions \
    -subj "/CN=myca/" \
    -outform PEM \
    -out myca.pem.cer \
    -keyout myca.pem.key \
    -nodes
    
# 将私钥和证书 合并成单个的 PKCS#12 格式的证书
openssl pkcs12 \
   -export \
   -in myca.pem.cer \
   -inkey myca.pem.key \
   -out myca.p12 \
   -passout pass:123456 \
   -name myca

# 将 PKCS#12 格式的证书 转换为 JKS 格式的
keytool -importkeystore \
   -srcstoretype PKCS12 \
   -srckeystore myca.p12 \
   -srcstorepass 123456 \
   -srcalias myca \
   -deststoretype JKS \
   -destkeystore myca.jks \
   -deststorepass 123456 \
   -destalias myca \
   -destkeypass 456789
```

## 生成server端证书
  
```sh
# 生成一个公钥私钥对儿
openssl genrsa \
    -out server.pem.key \
    2048

# 生成 生成一个CSR (Certificate Signing Request)
openssl req \
    -new \
    -key server.pem.key \
    -out server.pem.csr \
    -subj "/CN=server/"

# 签名
openssl x509 \
    -req \
    -days 3650 \
    -in server.pem.csr \
    -CA myca.pem.cer \
    -CAkey myca.pem.key \
    -CAcreateserial \
    -extfile openssl.cnf \
    -extensions server_ca_extensions \
    -out server.pem.cer

# 将私钥和证书 合并成单个的 PKCS#12 格式的证书
openssl pkcs12 \
    -export \
    -in server.pem.cer \
    -inkey server.pem.key \
    -out server.p12 \
    -passout pass:123456 \
    -name server

# 将 PKCS#12 格式的证书 转换为 JKS 格式的
keytool -importkeystore \
    -srcstoretype PKCS12 \
    -srckeystore server.p12 \
    -srcstorepass 123456 \
    -srcalias server \
    -deststoretype JKS \
    -destkeystore server.jks \
    -deststorepass 123456 \
    -destalias server \
    -destkeypass 456789

# 检查
openssl verify \
    -verbose \
    -CAfile myca.pem.cer \
    server.pem.cer
```

## 生成 client 端证书
     
```sh
# 生成一个公钥私钥对儿
openssl genrsa \
   -out client.pem.key \
   2048

# 生成 生成一个CSR (Certificate Signing Request)
openssl req \
   -new \
   -key client.pem.key \
   -out client.pem.csr \
   -subj "/CN=client/"

# 签名
openssl x509 \
   -req \
   -days 3650 \
   -in client.pem.csr \
   -CA myca.pem.cer \
   -CAkey myca.pem.key \
   -CAcreateserial \
   -extfile openssl.cnf \
   -extensions client_ca_extensions \
   -out client.pem.cer

# 将私钥和证书 合并成单个的 PKCS#12 格式的证书
openssl pkcs12 \
   -export \
   -in client.pem.cer \
   -inkey client.pem.key \
   -out client.p12 \
   -passout pass:123456 \
   -name client

# 将 PKCS#12 格式的证书 转换为 JKS 格式的
keytool -importkeystore \
   -srcstoretype PKCS12 \
   -srckeystore client.p12 \
   -srcstorepass 123456 \
   -srcalias client \
   -deststoretype JKS \
   -destkeystore client.jks \
   -deststorepass 123456 \
   -destalias client \
   -destkeypass 456789
   
# 检查
openssl verify \
    -verbose \
    -CAfile myca.pem.cer \
    client.pem.cer
```


# nginx 反向代理

docker安装nginx（需要版本1.9.0之后的）

```
docker run \
    --name my-nginx \
    -d \
    -p 80:80 \
    -p 443:443 \
    -p 11883:11883 \
    -p 18883:18883 \
    -p 19883:19883 \
    --link mq:mq \
    -v ~/tmp/my-nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro \
    -v ~/tmp/my-nginx/conf/conf.d:/etc/nginx/conf.d:ro \
    nginx:1.10.2

```

修改nginx.conf

```
# 最后一行追加以下配置
stream {
    include /etc/nginx/conf.d/*.conf.stream;
}
```

创建 $NGINX_HOME/conf/conf.d/mq.conf.stream

```
upstream mqtt_1883 {
    server mq:1883;
}   
upstream mqtts_8883 {
    server mq:8883;
}

# 完全 tcp 转发   
server {
    listen 11883;
    proxy_connect_timeout       20s;
    proxy_timeout               5m; 
    proxy_pass                  mqtt_1883;
}
server {
    listen 18883;
    proxy_connect_timeout       20s;
    proxy_timeout               5m; 
    proxy_pass                  mqtts_8883;
}

# 代为TSL
server {
    listen 19883 ssl;
    ssl_certificate             conf.d/mq/server.pem.cer;
    ssl_certificate_key         conf.d/mq/server.pem.key;
    ssl_session_cache           shared:SSL:10m;
    ssl_session_timeout         10m;
    #ssl_ciphers                HIGH:!aNULL:!MD5;
    #ssl_prefer_server_ciphers  on; 
    proxy_connect_timeout       20s;
    proxy_timeout               5m; 
    proxy_pass                  mqtt_1883;
}
```