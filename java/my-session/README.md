# 目标
基于Servlet API，不依赖于容器，由应用自行管理session，以通过将session集中存储达到Session共享的功能。

# 约束
* 不能使用HttpSessionListener。
* 注意me.test.SessionFilter的顺序，必须在使用使用session之前。

# 测试步骤
## 安装并启动redis
## 启动两个该示例应用

```sh
mvn -Djetty.port=10001 jetty:run
mvn -Djetty.port=10002 jetty:run
```
## 配置Nginx进行反向代理

创建 /etc/nginx/sites-enabled/my-session.conf, 内容如下，并重启nginx

```conf
log_format mySessionLogFmt  '$remote_addr - $remote_user [$time_local] "$request" '
                            '$status $body_bytes_sent "$http_referer" '
                            '"$http_user_agent" "$http_x_forwarded_for" $upstream_addr';

upstream mySessionServer {
    server                        localhost:10001 weight=1 max_fails=1 fail_timeout=1s;
    server                        localhost:10002 weight=1 max_fails=1 fail_timeout=1s;
}
server {
    listen 80;
    server_name my-session.test.me;
    location / {
            proxy_next_upstream http_500 http_502 http_503 http_504 timeout error invalid_header;
            proxy_pass                http://mySessionServer;
            proxy_set_header  X-Real-IP  $remote_addr;
            index  index.html index.htm index.php;
    }
    access_log  /var/log/nginx/access-mysession.log mySessionLogFmt;
    error_log   /var/log/nginx/error-mysession.log;
}
```

## 修改本机hosts

```sh
sudo vi /etc/hosts 
# 追加以下内容，前者是本机IP地址
000.000.000.000 my-session.test.me 
```

## 监视nginx访问日志

```sh
tailf /var/log/nginx/access-mysession.log
```
## 

## 浏览器访问并确认
1. 浏览器访问 http://my-session.test.me/?param=value
1. 第一次访问（request不含cookie）时，相应的 &lt;c:url&gt; 会生成包含 ";jsessionid=xxx" 的url
1. 每次刷新，根据nginx访问日志，可以得知请求依次转发到不同的服务器上。
1. 每次刷新，URL参数的值都会保存到session中，再次再访问时，即使URL不含参数，即使请求仍然被分配到不同服务器上，cookie的值也均能从redis中获取。