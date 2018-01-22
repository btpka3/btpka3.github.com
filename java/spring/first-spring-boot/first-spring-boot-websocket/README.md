## 运行

```bash
# bootRun 运行
gradle :first-spring-boot-websocket:bootRun
# 浏览器访问 http://localhost:8080/ws.html

# 修改 src/test/docker/nginx/conf.d/default.conf 中 upsteam 地址为自己的 ip 地址
# 启动 nginx
cd first-spring-boot-websocket/src/test/docker
docker-compose up
# 浏览器访问 http://localhost/ws.html
```
## 参考

* 《[How HTML5 Web Sockets Interact With Proxy Servers](https://www.infoq.com/articles/Web-Sockets-Proxy-Servers)》
* [websocket @ Spring](http://docs.spring.io/spring/docs/4.2.0.RELEASE/spring-framework-reference/htmlsingle/#websocket)
* [spring-websocket-test](https://github.com/rstoyanchev/spring-websocket-test)
* [WebSockets@MDN](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)



```
在 DispatcherServlet 中通过 WebSocketHttpRequestHandler

@EnableWebSocket
```


## FIXME

1. JUnit test
2. STOMP

