
```
# 该命令会一直后台运行
gradle bootRun

# 使用 spring-boot-devtools 可以自动重启。可手动触发IDE生成新的class。
# 如果是命令行,可以
gradle build

```

* [spring boot gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html)


# 提供静态资源
可以分析 `WebMvcAutoConfiguration` 源代码,静态资源可以在以下位置中:

```
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
		"classpath:/META-INF/resources/", "classpath:/resources/",
		"classpath:/static/", "classpath:/public/" };
```

也可以看看 `ResourceHandlerRegistry` 的源码,条件追加了 `classpath:/META-INF/resources/webjars/` 这个路径

|test page                    | test for                        |
|-----------------------------|---------------------------------|
|http://localhost:8080/a.txt  |`classpath:/META-INF/resources/` |
|http://localhost:8080/a.html |`classpath:/resources/`          |
|http://localhost:8080/a.css  |`lasspath:/static/`              |
|http://localhost:8080/a.js   |`classpath:/public/`             |
 


