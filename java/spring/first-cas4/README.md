

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


## 使用 Nginx 进行 https 反向代理

### 从 keystore 中导出 私钥、公钥

```
# keystore 的格式 ： JKS -> PKCS12
keytool -importkeystore \
    -srcstoretype JKS \
    -srckeystore ssl.keystore \
    -srcstorepass 123456 \
    -srcalias mykey1 \
    -srckeypass 123456 \
    -deststoretype PKCS12 \
    -destkeystore ssl.p12 \
    -deststorepass 123456 \
    -destalias mykey1 \
    -destkeypass 123456 \
    -noprompt

# keystore 的格式 ： PKCS12 -> PEM
openssl pkcs12 \
    -in ssl.p12 \
    -out ssl.pem.p12 \
    -passin pass:123456 \
    -passout pass:123456

# 导出私钥
openssl rsa \
    -in ssl.pem.p12 \
    -passin pass:123456 \
    -out ssl.pem.key \
    -passout pass:123456

# 导出公钥
 keytool -exportcert \
     -rfc \
     -file ssl.pem.cer \
     -alias mykey1 \
     -keystore ssl.keystore \
     -storepass 123456
     
openssl rsa \
    -in ssl.pem.p12 \
    -passin pass:123456 \
    -out ssl.pem.pub \
    -pubout
```

### 修改 nginx 相关配置

```conf
upstream my-cas {
    server                        localhost:10010 weight=1 max_fails=1 fail_timeout=1s;
}
server {
    listen 80;
    root /notExisted;
    index index.html index.htm;
    server_name cas.localhost.me;
    
    if ($request_method != GET ) {
       return 301 https://hisdev.eyar.com/;
    }
    return 301 https://hisdev.eyar.com$request_uri;
}
server {
    listen 443;
    root /usr/share/nginx/html;
    index index.html index.htm;
    server_name cas.localhost.me;
    ssl on;
    ssl_certificate     /path/to/ssl.pem.cer;
    ssl_certificate_key /path/to/ssl.pem.clear.key;

    location / {
         proxy_pass              http://my-cas;
         proxy_redirect          off;
         proxy_set_header        Host            $host;
         proxy_set_header        X-Real-IP       $remote_addr;
         proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
         proxy_set_header        X-Forwarded-Proto $scheme;
         add_header              Front-End-Https   on;
    }
}
```

### 修改 jetty 相关配置

修改 first-cas-server/src/main/config/jetty.xml，启用 httpConfig 中 的以下配置：

```
<Call name="addCustomizer">
  <Arg><New class="org.eclipse.jetty.server.ForwardedRequestCustomizer"/></Arg>
</Call>
```


## FIXME
* https reverse proxy
