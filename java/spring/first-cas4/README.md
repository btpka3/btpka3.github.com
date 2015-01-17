

## 端口分配

|module            |http  |https |
|------------------|------|------|
|first-cas4-server |10010 |10011 |
|first-cas4-client |10020 |10021 |


## 生成自签名证书

```
keytool -genkeypair \
        -alias mykey1 \
        -keyalg RSA \
        -keysize 1024 \
        -sigalg SHA1withRSA \
        -dname "CN=*.localhost.me, OU=R & D department, O=\"ABC Tech Co., Ltd\", L=Weihai, S=Shandong, C=CN" \
        -validity 365 \
        -keypass 123456 \
        -keystore ssl.keystore \
        -storepass 123456
```

其中 `*.localhost.me` 是域名。请修改域名为你所需要的实际域名。

注意多域名的 https 证书貌似只支持这样的 3级层次，
比如 `*.dev.localhost.me` 这样的 4级层次则没有成功。


## 加密密码

```sh
cd $JETTY_HOME/lib
java -cp jetty-util-9.2.6.v20141205.jar  org.eclipse.jetty.util.security.Password me 123456
```

从 Jetty 的压缩包中 copy 配置文件 etc/jetty*.xml 到 src/main/config 中，无需修改（jetty可以通过系统变量设置）。



## 运行

### 运行

```sh
cd first-cas4

# 启动 CAS 服务器
mvn -am -pl first-cas4-server -Dp_runServer prepare-package

# 启动 CAS 客户端
mvn -am -pl first-cas4-client -Dp_runClient prepare-package

```


## FIXME
* https reverse proxy
