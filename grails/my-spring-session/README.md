# 说明
1. 因为Grails 应用中无法独立在 web.xml中设定 filter，因此引入一个Grails Plugin工程完成该设定。
1. 修改完 session 中的对象之后，必须重新调用 HttpSession#setAttribute(String, Object) 才能完成保存
1. 分布式session中的对象值都必须实现 Serializable

# 演示
1. 修改 nginx 的配置文件 nginx.conf 并重新启动

    ```
    http {
        upstream my-spring-session {
            server      localhost:8080 weight=1 max_fails=1 fail_timeout=1s;
            server      localhost:8090 weight=1 max_fails=1 fail_timeout=1s;
        }
        server {
            listen       80;
            server_name  localhost;

            location /my-spring-session {
                proxy_next_upstream     http_500 http_502 http_503 http_504 timeout error invalid_header;
                proxy_pass              http://my-spring-session;
                proxy_set_header        Host                $host;
                proxy_set_header        X-Real-IP           $remote_addr;
                proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
                proxy_set_header        X-Forwarded-Proto   $scheme;
                index  index.html index.htm index;
            }
        }
    }
    ```

1. 启动两个实例

    ```sh
    grails run-app -Dgrails.server.port.http=8080
    grails run-app -Dgrails.server.port.http=8090
    ```

1. 浏览器循环访问

    ```text
    http://localhost/my-spring-session/test
    http://localhost/my-spring-session/test/update
    ```
