

# 目的
学习 [RocketMQ](http://rocketmq.apache.org/)
《[5.0速览](https://rocketmq.apache.org/version/)》 5.x 引入了POP机制（无状态）

# 步骤

```bash
# 启动 RocketMq
docker-compose up --build

# java 运行相应的 demo。
```

# ~~步骤(OLD)~~

```bash
# 以为还没有独立发布的压缩包，需要从源码构建
git clone git@github.com:apache/rocketmq.git
cd rocketmq
mvn -Prelease-all -DskipTests clean install -U
cd distribution/target/apache-rocketmq

# 启动 Name Server
bin/mqnamesrv
 
# 启动 Broker 
bin/mqbroker -n localhost:9876

# 启动 相应的 example, 需要设置相应的环境变量， 
export NAMESRV_ADDR=localhost:9876
# 然后运行 org.apache.rocketmq.example.simple.AsyncProducer 等例子。
```

# 旧：remoting api 

典型接口：
- org.apache.rocketmq.client.consumer.DefaultMQPushConsumer
- org.apache.rocketmq.client.producer.DefaultMQProducer


```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client</artifactId>
    <version>5.2.0</version>
</dependency>
```

# 新：remoting api 

典型接口：
- org.apache.rocketmq.client.apis.ClientServiceProvider
- org.apache.rocketmq.client.apis.producer.Producer
- org.apache.rocketmq.client.apis.ClientServiceProvider
- org.apache.rocketmq.client.apis.consumer.PushConsumer


```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client-java</artifactId>
    <version>5.0.6</version>
</dependency>
```

# 打包

```shell
./gradlew build -x test
# copy 到目标机器上
cat <<EOF > ~/key
AccessKey=xxx
SecretKey=yyy
EOF

vi applicaiton.yaml

java -jar my-rocketmq-0.0.1-SNAPSHOT.jar



```

