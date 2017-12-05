

# 目的
- 学习 spring batch


# 参考

- [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
- [Batch applications(https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#howto-batch-applications)

    该示例主要流程如下
    - 从文件中读取记录，一个行映射成一个 Person Bean
    - 通过 PersonItemProcessor 对 Person bean 进行处理
    - 再写入到数据库中
- org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
- org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration
- org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration      // 默认
- org.springframework.batch.core.configuration.annotation.ModularBatchConfiguration
- FlowBuilder
- CommandLineJobRunner
- javax.batch.api.BatchProperty

# 运行

```bash
# 启动数据库
docker-compose -f first-spring-boot-batch/src/test/docker/docker-compose.yml up
# docker-compose -f first-spring-boot-batch/src/test/docker/docker-compose.yml down


# 启动
./gradlew :first-spring-boot-batch:bootRun

# 浏览器访问 http://localhost:81 并登陆
Driver Class    : org.h2.Driver
JDBC URL        : jdbc:h2:tcp://localhost/~/test
User Name       : sa
Password        : EMPTY
# 会发现 有以下几张表：
#   BATCH_JOB_EXECUTION 
#   BATCH_JOB_EXECUTION_CONTEXT 
#   BATCH_JOB_EXECUTION_PARAMS 
#   BATCH_JOB_INSTANCE
#   BATCH_STEP_EXECUTION 
#   BATCH_STEP_EXECUTION_CONTEXT  
#   PERSON          # 里面有转换后的数据。
```

# 总结 
- JobRepository # BATCH_JOB_INSTANCE
- JobLauncher
- Job
    - FlowJob
    - GroupAwareJob
    - JsrFlowJob
    - SimpleJob
- JobInstance, JobExecution
- ItemReader
    - AggregateItemReader 
    - AmqpItemReader 
    - ExceptionThrowingItemReaderProxy 
    - FlatFileItemReader 
    - GeneratingTradeItemReader
    - HibernateCursorItemReader 
    - HibernatePagingItemReader 
    - IbatisPagingItemReader 
    - InfiniteLoopReader 
    - ItemReaderAdapter 
    - IteratorItemReader 
    - JdbcCursorItemReader 
    - JdbcPagingItemReader 
    - JmsItemReader 
    - JpaPagingItemReader 
    - LdifReader 
    - ListItemReader 
    - MappingLdifReader 
    - MongoItemReader 
    - MultiResourceItemReader 
    - Neo4j4ItemReader 
    - Neo4jItemReader 
    - OrderItemReader 
    - RepositoryItemReader 
    - ResourcesItemReader 
    - SingleItemPeekableItemReader 
    - StagingItemReader 
    - StaxEventItemReader 
    - StoredProcedureItemReader 
    - SynchronizedItemStreamReader
- ItemWriter
- ItemProcessor


# FIXME
- 如何调度？
    spring-batch 不是一个调度框架。
- 与 各种大数据框架相比呢(比如 Apache Hadoop, AKKA)？
    - [Spring Batch vs Akka](https://www.coveros.com/spring-batch-vs-akka-concurrency/)
- 与 spring framework 框架中 Scheduler 的关系是？
- 与 spring-cloud-stream, spring-cloud-bus，spring-message, spring-integration 如何集成？
- 与 JDK 8 中的 Stream，JDK 9 中的 Flow, RxJava 的区别是？ 

