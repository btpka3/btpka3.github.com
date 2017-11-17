
# 参考

- Sonartype Nexus API
    - [2.x Nexus Core API](https://repository.sonatype.org/nexus-restlet1x-plugin/default/docs/index.html)
       
      如果本地安装的有 nexus 2.x 版本，则可以登录后（默认用户 `admin`/`admin123`）,
      左侧面板选中 "Administration/Plugin Console"，
      右侧列表选中 `Nexus Core API (Restlet 1.x Plugin)`， 再点击 下面的 "Document" 链接即可浏览相关API。
      
      如果相关API不清楚，可以通过 Web 界面进行相应的操作，并监控网络请求面板里的信息。      


# sonartype nexus API
* 2.x
    * [Nexus Core API](https://repository.sonatype.org/nexus-restlet1x-plugin/default/docs/index.html)
     
* 3.x
    3.x 版本提供了基于 swagger 的API文档。
    * [simple-shell-example@github](https://github.com/sonatype/nexus-book-examples/tree/nexus-3.x/scripting/simple-shell-example)


# 目的

- snapshot 版本的文件过多，要求清理到旧的版本，只保留最近几个版本。

    - 方式一：通过 Nexus 的 REST API 完成（推荐）。
    - 方式二：查找 Nexus 本地磁盘上的文件，删除指定的，再通过web管理界面对该仓库 Rebuild Metadata.
            但是该方式，有可能潜在破获内部逻辑。而如果不通过文件系统存储，该方法将无效。
   
- 通过 RxJava / 或 Java8 中的 stream 相关API并发执行。
- 通过 jersey Reactive client。


# Why

- 为何不用 feign 进行声明式的 client 编程？因为服务器端API实现更倾向于 Jax-RS 2 的实现 Jersey 完成。
而 feign 暂时只支持 Jax-RS 1.1。

- Feign 在 Spring Cloud 中对 API 路由，发现等有很好的支持。而 Jersey client 则没有。
  是的，但是 Spring Cloud 同样也提供了如何发现服务并获取服务地址的API，非常方便使用。
  
# Jersey client vs. RestTemplate vs. Feign ?

- RestTemplate
    - 来自 Spring 框架，提供各种 HttpMessageConverter，对熟悉 Spring 的人而言，学习成本最低。
    - 接口层次较高，对开发人员友好
    - 有 UriComponentsBuilder 等方便的工具使用。
    - 需要较多的代码行
    - AsyncRestTemplate 提供了基本的异步编程支持

- WebClient
    - Spring framework 5.x 中的 WebFlux 提供， 方便 Rx 操作。但 WebFlux 是一套自定义的，全新的框架。
      可以理解为是 spring 版本的一套 servlet 规范，因此，有点特立独行。
      为了 Reactive 而抛弃现在所积累的 servlet 相关的知识，框架？
      
      WebFlux + netty vs. Jersey + netty ?  spring security 5.x 支持前者，那后者呢？

    - 可以流式API调用，API风格更现代
    - 支持同步、异步调用
    

- Jersey client
    - 与 RestTemplate 很类似，需额外学习成本
    - 也提供了 UriBuilder、 UriTemplate 等工具类 
    - 需要较多的代码行
    - Jersey Rx client 提供了很方便的 异步编程。但服务器端的实现并没有 reactive
    
- feign
    - 仅仅定义接口即可。非常方便对应简单的API。
    - 复杂的API呢？
        - 加密、签名
        - 特殊的请求头？
        - 支持 Hystrix 
        - 双向 SSL 证书？
    - FIXME: 各项注解中的值是否支持 placeholder?

# java8 stream vs. RxJava

 [nice answer @ stackoverflow](https://stackoverflow.com/a/35759458/533317)

与 rx.Observable 最相近的应该是 JDK8 的 Streams+CompletableFuture 组合了。


- Stream 是基于 pull 的，而 Observable 是基于 push 的。
- Stream 只能用一次，不能重复使用，Observable 则可以订阅多次
- `Stream#parallel()` 把序列进行了切割，而 `Observable#subscribeOn()` 和 `Observable#observeOn()` 则不会。
- `Stream#parallel()` 并没有指定要在哪个线程池上执行，而大多数 RxJava 方法都有 Scheduler 参数可以指定。

 
