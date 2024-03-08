
Apache RocketMQ :
- [快速开始](https://rocketmq.apache.org/zh/docs/quickStart/01quickstart)
- [部署 & 运维 / 部署方式](https://rocketmq.apache.org/zh/docs/deploymentOperations/01deploy/#%E5%90%AF%E5%8A%A8-broker)
- [Admin Tool](https://rocketmq.apache.org/zh/docs/deploymentOperations/02admintool)


# MacOS 本地直接启动

```shell
mkdir -p ~/data0/soft/rocketmq

# 下载并解压 rocketmq
export ROCKETMQ_HOME=~/data0/soft/rocketmq/rocketmq-all-5.1.1-bin-release

vi $ROCKETMQ_HOME/conf/rmq-proxy.json
```

修改 rmq-proxy.json 配置，防止端口冲突
- grpcServerPort     : 默认 8081 端口, 与 sunproxyadmin (这是什么？MCafee?) 冲突
- remotingListenPort : 默认 8080 端口, 与本地 ali-tomcat 的默认端口冲突

```json
{
  "rocketMQClusterName"  : "DefaultCluster",
  "grpcServerPort"       : 8082,
  "remotingListenPort"   : 8083
}
```

启动 NameServer

```shell
sdk use java 8.0.332-zulu
$ROCKETMQ_HOME/bin/mqnamesrv
```

启动 Broker+Proxy

```shell
sdk use java 8.0.332-zulu
export NAMESRV_ADDR=localhost:9876
$ROCKETMQ_HOME/bin/mqbroker --enable-proxy
```

启动 rocketmq-dashboard
一次性：先下载
```shell
# 下载 rocketmq-dashboard 的 spring boot 的 jar 包
mkdir -p ~/data0/soft/rocketmq-dashboard
wget -O ~/data0/soft/rocketmq-dashboard/rocketmq-dashboard-1.0.0.jar \
    https://repo1.maven.org/maven2/org/apache/rocketmq/rocketmq-dashboard/1.0.0/rocketmq-dashboard-1.0.0.jar
```

启动

```shell
# 使用 jdk 8
sdk use java 8.0.332-zulu

# 指定 nameserver 并启动
java -Drocketmq.namesrv.addr=127.0.0.1:9876 \
    -Dserver.port=8084 \
    -jar ~/data0/soft/rocketmq-dashboard/rocketmq-dashboard-1.0.0.jar
```

浏览器访问  [http://localhost:8084/](http://localhost:8084/)

## 启动示例程序
老的编程方式：
- 启动 consumer : [DemoMqConsumerService.java](../../java/me/test/my/rocketmq/demo/remoting/my/consumer/DemoMqConsumerService.java) 
- 启动 producer : [DemoMqProducerService.java](../../java/me/test/my/rocketmq/demo/remoting/my/producer/DemoMqProducerService.java)


#  docker 启动
启动 dashboard
```shell
# 注意 ： 这里的 30.196.226.115 是 MacOS 物理机的 IP 地址。由于这里是 docker容器内要访问，故不能设置成 localhost 或 127.0.0.1
docker run --rm -it -p 8084:8080 -e NAMESRV_ADDR=30.196.227.16:9876 registry.cn-hangzhou.aliyuncs.com/btpka3/rocketmq-dashboard:1.0.0

# 启动后浏览器验证
http://localhost:8084 
```







# 使用 podman-compose
现状：not work， 因为 物理机 MacOS 无法访问 podman 创建的container 的IP

```shell
podman machine start
podman-compose build
podman-compose up -d --force-recreate --renew-anon-volumes
podman-compose down
podman machine stop
```

```shell

export ROCKETMQ_HOME=$HOME/Downloads/rocketmq-all-5.1.0-bin-release
export NAMESRV_ADDR=localhost:9876
cd $ROCKETMQ_HOME
 
# 显示完整命令列表
./bin/mqadmin

# 检查 topic 是否存在
./bin/mqadmin topicList -c
# 创建/更新 topic 
./bin/mqadmin updateTopic -c DefaultCluster -p 6 -t yourNormalTopic
# 删除 topic
./bin/mqadmin deleteTopic -c DefaultCluster -t yourNormalTopic

# 根据msgId查询消息
./bin/mqadmin queryMsgByUniqueKey -i 018E413615E25925460477D19A00000001 -t yourNormalTopic
```

