

# 目的
- 学习 spring batch


# 参考

- [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
- [Batch applications(https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#howto-batch-applications)

    该示例主要流程如下
    - 从文件中读取记录，一个行映射成一个 Person Bean
    - 通过 PersonItemProcessor 对 Person bean 进行处理
    - 再写入到数据库中


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
# 会发现 有了一个 PERSON 表，且里面有转换后的数据。
```