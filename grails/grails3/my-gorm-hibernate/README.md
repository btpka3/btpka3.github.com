测试GROM Hibernate 访问关系数据库

# 步骤：
```
# 创建 grails 3 应用
grails create-app my-gorm-hibernate
cd my-gorm-hibernate
# 创建枚举类
# 创建domain类
# 创建controller
# 修改日志配置：logback.groovy, 启用对 "org.hibernate.SQL", 
  "org.hibernate.type" 的 TRACE 级别的日志监控，以方便调试创建数据库表等DDL，
  以及增删改查等DML。
  
# 启动 APP（可以在Idea Intellij 中执行）
grails run-app

# 创建表并插入记录
# 浏览器访问：http://localhost:8080/test/insert

# 查询
# 浏览器访问：http://localhost:8080/test/select

# 数据库确认
# 浏览器访问：http://localhost:8080/dbconsole/
# JDBC URL 为 application.yml 中配置的数据库URL，development环境默认为：
    jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
# 默认用户名为：sa，密码为空

```

# 参考
* Grails 3 文档：
    * 关于 [Datasource](http://docs.grails.org/latest/guide/conf.html#dataSource) 的配置，以及 Database Console 相关说明
    * 右上侧关于 "Database Mapping", "Domain Classes" 相关的快速参考（Quick Reference）
* [GORM for Hibernate](http://gorm.grails.org/6.0.x/hibernate/manual/)
* Hibernate 的 [Identifier generator](http://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/mapping.html#mapping-declaration-id-generator) 文档。





