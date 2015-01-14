

## 端口分配

|module            |http  |https |
|------------------|------|------|
|first-cas4-server |10010 |10011 |
|first-cas4-client |10020 |-     |


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

## 加密密码

```sh
cd $JETTY_HOME/lib
java -cp jetty-util-9.2.6.v20141205.jar  org.eclipse.jetty.util.security.Password me 123456
```

从 Jetty 的压缩包中 copy 配置文件 etc/jetty*.xml 到 src/main/config 中


默认可用用户名、密码 ： casuser / Mellon

## FIXME
* https reverse proxy
