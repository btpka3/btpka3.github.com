

# 目的

学习 netty

# 参考

* [netty-example](http://netty.io/4.1/xref/index.html) 下面的例子
* [Netty User Guide](http://netty.io/wiki/user-guide.html)
* github : [netty:netty](https://github.com/netty/netty)

* io.netty.example.socksproxy.SocksServer
* io.netty.example.socksproxy.SocksServerHandler 中有关于认证的
* io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequestDecoder
* io.netty.handler.codec.socksx.v5.Socks5AuthMethod
    

* [RFC-1928: SOCKS Protocol Version 5](https://tools.ietf.org/html/rfc1928)
* [RFC-1929: Username/Password Authentication for SOCKS V5](https://tools.ietf.org/html/rfc1929)
* [RFC-2743: GSS-API (Generic Security Service Application Program Interface)](https://tools.ietf.org/html/rfc2743)
* [RFC-4422: Simple Authentication and Security Layer (SASL)](https://tools.ietf.org/html/rfc4422)

* [chhsiao90/nitmproxy](https://github.com/chhsiao90/nitmproxy)
* [TCP and UDP Socket API](https://www.w3.org/TR/tcp-udp-sockets/)
* [Proxy Auto-Configuration (PAC) file](https://developer.mozilla.org/en-US/docs/Web/HTTP/Proxy_servers_and_tunneling/Proxy_Auto-Configuration_(PAC)_file)


## socks5 代理服务器

```bash
# 参考 `io.netty.example.socksproxy.*`

# 运行
#./gradlew run -DmainClassName=io.netty.example.socksproxy.SocksServer
./gradlew run -DmainClassName=io.github.btpka3.socks5.SocksV5Server

# 测试
curl --socks5 localhost:1080 https://www.baidu.com/

# FAILED
# curl: (7) User was rejected by the SOCKS5 server (1 255).
curl --proxy socks5://li4:123456@localhost:1080 https://www.baidu.com/

# SUCCESS
curl --proxy socks5://zhang3:zhang3@localhost:1080 https://www.baidu.com/



```