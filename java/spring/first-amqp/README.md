# 目的
该工程主要用来学习Spring AMQP + RabbitMQ.
RabbitMQ的Java API发送的消息都是byte数组，而Spring AMQP在其基础之上再进行抽象和封装，提供了MessageConverter，可以将Java对象按照要求的格式序列化（比如：JSON）为byte数组。

# 代码说明
java代码中，默认package的[代码](https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/java)来自于RabbitMQ提供的[教程](http://www.rabbitmq.com/tutorials/tutorial-one-java.html)。

java代码中，org.springframework.amqp.helloworld包下的[代码](https://github.com/spring-projects/spring-amqp-samples)来自于Spring-AMQP。

java代码中，me.test.amqp是总结后仿照RPC模式写的一个例子。如果需要异步，不需要返回值。需要以下改动：

1. 需要使用 AmqpTemplate#convertAndSend()方法
2. HelloWorldHandler#handleMessage() 不要提供返回值。


# 运行步骤

## 安装RabbitMQ

```sh
[me@localhost:~] sudo apt-get install rabbitmq-server
```
注意：由于RabbitMQ是用Erlang写的，因此，需要上述安装命令会自动先安装Erlang。

## RabbitMQ常用命令

```sh
# 启停命令等
[me@localhost:~] sudo service rabbitmq-server xxx

# 重置（即清除所有queue，binding，message等）
[me@localhost:~] sudo rabbitmqctl stop_app
[me@localhost:~] sudo rabbitmqctl force_reset
[me@localhost:~] sudo rabbitmqctl start_app
```

## 安装Maven
下载、解压、并设置环境变量

```sh
[me@localhost:~] export M2_HOME=/home/zll/work/apache-maven-3.2.1
[me@localhost:~] export PATH=${M2_HOME}/bin:$PATH
```
最后可以参考[这里](http://maven.oschina.net/help.html)，修改 ～/.m2/settings.xml 使用国内OSchina的Maven代理，以加快jar包下载速度。


## 运行 
第一次运行需要下载不少依赖，插件，会有点慢，be patient！如果不想看Maven给出的各种提示，可以在以下命令上追加 `-q` 选项。
注意：以下命令要按顺序执行，RpcConsumer会一直执行，请新开控制台运行RpcProducer。

```sh
[me@localhost:~] mvn clean compile
[me@localhost:~] mvn -q exec:java -Dexec.mainClass="me.test.amqp.RpcBroker"
[me@localhost:~] mvn -q exec:java -Dexec.mainClass="me.test.amqp.RpcConsumer"
[me@localhost:~] mvn -q exec:java -Dexec.mainClass="me.test.amqp.RpcProducer"
```
以下是我本地运行后，RpcProducer 的一次输出结果：

```txt
send msg :Hello World 1, reply = Hello World 1=1, using time = 170 millis.
send msg :Hello World 2, reply = Hello World 2=2, using time = 7 millis.
send msg :Hello World 3, reply = Hello World 3=3, using time = 9 millis.
send msg :Hello World 4, reply = Hello World 4=4, using time = 25 millis.
send msg :Hello World 5, reply = Hello World 5=5, using time = 55 millis.
send msg :Hello World 6, reply = Hello World 6=6, using time = 51 millis.
send msg :Hello World 7, reply = Hello World 7=7, using time = 4 millis.
send msg :Hello World 8, reply = Hello World 8=8, using time = 6 millis.
send msg :Hello World 9, reply = Hello World 9=9, using time = 5 millis.
send msg :Hello World 10, reply = Hello World 10=10, using time = 4 millis.
Total time: 336 millis, avg = 33 millis.
```

