
## 运行
```
# 该命令会一直后台运行
gradle bootRun

# 使用 spring-boot-devtools 可以自动重启。可手动触发IDE生成新的class。
# 如果是命令行,可以
gradle build
``` 

## 总结

1. ACL#owner 并不对 `hasPermission` 判断起什么作用。——仅仅用来判断权限修改是否可行？
   需要对所有者也追加 ACE。
   
1. 并不会对 Permission#的mask 进行 bit 型的判断，只会整个指的判断。这样的话：

    * 需要一个 permission 插入一条记录，记录数会很多。
    * 可以使用的 permission 的总数还是相当可观的。整数的值域个。

## ACL


```
GlobalMethodSecurityConfiguration
ExpressionBasedPreInvocationAdvice
    #setExpressionHandler

# 配置
DefaultMethodSecurityExpressionHandler
    #createSecurityExpressionRoot()
        #getPermissionEvaluator()
        SecurityExpressionRoot#setPermissionEvaluator()

# 调用
SecurityExpressionRoot#hasPermission()
    AclPermissionEvaluator#hasPermission()
```
 
## 测试用例编写

```
#组织: 
    浙江          所有者：张三，被授权者: 李四，被授予权限：管理、读、监督
        杭州        继承：true
    河南          所有者：张三，被授权者: 李四，被授予权限：管理、读
        郑州        继承：false
    山东          
        威海  
确认：
1. 李四对 `浙江` :
    1. 有 `管理、读、监督` 权限
    1. 有 `读` 权限
    1. 有 `监督` 权限
1. 李四对 `杭州` :
    1. 有 `管理、读、监督` 权限
    1. 有 `读` 权限
    1. 有 `监督` 权限
1. 李四对 `郑州`    没有任何权限
1. 李四对 `山东`    没有任何权限
1. 李四对 `威海`    没有任何权限
1. 测试使用编码判断权限，而非使用注解
```
 
 
## FIXME
*  如何使用 mongodb?

    需要重写 :
    1. AclService / MutableAclService (JdbcAclService / JdbcMutableAclService)
    1. LookupStrategy (BasicLookupStrategy)


## Cache

```
CacheConfigurations

@EnableCaching
    -> CachingConfigurationSelector

@EnableAutoConfiguration
CacheAutoConfiguration
    -> CacheProperties
    -> CacheManagerCustomizers
    -> CacheConfigurationImportSelector
        -> CacheConfigurations

NoOpCacheConfiguration      // 注册 bean : CacheManager

```