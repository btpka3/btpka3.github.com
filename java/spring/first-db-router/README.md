
# 原理：  
* 在Spring进行JDBC事务AOP之前，先自定义AOP： TransactionSynchronizationManager#bindResource() 方法绑定 数据源key(比如：hospitalId)
    * 由于有事务传播机制，这里需要以Stack或链表的方式绑定 数据源key
    * 如果新绑定值与旧绑定值不一致，应当抛出异常。
    * 事务方法返回前需要清除已经绑定的值
* Spring在JDBC事物AOP时，会通过AbstractRoutingDataSource路由到具体的datasource，再获取具体的Connection并根据设置确定是否创建新事务
* 我们的业务代码在调用JdbcTemplate或MyBatis的dao时，会通过AbstractRoutingDataSource路由到具体的datasource，处理只与数据相关的DB操作。

# 参考 Spring 的既有类：
    * AbstractRoutingDataSource
    * IsolationLevelDataSourceRouter
    * DataSourceLookup
    * DelegatingDataSource
    * TransactionInfo
    * TransactionAspectSupport#prepareTransactionInfo()
    * DefaultTransactionStatus
    * 《[dynamic-datasource-routing](https://spring.io/blog/2007/01/23/dynamic-datasource-routing)》

# 代码说明：
* DataSourceKeyAdvice 负责在Spring JDBC AOP前绑定具体的数据库路由key，获取数据源KEY的具体规则请参考Javadoc。
* RoutingDataSourceImpl AbstractRoutingDataSource的一个实现
* 该demo需通过命令 `mvn jetty:run` 运行。之后访问 `http://localhost:8080` 进行访问。
除了基本的数据库操作的示例以外，还为了给大家展示跨多院区的事物控制易出错的现象而给出了例子。

# 注意事项：
  实现数据库路由的代码其实很少，但是由于牵涉到数据库的事务控制和AOP，使用时还是有一些要求的。
* 数据源在应用启动时必须预先定义好，无法运行时动态添加、删除。
* 必须以院区为单位进行事务控制，可以相同数据源中事务方法调用另一个事务方法，跨数据源则不允许。
  示例: 如果 service1#method1(hospitalId1) 调用 service2#method2(hospitalId2)，如果两个院区ID不一致，则应抛出异常。
* 所有有Spring 事务控制AOP前都必须AOP 数据库路由KEY的Advice。
* 建议使用 @Transactional 注解来标明事务控制。因为：
    * 使用Spring声明式事物控制，不如 @Transactional 灵活。
    * Service方法中将来可能内部需要操作多院区的数据，则该方法就不能被事物AOP，但它可以调用各个子的事物AOP方法，然后再做汇总等处理。
* 业务方法中，除了要能从方法参数中获取数据源路由KEY，在操作数据时也要加上相应的where条件（比如 `where hospitalId = ?`）,否则可能会误操作数据，比如 requiredTransSucceed 实例。


# TODO
  DataSourceKeyAdvice 中基于注解+SpEL的获取数据库路由key尚未实现。
