

# 运行

gradle build

```
./gradlew wrapper --gradle-version=6.0.1 --distribution-type=all
./gradlew  first-spring-boot-security:bootRun
./gradlew  first-spring-boot-web:bootRun
```

// https://docs.gradle.org/current/userguide/dependency_management.html#controlling_transitive_dependencies
// https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph




# 插件

* [gradle-git-properties](https://github.com/n0mer/gradle-git-properties): 提供 'generateGitProperties' gradle 任务，
并生成 `git.properties`

* spring boot 的 [maven 插件](http://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/maven-plugin/build-info-mojo.html)、gradle 插件
可以生成 `${project.build.outputDirectory}/META-INF/build-info.properties`


# FIXME

- Atlas, Graphite, InfluxDB, Prometheus
    - [System Properties Comparison Graphite vs. InfluxDB vs. Prometheus](https://db-engines.com/en/system/Graphite%3bInfluxDB%3bPrometheus)


