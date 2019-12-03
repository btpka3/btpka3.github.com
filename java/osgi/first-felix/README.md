


## 目的

- ~~使用 [gradle osgi 插件](https://docs.gradle.org/current/userguide/osgi_plugin.html) 创建 OSGI 模块~~
- 使用 [biz.aQute.bnd.builder](https://github.com/bndtools/bnd/blob/master/biz.aQute.bnd.gradle/README.md#gradle-plugins-for-bnd-workspace-builds) 构建 OSGI 模块
- OSGI 模块使用外部第三方的非 OSGI 模块
- 两个自定义 OSGI 模块使用不同的 logging 框架
    - 模块 `my-module-a` 使用 apache common-logging + log4j
    - 模块 `my-module-b` 使用Slf4j + logback
- spring boot 的 fat jar 包中内嵌 OSGI 容器，并调用自己的 OSGI 模块
- spring 的 Application Context 使用 OSGI 容器提供的服务作为 bean。
- [bndtools](http://bndtools.org/)
- [apache felix](http://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html)
- 示例:  [htl-examples](https://github.com/heervisscher/htl-examples/blob/master/core/src/main/java/com/adobe/examples/htl/core/service/impl/MySimpleServiceImpl.java)
- IDEA intellij plugin [osmorc](https://plugins.jetbrains.com/plugin/1816-osmorc)
    - [doc](https://www.jetbrains.com/help/idea/creating-a-project-from-bnd-bndtools-model.html)

- Apache Felix
    - [Apache Felix Framework Launching and Embedding](http://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-launching-and-embedding.html)
    - [Blueprint tutorial](http://aries.apache.org/documentation/tutorials/blueprinthelloworldtutorial.html)

    
- [Apache karaf](http://karaf.apache.org)
    - [Apache Karaf调研](https://blog.csdn.net/songzehao/article/details/82990385)
    - [Tutorials for Apache Karaf](http://liquid-reality.de/Karaf-Tutorial/)
    - [karaf-springboot](https://github.com/klebeer/karaf-springboot)

- [Apache ServiceMix](http://servicemix.apache.org/)

- [Apache Aries](http://aries.apache.org/) 是一堆规范标准？
     

- [fabric8](http://fabric8.io)
    - fabric8 : [Apache karaf](http://fabric8.io/guide/karaf.html)

## bnd

```bash
https://search.maven.org/remotecontent?filepath=biz/aQute/bnd/biz.aQute.bnd/4.2.0/biz.aQute.bnd-4.2.0.jar
```

创建 bnd 命令行

```bash
#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
java -jar ${DIR}/biz.aQute.bnd-4.2.0.jar "$@"
```





## x

```text
Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: MyService bundle

# bundle 的唯一名称
Bundle-SymbolicName: com.sample.myservice

# bundle 的版本
Bundle-Version: 1.0.0

# 指出哪个类要监听 bundle 的声明周期变化
Bundle-Activator: com.sample.myservice.Activator

# 声明该 bundle 要 import 的外部依赖
# 也可以指定版本的 范围，下面的这个例子是指 1.0.4 以及后面的版本都 OK
Import-Package:  org.apache.commons.logging;version="1.0.4"

# 声明该 bundle 要暴露给其他 bundle 使用的接口和版本
Export-Package:  com.sample.myservice.api;version="1.0.0"

# 如果该 bundle 使用了 JPA，则该 bundle 就是一个 persistence bundle。
# 该 header 列出所有的 persistence.xml
Meta-Persistence: entities/persistence.xml, 
  lib/thirdPartyEntities.jar!/META-INF/persistence.xml

# 该 header 标识该 bundle 是个 web 应用 bundle。 
# 并指出 default context
Web-ContextPath: /contextRoot

# 
Export-EJB: 

# 指出 blueprint 文件描述符
Bundle-Blueprint: /blueprint/*.xml
```


# 参考

- [OSGI中blueprint简介](https://blog.csdn.net/u012734441/article/details/51818300)
- [Example: OSGi bundle manifest file](https://www.ibm.com/support/knowledgecenter/en/SSEQTP_8.5.5/com.ibm.websphere.osgi.doc/ae/ra_bundle_mf.html)
- [apache aries blueprint](http://aries.apache.org/documentation/tutorials/blueprinthelloworldtutorial.html)
- gradle plugin [biz.aQute.bnd.workspace](https://github.com/bndtools/bnd/blob/master/biz.aQute.bnd.gradle/README.md#gradle-plugin-for-workspace-builds)
- [MENIFEST.MF 格式](https://bnd.bndtools.org/chapters/790-format.html)
- [Maven : Using Extensions](https://maven.apache.org/guides/mini/guide-using-extensions.html)
- [Apache Maven Wagon :: Providers](https://maven.apache.org/wagon/wagon-providers/) 
