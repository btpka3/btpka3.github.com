
《[理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)》

《[Stateless Authentication with Spring Security and JWT](http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt)》

[spring-security-jwt demo](https://github.com/technical-rex/spring-security-jwt)


[spring-boot-gradle-plugin](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin)

```
User
```

## spring-security-oauth

[@github](https://github.com/spring-projects/spring-security-oauth)

OAuth2ClientConfiguration


《[SSO with OAuth2: Angular JS and Spring Security Part V](https://spring.io/blog/2015/02/03/sso-with-oauth2-angular-js-and-spring-security-part-v)》

《[Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)》

[UUA - User Account and Authentication Service](http://docs.cloudfoundry.org/api/uaa/)



```
curl acme:acmesecret@localhost:8080/oauth/token  \
-d grant_type=authorization_code -d client_id=acme     \
-d redirect_uri=http://example.com -d code=jYWioI


c
```
## 参考

1. [samples@spring-security-oauth](https://github.com/spring-projects/spring-security-oauth.git)
1. [oauth2-sso-demo@making](https://github.com/making/oauth2-sso-demo.git)
1. [UUA@github](https://github.com/cloudfoundry/uaa)

```
git clone https://github.com/spring-projects/spring-security-oauth.git
cd spring-security-oauth
mvn install -P bootstrap

cd samples/oauth2/tonr
mvn tomcat7:run

# 浏览器访问 http://localhost:8080/tonr2/ 
```



|URI                    |@FrameworkEndpoint         |Method |parameters                                      |Description                          |
|-----------------------|---------------------------|-------|------------------------------------------------|-------------------------------------|
|`/oauth/authorize`     |AuthorizationEndpoint      |GET    |client_id,state,redirect_uri,response_type,scope|从Client App将用户引导至此以完成授权的URL|
|                       |                           |POST   |user_oauth_approval                             |用户确认授权                           |
|`/oauth/confirm_access`|WhitelabelApprovalEndpoint |
|`/oauth/token`         |TokenEndpoint              |
|`/oauth/token_key`     |TokenKeyEndpoint           |
|`/oauth/error`         |WhitelabelErrorEndpoint    |
|`/oauth/check_token`   |CheckTokenEndpoint         |



## OAuth2RestTemplate


## 微信测试

[微信公众平台 - 管理测试号](http://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)


## FIXME

1. 单个工程多个 OAuth2 client 配置与使用。

## 代码分析

```
@EnableOAuth2Sso                                
    -> OAuth2SsoProperties                      // 读取配置 "security.oauth2.sso"
    -> OAuth2SsoDefaultConfiguration
    -> OAuth2SsoCustomConfiguration
    -> ResourceServerTokenServicesConfiguration // 不能和 @EnableAuthorizationServer 一起使用
    
@EnableResourceServer
    -> ResourceServerConfiguration  // 实现了 WebSecurityConfigurerAdapter
        #setConfigurers()           // 获取所有 bean : ResourceServerConfigurer/ResourceServerConfigurerAdapter
        #configure()                // 调用所有 bean : ResourceServerConfigurer/ResourceServerConfigurerAdapter

@EnableAuthorizationServer
    -> AuthorizationServerEndpointsConfiguration
        -> TokenKeyEndpointRegistrar                // BFPP : 
        #authorizationEndpoint()                    // 注册 bean : AuthorizationEndpoint
            #userApprovalHandler()
                AuthorizationServerEndpointsConfigurer#getUserApprovalHandler()
                    #userApprovalHandler()          // 根据配置创建 UserApprovalHandler
        #tokenEndpoint()                            // 注册 TokenEndpoint
        #configurers                                // 获取所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        #init()                                     // 调用所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        
    -> AuthorizationServerSecurityConfiguration     // 实现了 WebSecurityConfigurerAdapter
                                                    // 仅仅过滤 /oauth/token, /oauth/token_key, /oauth/check_token 这三个URL
        #configurers                                // 获取所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        #configure(ClientDetailsServiceConfigurer)  // 
        #configure(HttpSecurity)                    // 调用所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        -> ClientDetailsServiceConfiguration
            #clientDetailsServiceConfigurer()       // 注册 bean : ClientDetailsServiceConfigurer
            #clientDetailsService()                 // 注册 bean : ClientDetailsService
        -> AuthorizationServerEndpointsConfiguration
        
@EnableAutoConfiguration
OAuth2AutoConfiguration
    -> OAuth2ClientProperties                   // 读取配置 "security.oauth2.client"
    -> OAuth2AuthorizationServerConfiguration   // 提供默认 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        -> AuthorizationServerProperties        // 读取配置 "security.oauth2.authorization"
        -> BaseClientDetails
    -> OAuth2MethodSecurityConfiguration        // BFPP
        -> OAuth2ExpressionHandlerInjectionPostProcessor // BPP : DefaultMethodSecurityExpressionHandler -> OAuth2MethodSecurityExpressionHandler
    -> OAuth2ResourceServerConfiguration
        #resourceServer()                       // 默认提供一个 ResourceServerConfigurer(ResourceSecurityConfigurer)
        -> ResourceServerTokenServicesConfiguration
            #jwtTokenStore()                    // 注册 bean : JwtTokenStore
    -> OAuth2RestOperationsConfiguration
        -> OAuth2ProtectedResourceDetailsConfiguration
    #resourceServerProperties()                 // 读取配置 "security.oauth2.resource"

FrameworkEndpointHandlerMapping     // 查找所有 @FrameworkEndpoint 作为 controller，并允许自定义路径。 

@FrameworkEndpoint
    AuthorizationEndpoint
        #authorize()    // 根据参数创建 AuthorizationRequest，并保存到 session 中。
                        // FIXME : 用户登录
                        // 判断用户预授权。
                        // 预授权OK后，判断能否立即调转回去，若可以，则跳转
                        // 返回让用户授权表单画面
        #approveOrDeny()// 根据用户授权结果进行跳转
    WhitelabelErrorEndpoint
    TokenKeyEndpoint
    WhitelabelApprovalEndpoint
    CheckTokenEndpoint
    TokenEndpoint
```

# FIXME
微信 OAuth 登录