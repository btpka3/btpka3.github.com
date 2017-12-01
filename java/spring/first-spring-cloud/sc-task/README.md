

# 参考

- org.springframework.cloud.task.configuration.ResourceLoadingAutoConfiguration
- org.springframework.cloud.task.repository.support.TaskRepositoryInitializer
- org.springframework.cloud.task.configuration.TaskProperties 
- EnableTaskLauncher

# 运行

```bash
cd src/test/docker
docker-compose up --build
#docker-compose down

cd ${PROJECT_ROOT}
./gradlew bootRun

# 浏览器访问 http://localhost:81 并登陆
Driver Class    : org.h2.Driver
JDBC URL        : jdbc:h2:tcp://localhost/~/test
User Name       : sa
Password        : EMPTY
# 会发现有三张表：TASK_EXECUTION 、TASK_EXECUTION_PARAMS 、TASK_TASK_BATCH 
# 其中 TASK_EXECUTION 有刚刚执行过的信息。
```


# FIXME
- spring-cloud-task 针对的是短时运行的任务，他和任务调度器有何关系。
  有时候会有很频繁的任务调度的。不至于每次都独立运行JVM，执行后并销毁 JVM 吧。
- spring-cloud-task 与 Apache Storm, Flink 等分布式计算框架对比呢？
- 能否一个 jar 包多个任务呢？但貌似只能一次启动一个了，因为 taskName， executionid 只能统一配置
- 只是牵涉到了任务执行，并不牵涉到任务调度配置的存储，是否重复执行，是否正在执行等相关内容。
- 文档中提到的 spring-cloud-deployer 都还未发布
