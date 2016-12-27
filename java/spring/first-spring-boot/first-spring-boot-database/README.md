
## 运行

验证 spring-boot 对关系数据库数据库的配置与访问。

默认可以通过 `http://localhost:8080/h2-console` 访问 h2 database 控制台

```

```

## 启动独立的 h2 server

### 通过 spring 

参考 H2 database 的参考手册 : 《[Using Spring](http://h2database.com/html/tutorial.html#spring)》，
在 spring 中配置一个bean 即可。

XML 配置：

```
<bean id = "org.h2.tools.Server"
            class="org.h2.tools.Server"
            factory-method="createTcpServer"
            init-method="start"
            destroy-method="stop">
    <constructor-arg value="-tcp,-tcpAllowOthers,-tcpPort,8043" />
</bean>
```

如何通过 Java 配置 实现？可以通过 BeanFactoryPostProcessor/BeanPostProcessor 来实现。
具体例子请参考 H2Conf.

### 通过 gradle 启动

```
# 配合 gradle 的 application 插件
./gradlew :first-spring-boot-database:run -DmainClass=org.h2.tools.Server -Dexec.args="-?"
```


## FIXME
1. JPA + Hibernate 

