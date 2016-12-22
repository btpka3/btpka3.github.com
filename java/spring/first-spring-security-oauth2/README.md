
# 运行

## 启动

```
# 为了防止 JSESSION cookie 冲突，使用不同的域名进行测试

cat <<EOF | sudo tee -a /etc/hosts
## OAuth 2.0 test
127.0.0.1   a.localhost     # authorization server
127.0.0.1   r.localhost     # resource server
127.0.0.1   c.localhost     # client app
127.0.0.1   s.localhost     # client sso
EOF

# 启动服务器
cd first-spring-security-oauth2
./gradlew :oauth2-authorization-server:bootRun
./gradlew :oauth2-client-app:bootRun

```

## 验证 OAuth2 authorization 授权模式

1. 通过 testcase 检验, 查看 MyClientAppTest#oauthAuthCode01() 代码
   以及运行结果。

    ```
    ./gradlew :oauth2-client-app:test
    ```

1. 通过浏览器访问校验: `http://c.localhost:10003/photo/authCode`

## 验证 OAuth2 implicit 授权模式
通过浏览器访问 `http://c.localhost:10003/implicit.html#` ，
依次点击 `去授权`，`去请求资源`、`传递AT到后台，让后台请求资源`,`清除 AT` 进行验证。
请留意浏览器的控制台输出、以及网络监控。


## 验证 OAuth2 password 授权模式

通过浏览器访问校验: `http://c.localhost:10003/photo/password`

```
# 命令行获取AT。注意，这里配合了 Http Basic 认证来认证 client
curl http://MY_CLIENT:secret@a.localhost:10001/oauth/token \
    -d grant_type=password \
    -d username=a_admin \
    -d password=a_admin \
    -d scope=read \
    --trace-ascii /dev/stdout 

```

## 验证 OAuth2 client 授权模式

通过浏览器访问校验: `http://c.localhost:10003/photo/client`


```
# 命令行获取AT。注意，这里配合了 Http Basic 认证来认证 client
curl http://MY_CLIENT:secret@a.localhost:10001/oauth/token \
    -d grant_type=client_credentials \
    -d scope=read \
    --trace-ascii /dev/stdout 
    
响应如为：{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiTVlfUlNDIl0sInNjb3BlIjpbInJlYWQiXSwiZXhwIjoxNDgyMDA0MTk1LCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJqdGkiOiIzYWY3NDVlZS1jYWIyLTRjNzctYjdmNi1jOWU4NDNhYzEzZDkiLCJjbGllbnRfaWQiOiJNWV9DTElFTlQifQ.T4Lq5vkNPNE6tDjCIf8NtPjzV6T15pU3WaFoHqnCtv8", 
    "token_type": "bearer", 
    "expires_in": 43199, 
    "scope": "read", 
    "jti": "3af745ee-cab2-4c77-b7f6-c9e843ac13d9"
}

该 demo 中配置的是使用 JWT，解析后内容如下（注意：作为client app，则无需关心该内容）:

access_token.header = {
  "alg": "HS256",
  "typ": "JWT"
}

access_token.payload = {
  "aud": [
    "MY_RSC"
  ],
  "scope": [
    "read"
  ],
  "exp": 1482004195,
  "authorities": [
    "ROLE_CLIENT"
  ],
  "jti": "3af745ee-cab2-4c77-b7f6-c9e843ac13d9",
  "client_id": "MY_CLIENT"
}

```


# OAuth 2

## Authorization Code 授权方式

```
# 请求授权
GET /oauth/authorize
        ?response_type=code
        &client_id=xxx
        &redirect_uri=xxx
        &scope=xxx
        &state=xxx

# 授权返回
GET redirect_uri
        ?code=xxx
        &state=xxx
        
# 换取 AT
POST /oauth/token
        grant_type=xxx
        &code=xxx
        &redirect_uri=xxx
        &client_id=xxx

# 响应
{
   "access_token":"2YotnFZFEjr1zCsicMWpAA",
   "token_type":"example",
   "expires_in":3600,
   "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
   "example_parameter":"example_value"
 }
```

## Implicit 授权方式

```
GET /oauth/authorize
        ?response_type=token
        &client_id=xxx
        &redirect_uri=xxx
        &scope=xxx
        &state=xxx
        
GET redirect_uri
        #access_token=xxx
        &token_type=xxx
        &expires_in=xxx
        &scope=xxx
        &state=xxx
```

## 用户密码 授权方式

```
# 请求
PSOT /oauth/token
        ?grant_type=password
        &username=xxx
        &password=xxx
        &scope=xxx
# 响应
{
   "access_token":"2YotnFZFEjr1zCsicMWpAA",
   "token_type":"example",
   "expires_in":3600,
   "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
   "example_parameter":"example_value"
}
```

## client 授权方式

```
# 请求
PSOT /oauth/authorize
        ?grant_type=client_credentials
        &scope=xxx

# 响应
{
   "access_token":"2YotnFZFEjr1zCsicMWpAA",
   "token_type":"example",
   "expires_in":3600,
   "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
   "example_parameter":"example_value"
}
```


# 参考

* 《[理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)》
* 《[Stateless Authentication with Spring Security and JWT](http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt)》
* [spring-security-jwt demo](https://github.com/technical-rex/spring-security-jwt)
* [spring-boot-gradle-plugin](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin)
* [auth0](https://auth0.com/#)
* [spring-security-oauth@github](https://github.com/spring-projects/spring-security-oauth)
* 《[SSO with OAuth2: Angular JS and Spring Security Part V](https://spring.io/blog/2015/02/03/sso-with-oauth2-angular-js-and-spring-security-part-v)》
* 《[Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)》
* [UUA - User Account and Authentication Service](http://docs.cloudfoundry.org/api/uaa/)
* [微信公众平台 - 管理测试号](http://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)
* [samples@spring-security-oauth](https://github.com/spring-projects/spring-security-oauth.git)
* [oauth2-sso-demo@making](https://github.com/making/oauth2-sso-demo.git)
* [UUA@github](https://github.com/cloudfoundry/uaa)
* [OAuth2 SSO](http://docs.spring.io/spring-boot/docs/1.4.2.RELEASE/reference/htmlsingle/#boot-features-security-oauth2-single-sign-on)

```
git clone https://github.com/spring-projects/spring-security-oauth.git
cd spring-security-oauth
mvn install -P bootstrap

cd samples/oauth2/tonr
mvn tomcat7:run

# 浏览器访问 http://localhost:8080/tonr2/ 
```



|URI                    |@FrameworkEndpoint         |Description                          |
|-----------------------|---------------------------|-------------------------------------|
|`/oauth/authorize`     |AuthorizationEndpoint      |GET: 授权表单画面、POST: 提交授权网址|
|`/oauth/token`         |TokenEndpoint              |拿code换取AT的网址，password/client 模式直接请求AT的网址|
|`/oauth/token_key`     |TokenKeyEndpoint           |如果AT是JWT，可从此处获取验证签名的秘钥|
|`/oauth/check_token`   |CheckTokenEndpoint         ||
|`/oauth/confirm_access`|WhitelabelApprovalEndpoint ||
|`/oauth/error`         |WhitelabelErrorEndpoint    ||


## 代码分析

```
@EnableResourceServer
    -> ResourceServerConfiguration  // 实现了 WebSecurityConfigurerAdapter
        #setConfigurers()           // 获取所有 bean : ResourceServerConfigurer/ResourceServerConfigurerAdapter
        #configure()                // 调用所有 bean : ResourceServerConfigurer/ResourceServerConfigurerAdapter
            ResourceServerConfigurer#configure(ResourceServerSecurityConfigurer)
            HttpSecurity#apply(ResourceServerSecurityConfigurer)
            
            ResourceServerConfigurer#configure(HttpSecurity)
            #

ResourceServerSecurityConfigurer
    #configure(HttpSecurity)        // 注册 OAuth2AuthenticationProcessingFilter

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
        #whitelabelApprovalEndpoint()               // 注册 bean : 用以 `/oauth/confirm_access` 生成用户确认授权的表单画面
        #whitelabelErrorEndpoint()                  // 注册 bean : 用以 `/oauth/error` 生成用户拒绝授权的错误画面
        
    -> AuthorizationServerSecurityConfiguration     // 实现了 WebSecurityConfigurerAdapter
                                                    // 仅仅过滤 /oauth/token, /oauth/token_key, /oauth/check_token 这三个URL
                                                    // 注意：这三个路径默认有不同的权限，需要自行检查配置。
        #configurers                                // 获取所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        #configure(ClientDetailsServiceConfigurer)  // 
        #configure(HttpSecurity)                    // 调用所有 bean : AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
        -> ClientDetailsServiceConfiguration
            #clientDetailsServiceConfigurer()       // 注册 bean : ClientDetailsServiceConfigurer
            #clientDetailsService()                 // 注册 bean : ClientDetailsService
        -> AuthorizationServerEndpointsConfiguration

@EnableOAuth2Client
    -> OAuth2ClientConfiguration
        #oauth2ClientContextFilter()            // 注册 bean : OAuth2ClientContextFilter
        #oauth2ClientContext()                  // 注册 bean : session 作用域 : DefaultOAuth2ClientContext
        #accessTokenRequest()                   // 注册 bean : request 作用域 : DefaultAccessTokenRequest

@EnableOAuth2Sso                                
    -> OAuth2SsoProperties                      // 读取配置 "security.oauth2.sso"
    -> OAuth2SsoDefaultConfiguration            // 实现了 WebSecurityConfigurerAdapter
    -> OAuth2SsoCustomConfiguration             // BPP, 通过AOP在调用 WebSecurityConfigurerAdapter#getHttp() 时追加额外配置
                                                // 追加 SsoSecurityConfigurer 对 HttpSecurity 进行配置
        -> SsoSecurityConfigurer
            #configure(HttpSecurity)            // 追加 OAuth2ClientAuthenticationProcessingFilter
            #addAuthenticationEntryPoint()      // 针对网站和异步请求增加不同的认证点
    -> ResourceServerTokenServicesConfiguration // 不能和 @EnableAuthorizationServer 一起使用
        #remoteTokenServices()                  // 注册 bean : RemoteTokenServices
        #userInfoRestTemplateFactory()          // 注册 bean : UserInfoRestTemplateFactory
                                                // 会依赖注入所有的 UserInfoRestTemplateCustomizer、OAuth2ProtectedResourceDetails、OAuth2ClientContext
            #getUserInfoRestTemplate()          // 会调用所有的 UserInfoRestTemplateCustomizer
    

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
    -> OAuth2RestOperationsConfiguration        // 只有配置了 "security.oauth2.client.client-id" 时才启用
        -> SessionScopedConfiguration           // 有 bean OAuth2ClientConfiguration 时启用
            -> OAuth2ProtectedResourceDetailsConfiguration
                #oauth2RemoteResource()         // 注册 bean : AuthorizationCodeResourceDetails
            #oauth2ClientFilterRegistration     // 在 servlet 容器中配置 oauth2ClientContextFilter
            -> ClientContextConfiguration
                #oauth2ClientContext()          // 注册 bean : session 作用域 : DefaultOAuth2ClientContext
        -> RequestScopedConfiguration           // 无 bean OAuth2ClientConfiguration 时启用
            #oauth2ClientContext()              // 注册 bean : request 作用域 : DefaultOAuth2ClientContext
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
    
FIXME OAuth2ClientContext 是怎么创建的？

OAuth2RestTemplate
    #doExecute
        super#doExcecute()
            this.createRequest()
                #getAccessToken()
                    OAuth2ClientContext#getAccessToken()
                    #acquireAccessToken()
                        AccessTokenProvider#obtainAccessToken       // UserRedirectRequiredException

AccessTokenProvider
    AccessTokenProviderChain
    AuthorizationCodeAccessTokenProvider 
    ImplicitAccessTokenProvider
    ResourceOwnerPasswordAccessTokenProvider
    ClientCredentialsAccessTokenProvider

TokenGranter
    CompositeTokenGranter
    AbstractTokenGranter
        AuthorizationCodeTokenGranter
        RefreshTokenGranter
        ImplicitTokenGranter
        ClientCredentialsTokenGranter
        ResourceOwnerPasswordTokenGranter

OAuth2Authentication    // 包含以下两个字段
    1. OAuth2Request storedRequest;         // 代表 Client APP
    2. Authentication userAuthentication;   // 代表 User
   

OAuth2ClientContexFilter                // 用以捕获 UserRedirectRequiredException 并将用户重定向到 auth server
    #redirectUser
Oauth2ClientAuthenticationProcessingFilter
OAuth2AuthenticationProcessingFilter
TokenEndpointAuthenticationFilter
ClientCredentialsTokenEndpointFilter

```

# FIXME

1. implicit 获取的 AT 能否从 SPA 传递给API后台，让API后台代为请求资源？
 
   ```
   // TRY : 需要当前用户已经登录的情况下
   OAuth2AccessToken at = new DefaultOAuth2AccessToken(params.at)
   myClientOAuth2RestTemplate.getOAuth2ClientContext().setAccessToken(at)
   myClientOAuth2RestTemplate.xxx()
   ```
   
   貌似理论上可行，但是后台服务器在集群，就得保证 session 也在集群。否则 API 服务器切换时将出错。

 
1. 单个工程多个 OAuth2 client 配置与使用。

微信 OAuth 登录
OAuth2ClientContext

DefaultOAuth2AccessToken

AuthenticationScheme

JaxbOAuth2AccessTokenMessageConverter
DefaultClientAuthenticationHandler
BaseOAuth2ProtectedResourceDetails



