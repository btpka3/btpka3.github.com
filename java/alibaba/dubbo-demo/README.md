


## 环境准备



```bash
# 安装并启动 zookeeper （作为注册中心。）
# 广播模式的注册中心 在 dubbo 2.6.1 中有问题。需要到 2.6.2 中修复（尚未发布）
```

## 运行

```bash
./gradlew clean
./gradlew demo-provider:bootRun
./gradlew demo-consumer:bootRun
```


## 注意
- ProtocolConfig : 只在 provider 端配置

## 参考
 
- [demo](https://github.com/apache/incubator-dubbo/tree/master/dubbo-demo)
- [API 配置](http://dubbo.apache.org/books/dubbo-user-book/configuration/api.html)
- [安装手册](http://dubbo.apache.org/books/dubbo-admin-book/install/introduction.html)
