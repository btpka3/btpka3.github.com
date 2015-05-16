# 目的

* 学习 Gralde
* 学习 Spring Boot
* 尝试使用 Groovy 进行开发
* 尝试取代 Grails 进行开发


# 构建

1. 优化 Gradle 配置

    ```
    echo "org.gradle.daemon=true" >> ~/.gradle/gradle.properties
    ```

1. 使用 gradle 初始化目录结构

    ```
    gradle init
    ```

1. 使用 [Spring Initializr](http://start.spring.io/) 获取初始化配置。

1. 初始化 IDEA 的项目文件

    ```
    gradle tasks
    gradle cleanIdea idea  # 如果进行了合理配置，这一步骤会下载源码jar包的
    ```

1. 编写一个 Controller（比如HiController）

    ```
    package me.test

    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController

    @RestController
    class HiController {

        @RequestMapping("/")
        public String index() {
            return "Greetings from Spring Boot!";
        }
    }
    ```

1. 命令行运行

    ```
    gradle bootRun
    ```

1. Intellij IDEA 中 debug

    1. 新建 Gradle 运行项目
    1. name可以随意，一般为工程名称
    1. 勾选中 `Single instance only`
    1. Gradle project 为该Gradle工程根目录中的 build.gradle
    1. Tasks 为 `bootRun`
    1. 根据需要运行或调试

1. 打包，之后就可以在 `build/distributions` 中找到相应的压缩包

    ```
    gradle distTar
    ```


# 参考

* [Building an Application with Spring Boot](http://spring.io/guides/gs/spring-boot/)
