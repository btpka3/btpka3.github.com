

# 目的
学习 [RocketMQ](http://rocketmq.apache.org/)

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