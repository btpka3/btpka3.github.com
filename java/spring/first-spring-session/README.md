






# 说明
1. first-spring-session 启动两个节点: 10001 和 10002 端口

1. first-spring-session 提供一下 api:

    - `GET /1000x/session/exists`   // 判断后台当前是否有session。不会创建。  
    - `GET /1000x/session/values`   // 如果有session，则取出 session 中的给定值
                                    // 如果没有session，则返回空对象 "{}"
    - `GET /1000x/session/save`     // 在session中放入一些测试值
                                    // - 基础数据类型，集合类数据类型
                                    // - 非基础数据类型 更新后，有/无调用 session.setAttribute()。                            
    - `GET /1000x/session/destroy`  // 使 session失效。

1. first-spring-session 要配置使用相同的 session cookie path (SessionConf.java) 


1. nginx 监听 80 端口。通过路径的方式将两个节点区分，但两个节点访问不跨域（相同的协议、域名、端口号）

# 测试用例

1. 初始状态下 所有 session 均不存在
1. session 同步创建并共享：


    1. `GET /10001/session/save` 之后， 
        1. `GET /10001/session/values` 能看到更新的值。
        1. `GET /10002/session/values` 也能看到更新的值，且值与之前的一直。

    1. 再次 `GET /10001/session/save` 之后， 
        1. `GET /10001/session/values` 能看到更新的值。
        1. `GET /10002/session/values` 也能看到更新的值，且值与之前的一直。

1. session 同步销毁

    1. `GET /10001/session/destroy` 之后， 
        1. `GET /10001/session/exists` 能看到更新的值。
        1. `GET /10001/session/values` 看到的值为空
        1. `GET /10002/session/exists` 能看到更新的值。
        1. `GET /10002/session/values` 看到的值为空




# 运行

```bash
# 修改 src/test/docker 下文件中的 ip 地址

# 启动相关服务
cd src/test/docker
docker-compose up
docker-compose stop

# 启动测试用APP
./gradlew -Dp=10001 bootRun  # 也可以在 IDE 中 debug 模式启动
./gradlew -Dp=10002 bootRun

# 浏览器访问，点相应按钮，并监控网络面板进行测试。
http://localhost/10001/test.html
```

# 总结
1. 每次修改 session 中的引用对象后，无需在 session.put() 进行保存，即可确保 session 中共享。
   猜测是在每个请求处理完成后，再把 session 写到数据库内一次。

1. 本来以为会在 session 注销时会有问题，需要自己通过额外的消息通知机制完成 "单点退出" 的，结果发现并不需要。


1. 但是怀疑会有以下问题：

    ```txt
    # 同个用户多个开时，其中一个先把 session 过期，但在返回 resp (把 session 从数据库中删除) 前
    # 另外一个请求已经读取出未过期前的session，并更新，
    # 是否会造成 session 删除失败？
    ----------------------------------------------------------------> timeline
    UserAgent --> node1 ---> getSessionFromDB ----> destory session --------------------------> resp
    UserAgent --> node2 ---> getSessionFromDB -------------------------> update session ------> resp
    ```
   