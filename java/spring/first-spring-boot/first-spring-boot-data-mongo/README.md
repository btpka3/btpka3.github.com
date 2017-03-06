

# 目的
学习 spring-data-mongo


# 运行

```
./gradlew :first-sprint-boot-mongo:bootRun

# 浏览器访问（参考 TestController)
http://localhost:8080/tplAdd1

# 连接到 mongodb 数据库进行确认，可以使用 RoboMongo 或命令行
```

# 参考
* spring-data-mongo [Reference](http://docs.spring.io/spring-data/data-mongo/docs/1.10.0.RELEASE/reference/html/#repositories.create-instances)、
 [API](http://docs.spring.io/spring-data/data-mongo/docs/1.10.0.RELEASE/api/index.html?org/springframework/data/mongodb/core/MongoTemplate.html)

* spring-data-common [Reference](http://docs.spring.io/spring-data/commons/docs/current/reference/html/)

* [QueryDSL, spring-boot & Gradle](http://stackoverflow.com/questions/22773639/querydsl-spring-boot-gradle)
* [spring-boot-querydsl demo](https://github.com/mariuszs/spring-boot-querydsl/blob/master/build.gradle)


# 总结
1. 自动更新索引:  索引变更需要手动在原有数据库清除索引，然后spring-data-mongo才能新建搜因
1. 级联保存: 根据 spring-data-mongo 的[文档](http://docs.spring.io/spring-data/data-mongo/docs/1.10.1.RELEASE/reference/html/#mapping-usage-references)

    > The mapping framework does not handle cascading saves

   因此，框架不能自动级联保存，必须手动保存。

1. 内嵌文档，个人建议（1）使用用inner class （2）不要加上 @Document 注解（3）必要时加上 @Indexed 注解。
1. 使用 @Id, @LastModifiedDate,@Version 等注解自动更新？ 
1.  AbstractMongoEventListener、BeforeSaveEvent 等事件处理是同步的, 
1. 一对一，一对多，多对多等关系. 文档中有提到说，不需要使用类似 JPA 中 `@OneToMany` 注解
1. Map -> bean, 如果使用 groovy bean, 必须使用 groovy 的  `[:] as User` 语法，包含内嵌文档。
1. 通过 `@Indexed` 创建后并变更索引会抛出异常，需要手动删除索引，请参考 `MongoPersistentEntityIndexCreator`



# TODO
1. 正常访问Mongodb数据库
1. 大数据量访问
1. aggregate
1. query DSL
1. 异步执行
1. spring-data-common 中扩展，对于 Controller 支持额外参数类型
    * 自定义数据库 Domain 类
    * Pageable(PageableArgumentResolver)
    * Sort(SortArgumentResolver)

1. queryDsl : [QueryDSL for Mongo with Spring and gradle](http://stackoverflow.com/questions/22564178/querydsl-for-mongo-with-spring-and-gradle)、
   [spring-data-demo](https://github.com/corneil/spring-data-demo)、
   [IntelliJ with gradle and querydsl](http://stackoverflow.com/questions/29689971/intellij-with-gradle-and-querydsl)
   [gist-5377401](https://gist.github.com/EdwardBeckett/5377401)
   [querydsl-plugin](https://github.com/ewerk/gradle-plugins/tree/master/querydsl-plugin)
   
   querydsl-plugin -> MongoAnnotationProcessor
1. MappingMongoConverter

