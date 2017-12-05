

## 目的

1. 验证 MongoDB 下 Spring-security-acl 操作

## 步骤 

```
# 通过 docker 安装并启动 mongodb
docker start my-mongo

grails create-app my-sec-acl

# 修改 BuildConfig.groovy, Config.groovy, DataSource.groovy

# 创建用户、权限 domain，并关联
grails s2-quickstart me.test User Role

# 修改 BootStrap.groovy 对数据库进行初始化


# 创建 ACL 相关的 domain
grails s2-create-acl-domains 

# 修改 domain : 统一使用 MongoDB 的 String(ObjectId) 作为主键
```


## 总结

```
grails-spring-security-core
    -> filterInvocationInterceptor
        -> accessDecisionManager
            -> SpringSecurityUtils.voterNames
                ref("authenticatedVoter")
                ref("roleVoter")
                ref("webExpressionVoter")
                ref("closureVoter")
        -> AnnotationFilterInvocationDefinition // 仅仅处理插件提供的  @Secured

AnnotationFilterInvocationDefinition#initialize()  初始化的时候，针对Controller注册 URL权限。

// grails.plugin.springsecurity.annotation.Secured  仅仅用于 controller，而非 service。
grails-spring-security-core 插件仅仅注册了了 "filterInvocationInterceptor" bean， 而没有注册 MethodSecurityInterceptor


grails-spring-security-acl
    -> MethodSecurityInterceptor
        -> aclAccessDecisionManager
            ->  ref('roleVoter')
                ref('authenticatedVoter')
                ref('preInvocationVoter')
                ref('groovyAwareAclVoter')
                AclEntryVoter // 如果domain上面有 @AclVoters, @AclVoter, 则为创建
                
    -> aclSecurityMetadataSource                                    // ProxyAwareDelegatingMethodSecurityMetadataSource
        ->  ref('prePostAnnotationSecurityMetadataSource')
            ref('springSecuredAnnotationSecurityMetadataSource')    // 注意：service 类只能使用 spring security 框架本身提供的 @Secured
            ref('serviceStaticMethodSecurityMetadataSource')


ClosureVoter








```


## 参考

* [Grails Spring Security Core Plugin](https://grails-plugins.github.io/grails-spring-security-core/)
* [Grails Spring Security ACL Plugin](https://grails-plugins.github.io/grails-spring-security-acl/)
