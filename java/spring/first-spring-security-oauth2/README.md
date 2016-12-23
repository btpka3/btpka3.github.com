
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

## 验证 html OAuth2 SSO

通过浏览器访问校验: `http://s.localhost:10004/sec`

## 验证 Ajax OAuth2 SSO
通过浏览器访问校验: `http://s.localhost:10004/ajaxSso.html`
并点击 `GET /SEC` 按钮。

# OAuth 2



|                                                               |auth code  |Implicit   |password   |client |
|---------------------------------------------------------------|-----------|-----------|-----------|-------|
|所有 AT 都从 Authorization Endpoint (`/oauth/authorize`) 返回？  | no        |            |           |       |
|所有 AT 都从 Token Endpoint (`/oauth/token`) 返回？              | yes       |            |           |       |
|AT 未泄露给 User Agent?                                         | yes       |            |           |       |
|Client App 可以被认证？                                          | yes       |            |          |       |
|AT 可以被 Refresh?                                              | yes       |             |          |       |
|一次 http 请求就可以返回 AT?                                     | no         |             |          |       |
|大多数请求都是 Server-to-Server?                                 | yes        |            |          |       |


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

AccessTokenConverter
    JTI     // TOKEN_ID
    ATI     // ACCESS_TOKEN_ID

```

# FIXME

* implicit 获取的 AT 能否从 SPA 传递给API后台，让API后台代为请求资源？
 
   ```
   // TRY : 需要当前用户已经登录的情况下
   OAuth2AccessToken at = new DefaultOAuth2AccessToken(params.at)
   myClientOAuth2RestTemplate.getOAuth2ClientContext().setAccessToken(at)
   myClientOAuth2RestTemplate.xxx()
   ```
   
   貌似理论上可行，但是后台服务器在集群，就得保证 session 也在集群。否则 API 服务器切换时将出错。

 
* 单个工程多个 OAuth2 client 配置与使用。
 
   可以的，需要使用不同的 OAuth2ProtectedResourceDetails 配置不同的 OAuth2RestTemplate。

* 微信 OAuth 登录

* 注册自己平台的统一账号

    ```
    
    ```
* 如何利用 OAuth2 创建 Stateless 的 Restful API 服务器?

    把该服务器当成 OAuth2 的 Resource Server 即可。配合使用 Implicit 授权模式。
    
* Implicit 模式下如何 SSO？

    后台 API 需提供接口，判断当前存储在浏览器端的 AT 是否有效，
无效的话就引导用户到 authorization 服务器上进行授权，并返回。 

* Implicit 授权模式没有 refresh token，如何保障持久性登录？

    在 OAuth2 的 authorization 服务器上使用集群、分布式、持久化的session。通过无缝重定向确保。

* 如何有效使用 JWT ? 

```
access_token.payload = {
    // for user
    user_name   :   ""
    authorities :   [""],
    scope       :   [""],
    jti         :   "",     // token id。可以理解为该 AT 的主键，可以用于防范重放攻击 (replay attack) ?
    exp         :   0,      // 过期时间
    grant_type  :   "",     // 可选：授权方式
    client_id   :   "",     // 
    aud         :   [""] ,  // 授权的 resource id 列表
    *           :   *       // 所有额外信息
}

# rfc7519
iss         : (R) Issuer Claim
sub         : (R) Subject Claim
aud         : (R) Audience Claim
exp         : (R) Expiration Time Claim
nbf         : () Not Before Claim
iat         : (R) Issued At Claim
jti         : () WT ID Claim

http://openid.net/specs/openid-connect-core-1_0.html
auth_time   : (O)
nonce       :   
acr         : (O) Authentication Context Class Reference
amr         : (O) Authentication Methods References
azp         : Authorized party
```

    1. 使用公钥、私钥
    2. 使用额外的校验:
    
        * 校验 client_id 是否与自己的应用的 client id 一致
        * 通过扩展 JwtAccessTokenConverter#enhance() 对 JWT 追加额外信息：比如创建时间
        * 通过扩展 JwtAccessTokenConverter#extractAuthentication(), 对 额外信息进行判断。
    3. resource server，client app 保存所有的 AT，通过 jti 判断是否重复。
    4. 启用 `iat`, `nbf` 等字段，并加强控制。

* 使用 JWT 时，如何防范重放攻击 (replay attack) ? 
    使用黑名单机制，保存已经废弃的 at. 
    
* JWT 类型的 AT 不能 revoke？
    
    1. 需要 client app， resource server 通过 轮训 从 auth 服务器上获取 revoke 的 at
    2. auth server 主动推送给 client app， resource server 未过期且已被revoke的 at
    3. client app， resource server 上 at 存储设计：
    
        * at、revoked_at 表中只保存未过期的，
        * revoked_at 表中数据从 auth_server 轮训获取。

* 如何实现单点退出？

    同 at 的 revoke， 最好由 auth server 主动通知 client app、 resource server 被撤销的 AT。
    如果是使用了session，则需要 destory session。

* 微信账号绑定后，使用其他微信账号登录？

    微信登录后，如果已经绑定，则使用绑定的身份登录，否则新建本地账户。

* 如何处理用户多次授权的 AT? 用户第一次微信登录，第二次本地账户登录时，能够继续使用第一次微信登录的 AT？

    应当保存所有用户授权的 AT，但使用时，应当只用最新，scope 最全的。

* 何时使用无需用户授权的登录？何时使用需要授权的登录？

    目前来看，需要用户授权的，只有获取用户基本信息。
    因此，应当只在用户通过微信注册账号，或者微信绑定账号时才使用 `snsapi_userinfo` scope，让用户授权。
    其他情形则应当使用 `snsapi_base` scope 仅仅进行登录。

* 微信 AT 刷新后，如何及时到其他 resource server? 

* 配置服务中心？


* 能够本地使用 [UUA](http://docs.cloudfoundry.org/api/uaa/), [UUA@github](https://github.com/cloudfoundry/uaa)?
* Spring Social ?
* Spring Retry ?


# 多模块应用设计

* OAuth2 SSO server
    * 统一所有账号，创建本地账号，并绑定第三方账号
    * 提供本地账户相关的API
    * 提供第三方账号绑定、解绑相关的API

    * FIXME: 提供微信用户 AT 相关的API
    
* OAuth2 resource server (API server)
    * 组织、权限维护
    * 用户信息维护（用户详细信息，收货地址维护）
    * 仓库管理
    * 商品管理
    * 订单管理
    * 搜索服务
    * 消息服务()

* OAuth2 Client APP (SPA)
    单网页应用, FIXME angular 2 使用 component 技术发开，按需加载？

# OAuth2 SSO 服务器设计

* 注册本地账号
* 绑定第三方账户
* 使用第三账户登录、注册并绑定。
* 第三方账户登录
* 本地账户登录



## 自己的 OAuth2 SSO 如何使用 微信、支付宝等第三方账户？
 
### 代表自己的微信公众号

[代表自己的微信公众号](http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html)
模式下，AT 是使用 client 模式获取的， AT 有效期是2个小时，需要定时刷新，刷新后前一个AT会失效。
因此，只能一个服务器去定时刷新。
        
该类型与自己的 OAuth2 SSO 没有关系。可以是后台集群的无界面的服务，通过分布式锁单线程刷新 AT。

使用 OAuth2RestTemplate ?

1. 应使用固定的 DefaultOAuth2ClientContext 而无需是 session、request 作用的，
1. 使用 AccessTokenProviderChain， 仅仅包含一个 ClientCredentialsAccessTokenProvider 即可
1. 需扩展 AccessTokenProviderChain#refreshAccessToken() 使用分布式锁。

### 代表用户

[代表用户](http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html)
模式下，AT 是通过 auth code 获取的。
        
自己本地的 OAuth2 authorization 服务器是可以（也建议）使用 session 的，
可以使用 微信的 auth code 方式的授权码。

 


auth 服务器需要提供以下 API， 以方便 resource server 访问。但基本上不需要。

* `GET /wxUserAt/list`

    由 resource server / client app 调用，获取用户(此次/前次)微信登录时授权的 AT 列表。
    返回值可能有多个，scope 相同的，只保留最后一个。主要用于微信的 JS SDK。
    
    OAuth client 模式，仅对 resource server 开放。


    |param|desp|
    |-------|----|
    |mySsoAt            |本地 SSO 服务器登录的 AT，代表用户本次 SSO 登录的 session|
    |onlyCurWxLogin     | true, 仅限此次用户登录是微信登录, false - 返回可能是之前用户通过微信登录授权的的 AT |

    ```
    {
       wxAt: [{
            access_token    : "",
            scope           : [""],
            expires_in      : 7200,
            // refresh_token   : "",    // 为防止误用，不返回，应统一在一个地方进行刷新。 
            openid          : "",
            unionid         : ""
       },{},{}]
    }
    ```
    

* `POST /wxUserAt/refresh`

    由 resource server / client app 调用，刷新用户(此次/前次)微信登录时的授权 AT。
    
    OAuth client 模式，仅对 resource server 开放。

    |param|desp|
    |-------|----|
    |mySsoAt            |本地 SSO 服务器登录的 AT，代表用户本次 SSO 登录的 session|

 

```
# 获取微信用户基本信息

User Agent -> http Bearer 认证 (OAuth implicit 模式，代表用户)
    -> My Resource Server
        -> Weixin Auth Server
            // ClientCredentialsResourceDetails#setAuthenticationScheme(AuthenticationScheme.query)
            // 
            // 直接使用普通的 RestTemplate 即可:
            // 1. 如果没有 微信 AT, 则从 My Auth Server `/wxUserAt/list` 获取微信用户AT
            // 2. 调用微信 API，获取用户基本信息。
            // 3. error 处理
            // 3.1 从 My Auth Server `/wxUserAt/list` 获取微信用户AT，并使用最新的，且与刚才不一样的 AT 再次重试。
            // 3.2 从 My Auth Server `/wxUserAt/refresh` 主动刷新AT，并再次重试。
            // 3.3 返回异常。FIXME: 参考使用 Spring Retry ?
```

使用 OAuth2RestTemplate ?



AuthenticationScheme

JaxbOAuth2AccessTokenMessageConverter
DefaultClientAuthenticationHandler
BaseOAuth2ProtectedResourceDetails



