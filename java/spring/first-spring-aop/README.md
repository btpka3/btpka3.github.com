

## 目的

- 了解 spring runtime proxy-based AOP 中 proxyTargetClass 的差异
- 了解 spring runtime proxy-based AOP 与 AspectJ Load-time-waving 的差异。 

## 运行

- JdkAopTest 直接单元测试即可
- CGlibAopTest 直接单元测试即可
- AspectJLtwAopTest 运行单元测试时，需要追加 jvm 参数 :

    ```txt
    -javaagent:/Users/zll/.gradle/caches/modules-2/files-2.1/org.aspectj/aspectjweaver/1.8.13/ad94df2a28d658a40dc27bbaff6a1ce5fbf04e9b/aspectjweaver-1.8.13.jar
    ```
    
    这种 AOP 会内部方法也会被 AOP 的。

### FIMXE
 
-  `spring-instrument-*.jar` 与 `aspectjweaver-*.jar` 的差别？
- 以下三者各种组合会怎样
    - `@EnableLoadTimeWeaving` + `EnableLoadTimeWeaving.AspectJWeaving` 枚举值的变更
    - `/META-INF/aop.xml` 存在与否
    - `@EnableAspectJAutoProxy`
- AspectJ LTW 时， `@Aspect` 类的实例是怎么初始化的，它如何依赖注入 spring 管理的其他 bean ？    
 

## 参考

- [aop.xml](https://www.eclipse.org/aspectj/doc/next/devguide/ltw-configuration.html)
