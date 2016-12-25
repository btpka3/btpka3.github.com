
## 运行

```
# 安装所需的依赖
npm install
bower install

# 运行
node .
 
# 浏览器打开 http://localhost:3000, 并发送请求
# 请留意命令行的控制台输出，以及浏览器的网络请求。
```


## 参考
* socket.io
    * [@github](https://github.com/socketio/socket.io)
* chat demo
    * [get start](http://socket.io/get-started/chat/)
    * [source @ github](https://github.com/rauchg/chat-example)
* 《[Socket.IO：支持WebSocket协议、用于实时通信和跨平台的框架](http://www.infoq.com/cn/news/2015/01/socket-io-websocket/)》

## 疑问

* socket.io 是什么东东？
 
提供node环境下 消息服务器的实现，以及浏览器环境的js 客户端。使用 websocket 来实现通讯。

* 在 WebSocket 之上，使用的是哪一种消息协议？自定义的么？
  如果是自定义的，也就意味着只能使用 socket.io-client 来跟其服务器进行通信。
  

