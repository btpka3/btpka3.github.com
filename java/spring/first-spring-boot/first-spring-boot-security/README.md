
## 运行
```
# 该命令会一直后台运行
gradle bootRun

# 使用 spring-boot-devtools 可以自动重启。可手动触发IDE生成新的class。
# 如果是命令行,可以
gradle build
```
 

## URL设计

|path                   |test for           |
|-----------------------|-------------------|
|`/js/a.js`             |SecurityProperties#IGNORED_ORDER, 默认不进行安全设置|
|`/`                    |SecurityProperties#ACCESS_OVERRIDE_ORDER, 主页，可公共访问       |
|`/static/pub.html`     |静态资源，可以公共访问|
|`/static/sec.html`     |静态资源，登录后可访问|
|`/static/adm.html`     |静态资源，登录后可访问|
|`/controller/pub`      |动态画面，登录后可以访问|
|`/controller/sec`      |动态画面，登录后可以访问|
|`/controller/adm`      |动态画面，限admin可登陆后访问|
|`/controller/login`    |登录表单画面|
|`/controller/logouted` |退出后的默认画面|



```
@Secured 不支持 SpEL
SecConf


curl -v http://admin:admin@localhost:8080/static/sec.html
curl -v http://admin:admin@localhost:8080/controller/sec
```

# http basic 认证



## spring-security-config
* org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
    提供 bean : AuthenticationManagerBuilder
* org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

* WebSecurityConfiguration
    springSecurityFilterChain

## spring-boot-autoconfigure
* org.springframework.boot.autoconfigure.security.SecurityProperties
* org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration
    * 內建多个 WebSecurityConfigurerAdapter
* org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration
    * 当不存在 bean AuthenticationManager 时会配置一个。 默认打印 "Using default security password: ..."

## 容器配置

对传统的 WAR 部署，可以针对 Servlet 3 规范使用Spring 的该扩展接口：
org.springframework.boot.web.support.SpringBootServletInitializer

但是对于Spring Boot
org.springframework.boot.builder.SpringApplicationBuilder 启动内嵌tomcat，
并使用 ServletRegistrationBean、FilterRegistrationBean、ServletListenerRegistrationBean
等通过注册bean来配置tomcat。
可以通过 @ServletComponentScan 来启用servlet 3规范中定义的 @WebServlet、@WebFilter、@WebListener

spring security 的 "springSecurityFilterChain" 是在哪里注册配置的？

1. 在 WebSecurityConfiguration#springSecurityFilterChain() 创建bean
1. 在 SecurityFilterAutoConfiguration#securityFilterChainRegistration() 中通过声明类型为 DelegatingFilterProxyRegistrationBean 进行filter mapping配置。



## spring 扩展点


* BeanPostProcessor: (BPP)  

    能在实例化其他 bean 的前后进行一些自定义操作。
    能通过 bean 定义判断出该类型的bean，并优先初始化，在其他bean创建之前应用他们。
    因此，在使用 @Bean方法来返回一个 BeanPostProcessor 对象时，该方法的返回类型必须是 BeanPostProcessor 或其子类。
    对 BeanPostProcessor 设置为 lazy-init 是无效的
    注意: 被 BeanPostProcessor 直接引用的其他bean，将在applicationContext初始化的最初特殊启动阶段就被初始化。
    因为  AOP是用 BeanPostProcessor 实现的，所以，BeanPostProcessor 以及他们直接引用的bean都不会被proxy。
    但是，如果你的 BeanPostProcessor 中通过 @Autowired 或 @Resource 来依赖注入其他 bean，则这些bean可能会回退为自动封装的。
    常见子类：
    
    * AbstractAdvisingBeanPostProcessor
    * ConfigurationPropertiesBindingPostProcessor


* BeanFactoryPostProcessor: (BFPP) 

    类似 BeanPostProcessor。
    实现该接口的类名大多是 XxxConfigurer。在bean初始化前，修改 bean 定义。
    常见子类：
    
    * BeanDefinitionRegistryPostProcessor
    * ConfigurationClassPostProcessor
    
    ```
    @Conditional
        @ConditionalOnBean
        @ConditionalOnMissingBean
        @ConditionalOnClass
        @ConditionalOnMissingClass
        @ConditionalOnEnabledResourceChain
        @ConditionalOnExpression
        @ConditionalOnInitializedRestarter
        @ConditionalOnJava
        @ConditionalOnJndi
        @ConditionalOnWebApplication
        @ConditionalOnNotWebApplication
        @ConditionalOnProperty
        @ConditionalOnResource
        @ConditionalOnSingleCandidate
    ```
        
            
    
* FactoryBean: (FB)

    特定的工厂类，用来创建特定的bean。
    常见子类：
        ProxyFactoryBean —— 用于AOP，创建特定bean 的 Proxy bean。
        AbstractSingletonProxyFactoryBean

* BeanFactory: (BF)

    创建 BeanFactory 的流程
        
    1. BeanNameAware's setBeanName
    2. BeanClassLoaderAware's setBeanClassLoader
    3. BeanFactoryAware's setBeanFactory
    4. EnvironmentAware's setEnvironment
    5. EmbeddedValueResolverAware's setEmbeddedValueResolver
    6. ResourceLoaderAware's setResourceLoader (only applicable when running in an application context)
    7. ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context)
    8. MessageSourceAware's setMessageSource (only applicable when running in an application context)
    9. ApplicationContextAware's setApplicationContext (only applicable when running in an application context)
    10. ServletContextAware's setServletContext (only applicable when running in a web application context)
    11. postProcessBeforeInitialization methods of BeanPostProcessors
    12. InitializingBean's afterPropertiesSet
    13. a custom init-method definition
    14. postProcessAfterInitialization methods of BeanPostProcessors

    关闭 BeanFactory 的流程
    
    1. postProcessBeforeDestruction methods of DestructionAwareBeanPostProcessors
    2. DisposableBean's destroy
    3. a custom destroy-method definition

## AOP

# 类关系

```

AopProxy
    CglibAopProxy
    JdkDynamicAopProxy

ProxyConfig
    AbstractSingletonProxyFactoryBean       // FB,
        CacheProxyFactoryBean
        TransactionProxyFactoryBean
    AdvisedSupport
        ProxyCreatorSupport
            AspectJProxyFactory
            ProxyFactory
            ProxyFactoryBean                // FB
    ProxyProcessorSupport
        AbstractAdvisingBeanPostProcessor                   // BPP
            AbstractBeanFactoryAwareAdvisingPostProcessor
                AsyncAnnotationBeanPostProcessor
                MethodValidationPostProcessor
                PersistenceExceptionTranslationPostProcessor
        AbstractAutoProxyCreator                            // BPP
            AbstractAdvisorAutoProxyCreator
                AspectJAwareAdvisorAutoProxyCreator
                DefaultAdvisorAutoProxyCreator
                InfrastructureAdvisorAutoProxyCreator
            BeanNameAutoProxyCreator
    ScopedProxyFactoryBean                                  // FB, 处理 request/session/globalSession/application 作用域
                                                            // 创建 ScopedObject
Scope
    AbstractRequestAttributesScope
        RequestScope            // RequestContextFilter : LocaleContextHolder/RequestContextHolder
        SessionScope            // RequestContextListener
    PortletContextScope
    SimpleThreadScope
    ServletContextScope
    SimpleTransactionScope 
    SimpSessionScope

# Scope
ScopedProxyFactoryBean#getObject()
    -> ScopedObject 创建代理引用
    DefaultScopedObject#getTargetObject()
        -> ConfigurableBeanFactory#getBean()
            -> AbscractBeanFactory#doGetBean()
                -> Scope.get(String,ObjectFactory) 来获取 特定 scope 的 bean.

ConfigurableBeanFactory#registerScope(String,Scope)
    Scope#get(String,ObjectFactory)
        RequestContextHolder.currentRequestAttributes()
        RequestAttributes#getAttribute(String,int);

@EnableLoadTimeWeaving
    -> LoadTimeWeavingConfiguration             // 创建 bean "loadTimeWeaver"

@AopAutoConfiguration
    @EnableAspectJAutoProxy
        -> AspectJAutoProxyRegistrar#registerBeanDefinitions()
            AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);  
            // 注册 BPP bean : AnnotationAwareAspectJAutoProxyCreator.class

AnnotationAwareAspectJAutoProxyCreator
    #postProcessBeforeInstantiation()
        #getAdvicesAndAdvisorsForBean()     // 从 ApplicationContext 中获取所有的、适用于当前 bean 定义的 Advisor
            #findEligibleAdvisors()
                #findCandidateAdvisors()
        #createProxy()                      // 使用上述所有的 Advisor 创建 Proxy
```

 
 
## Java config


```
@Component
    @Repository
    @Configuration
    @Service
    @Controller
        @RestController

SpringApplication
    #initialize()
    #run()
        #getRunListeners()              // 获取并启动 SpringApplicationRunListener
        #prepareEnvironment()           // 创建 StandardServletEnvironment
        #printBanner()                  // 打印 banner
        #createApplicationContext()     // 创建 AnnotationConfigEmbeddedWebApplicationContext
        #prepareContext()
            #postProcessApplicationContext()    //
            #applyInitializers()                // 调用 ApplicationContextInitializer
            #load()                             //
        #refreshContext()
            #refresh()
                ApplicationContext#refresh() 
        #afterRefresh()
            #callRunners()                      // 运行 ApplicationRunner, CommandLineRunner


AbstractApplicationContext
    #refresh()
        #prepareRefresh()
        #prepareBeanFactory()       
            # 条件注册 bean: BPP : LoadTimeWeaverAwareProcessor
            # 注册 bean : "environment"、"systemProperties"、"systemEnvironment"
        #postProcessBeanFactory()
            ClassPathBeanDefinitionScanner#scan()   // 从 classpath 下面按照条件读取类定义，交给 AnnotatedBeanDefinitionReader 生成 BeanDefine。
        #invokeBeanFactoryPostProcessors()          // 调用 BFPP
        #registerBeanPostProcessors()               // 查找 BPP，排序并注册 
        #initMessageSource()
        #initApplicationEventMulticaster()
        #onRefresh()
        #registerListeners()
        #finishBeanFactoryInitialization()
            # 从 applicationContext 中获取 "conversionService" bean 并使用
            # 优先获取 bean : LoadTimeWeaverAware.class
            # 冻结配置
            DefaultListableBeanFactory#preInstantiateSingletons()      // 预初始化单例 bean
                # 初始化所有非 lazy 的单例 bean
        #finishRefresh()
    #getBean()
        AbstractBeanFactory#getBean()
            #doGetBean()
            AbstractAutowireCapableBeanFactory#createBean()
                #doCreateBean()
                    #initializeBean()
                        #applyBeanPostProcessorsBeforeInitialization()
                            BeanPostProcessor#postProcessBeforeInitialization(result, beanName);  // 调用 BPP
    



ClassPathBeanDefinitionScanner
    #构造函数
        super#registerDefaultFilters()              // 扫描 @Component, @ManagedBean, @Named
    #scan()
        AnnotationConfigUtils.registerAnnotationConfigProcessors()  // 注册各种 BeanFactoryPostProcessor
    #doScan()                                           // 扫描并创建 BeanDefinitionHolder
        super#findCandidateComponents()                 // 过滤出被 @Component 标注的、且能被直接构建的类
        AnnotationScopeMetadataResolver#resolveScopeMetadata()      // @Scope
        AnnotationBeanNameGenerator#generateBeanName()              // [@Component,@Repository,@Service,@Controller]*#value()
        AnnotationConfigUtils.processCommonDefinitionAnnotations    // @Lazy, @Primary, @DependsOn, @Role, @Description, 

AnnotationConfigUtils.registerAnnotationConfigProcessors()  // 注册各种 BeanFactoryPostProcessor
    ConfigurationClassPostProcessor         // 处理 @Configuration
        #postProcessBeanDefinitionRegistry
            ImportAwareBeanPostProcessor            // BPP: 处理 ImportAware
            EnhancedConfigurationBeanPostProcessor  // BPP: 处理
            #processConfigBeanDefinitions
                ConfigurationClassParser#parse()    
    AutowiredAnnotationBeanPostProcessor        // @Autowired, @Value
    RequiredAnnotationBeanPostProcessor         // @Required
    CommonAnnotationBeanPostProcessor           // JSR-250 注解
    PersistenceAnnotationBeanPostProcessor
    EventListenerMethodProcessor
    DefaultEventListenerFactory




    ConfigurationClassBeanDefinitionReader

ConfigurationClassParser#parse()
    #processDeferredImportSelectors()   // 查找所有 @Conditional 标记的注解的信息
    EnableAutoConfigurationImportSelector#selectImports()   // 从 @SpringBootApplication 查找起
        SpringFactoriesLoader.loadFactoryNames()    // 从 spring-boot-autoconfigure.jar!META-INF/spring.factories 中
                                                    // 查找 EnableAutoConfiguration 要加载的类。
    #processConfigurationClass()
        #doProcessConfigurationClass()  // 级联从 @Import 中读取要 import 的类
            
    
AnnotationConfigApplicationContext
    AnnotatedBeanDefinitionReader#registerBean()        // @Primary, @Lazy
        ConditionEvaluator#shouldSkip()                 // @Conditional @Bean

ConfigFileApplicationListener
    加载 application.yml, application.properties
```

## @@SpringBootApplication

```
EnableAutoConfigurationImportSelector
```

## @EnableAutoConfiguration


@EnableAutoConfiguration
主要扫描@Configuration + 
    @ConditionalOnBean
    @ConditionalOnMissingBean
    @ConditionalOnClass
    @AutoConfigureAfter
配合的bean。

```
@EnableAutoConfiguration
SecurityAutoConfiguration
    -> SpringBootWebSecurityConfiguration
        #ignoredPathsWebSecurityConfigurerAdapter()     // 创建 WebSecurityConfigurer，对特定路径不进行安全设置。
        .ApplicationNoWebSecurityConfigurerAdapter()    // 创建 WebSecurityConfigurer，当没有 security.basic.enabled 时创建，不对任何路径进行basic认证。
        .ApplicationWebSecurityConfigurerAdapter()      // 创建 WebSecurityConfigurer，当 security.basic.enabled 时创建，
                                                        // 根据 application.yml 中 "security.*" 进行相应的设置。
        -> EnableWebSecurity
    -> BootGlobalAuthenticationConfiguration
        #bootGlobalAuthenticationConfigurationAdapter()     // 优先初始化 @EnableAutoConfiguration 的 bean
    -> AuthenticationManagerConfiguration                   // 如果不存在 AuthenticationManager，则创建一个。

@EnableGlobalMethodSecurity
    -> GlobalMethodSecuritySelector
        -> MethodSecurityMetadataSourceAdvisorRegistrar
            #registerBeanDefinitions()              // 在 spring 容器中注册 bean : MethodSecurityMetadataSourceAdvisor.class
                                                    // 并引用 bean "methodSecurityInterceptor"
        -> GlobalMethodSecurityConfiguration
            #methodSecurityInterceptor()            // 在 Spring 容器中注册 bean : "methodSecurityInterceptor"
                                                    // MethodSecurityMetadataSourceAdvisor
    -> ObjectPostProcessorConfiguration
    -> @EnableGlobalAuthentication 
        -> AuthenticationConfiguration

@EnableWebSecurity
    -> WebSecurityConfiguration
        #autowiredWebSecurityConfigurersIgnoreParents() // 从当前spring 容器中获取 webSecurityConfigurer/WebSecurityConfigurerAdapter
        #setFilterChainProxySecurityConfigurer()        // 创建 webSecurity, 并应用上述所有 webSecurityConfigurer
        #springSecurityFilterChain()                    // 通过 webSecurity 注册 filter bean "springSecurityFilterChain"
            :webSecurity.build()
    -> ObjectPostProcessorConfiguration                 // 创建 ObjectPostProcessor Bean
    -> SpringWebMvcImportSelector
        -> WebMvcSecurityConfiguration


AuthenticationConfiguration
    #authenticationManagerBuilder()                 // 构建 ProviderManager， 将认证交给一组的 AuthenticationManager
    #enableGlobalAuthenticationAutowiredConfigurer()// 优先初始化 @EnableGlobalAuthentication 的 bean
    #initializeUserDetailsBeanManagerConfigurer()   // 为 AuthenticationManagerBuilder 初始化 DaoAuthenticationProvider；
                                                    // 所需 UserDetailsService、PasswordEncoder 从applicationContext中获取。
    #initializeAuthenticationProviderBeanManagerConfigurer()    // 从 Spring 容器中获取 AuthenticationProvider bean， 用以配置 AuthenticationManagerBuilder。
    #getAuthenticationManager()                     // 调用 AuthenticationManagerBuilder 进行构建
    
AuthenticationManagerConfiguration                  // 如果自己手动 注册了 authenticationManager bean，则会忽略这里的配置。
    #authenticationManager()                        // 注册 spring bean : authenticationManager
        AuthenticationConfiguration#getAuthenticationManager()  // 对 authenticationManagerBuilder 第二次构建（直接返回第一次构建好的对象） 
    #springBootAuthenticationConfigurerAdapter      // 如果不存在 AuthenticationManager，则为 AuthenticationManagerBuilder 默认配置一个。

WebSecurity
    #build()
        #doBuild()
            #beforeInit()
            #init()                 // 调用所有 SecurityConfigurer#init()
            #beforeConfigure()
            #configure()            // 调用所有 SecurityConfigurer#configure()
            #performBuild()
                # 为每个ignored路径配置一个 DefaultSecurityFilterChain
                # 用每个 SecurityBuilder/HttpSecurity 构建一个 SecurityFilterChain
                # 将上述所有的 securityFilterChains 封装成一个 FilterChainProxy

WebSecurityConfigurerAdapter        // 在实现是请设置 @Order，参考 SecurityProperties.ACCESS_OVERRIDE_ORDER，第一个 安全匹配规则起作用，
                                    // 参考 [multiple-httpsecurity](http://docs.spring.io/spring-security/site/docs/4.2.0.RELEASE/reference/htmlsingle/#multiple-httpsecurity)
    #init()
        #getHttp()
            #authenticationManager()
                AuthenticationConfiguration#getAuthenticationManager()      // 对 authenticationManagerBuilder 第一次构建
            # 创建 httpSecurity —— 即 WebSecurityConfigurerAdapter 与 httpSecurity 是一一对应关系
            # 对 httpSecurity 进行默认配置。
            #config(HttpSecurity)                   // 如果没有子类重写，再次补充默认配置（所有请求都需登录，配置表单登录和 http basic 认证)
                HttpSecurity#authorizeRequests()    // 启用 ExpressionUrlAuthorizationConfigurer 并配置 FilterSecurityInterceptor
                                                    // 使 WebExpressionVoter 从而可以对 URL 授权时使用 Web Expression 的方法。
                                                    // 如果需要自定义 accessDecisionManager,
                                                    //      1. 扩展 GlobalMethodSecurityConfiguration 并重写 accessDecisionManager
                                                    //      2. HttpSecurity#authorizeRequests()#accessDecisionManager(...)
                                                    // 参考： http://stackoverflow.com/questions/23734262/spring-security-accessdecisionmanager-rolevoter-acl-voter
        # 将 HttpSecurity 加入 WebSecurity
        # 设置 FilterSecurityInterceptor
 
@Order
    同 Ordered 接口。数值越小，优先级越高。
    没有使用 @Order 注解，且 没有实现 Ordered 接口，则默认是最低优先级（最后处理，整数的最大值）
```

##AbstractSecurityInterceptor

主要子类有：
    ChannelSecurityInterceptor
    FilterSecurityInterceptor
    MethodSecurityInterceptor

1. 从 SecurityContextHolder 中获取 Authentication
1. 查询 SecurityMetadataSource ，确认请求是公共的，还是受保护的资源
1. 如果是受保护的资源：
    1. 如果用户未登录、或者 alwaysReauthenticate 为 true，则 使用 AuthenticationManager 进行认证
        认证成功后，替换 SecurityContextHolder 中的 Authentication 为认证后的
    1. 使用 AccessDecisionManager 对认请求行授权判断
    1. 使用 RunAsManager 进行处理
    1. 调用实现类，执行具体业务代码
    1. 实现类调用  afterInvocation
    1. 如果 RunAsManager 替换了 Authentication 对象，则在调用 AuthenticationManager 之后，
        将原来的 Authentication 放到 SecurityContextHolder
    1. 调用 AfterInvocationManager
1. 如果是公开的资源：——即 ConfigAttributes 中没有值，当 afterInvocation 调用之后， AbstractSecurityInterceptor 将不会有任何动作
1. 实现类将目标对象的返回值返还给原有调用者，或者抛出异常。

    
    
FIXME: authentioncationManager 是何时注册到 applicationContenxt 中的？
 
 
## 如何自定义配置  WebSecurity、HttpSecurity、

1. 实现 WebSecurityConfigurerAdapter 并注册为 bean

## 如何自定义配置 AuthenticationManagerBuilder

1. 直接 Autowired AuthenticationManagerBuilder 并进行配置，

    ```groovy
    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")
    
                .and()
                .withUser("user")
                .password("user")
                .authorities("UUU")
                .roles("USER");
                // 注意：这里不可以调用 build 方法，将其返回值作为 @Bean 返回
    }
    ```
1. 实现 GlobalAuthenticationConfigurerAdapter 并注册为 bean
1. 在 @EnableAutoConfiguration 中注册 bean
1. 在 @EnableGlobalAuthentication 中注册 bean 



                            
## 类关系

```
SecurityBuilder
    HttpSecurityBuilder<H>
    ProviderManagerBuilder<B>
    AbstractSecurityBuilder<O>
        AbstractConfiguredSecurityBuilder 
            WebSecurity                     // 使用 WebSecurity 配置 Filter，可忽略特定path的安全性，配置SpEL, 
            HttpSecurity                    // 使用 HttpSecurity 配置 DefaultSecurityFilterChain,
            AuthenticationManagerBuilder    // 使用 AuthenticationManagerBuilder> 配置 AuthenticationManager(ProviderManager)

SecurityConfigurer                          // 初始化并配置 SecurityBuilder
    GlobalAuthenticationConfigurerAdapter
    WebSecurityConfigurer                   // 该类中 configure()  在 AbstractConfiguredSecurityBuilder 的 configure 阶段调用
        WebSecurityConfigurerAdapter        // : #getHttp() , 创建 httpSecurity
                                            // 新建内部 AuthenticationManagerBuilder
            SpringBootWebSecurityConfiguration.ApplicationNoWebSecurityConfigurerAdapter
            SpringBootWebSecurityConfiguration.ApplicationWebSecurityConfigurerAdapter
            ResourceServerConfiguration
        SpringBootWebSecurityConfiguration.IgnoredPathsWebSecurityConfigurerAdapter    
    SecurityConfigurerAdapter               // 该类中 configure()  在 AbstractConfiguredSecurityBuilder 的 configure 阶段调用
        AbstractHttpConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, B>
            AbstractAuthenticationFilterConfigurer,
                FormLoginConfigurer, 
                OpenIDLoginConfigurer
            AnonymousConfigurer, 
            ChannelSecurityConfigurer, 
            CorsConfigurer, 
            CsrfConfigurer, 
            DefaultLoginPageConfigurer, 
            ExceptionHandlingConfigurer, 
            ExpressionUrlAuthorizationConfigurer, 
            HeadersConfigurer,
            HttpBasicConfigurer, 
            JeeConfigurer, 
            LogoutConfigurer, 
            PortMapperConfigurer, 
            RememberMeConfigurer, 
            RequestCacheConfigurer, 
            SecurityContextConfigurer, 
            ServletApiConfigurer, 
            SessionManagementConfigurer, 
            UrlAuthorizationConfigurer, 
            X509Configurer
        LdapAuthenticationProviderConfigurer extends SecurityConfigurerAdapter<AuthenticationManager, B>
        UserDetailsAwareConfigurer
            DaoAuthenticationConfigurer, 
            UserDetailsServiceConfigurer
                UserDetailsManagerConfigurer
                    InMemoryUserDetailsManagerConfigurer
                    JdbcUserDetailsManagerConfigurer
        ResourceServerSecurityConfigurer
                    
```
             
## Web 请求顺序

```
DelegatingFilterProxy
    -> FilterChainProxy
        -> DefaultSecurityFilterChain (多个)
```

## filter-stack
Spring security 的 filter-stack 在[这里](http://docs.spring.io/spring-security/site/docs/4.2.0.RELEASE/reference/htmlsingle/#filter-stack)。
主要是通过 `FilterChainProxy`、`SecurityFilterChain` 来代理各种 filter 的。
排序请参考 `FilterComparator`

|Order  |SecurityFilters enum                   |Filter Class                               |Namespace Element or Attribute         |HttpSecurity#xxx()     |
|-------|---------------------------------------|-------------------------------------------|---------------------------------------|-----------------------|
|  -MIN |                                       |OrderedCharacterEncodingFilter             |                                       |                       |
|-10000 |                                       |OrderedHiddenHttpMethodFilter              |                                       |                       |
| -9900 |                                       |OrderedHttpPutFormContentFilter            |                                       |                       |
|  -110 |                                       |OAuth2ClientContextFilter  ???             |                                       |                       |
|  -105 |                                       |OrderedRequestContextFilter                |                                       |                       |
|  -100 |                                       |DelegatingFilterProxy                      |                                       |                       |
|       |                                       |                                           |                                       |                       |
|       |                                       |                                           |                                       |                       |
|     0 |    0 : FIRST                          |                                           |                                       |                       |
|   100 |  100 : CHANNEL_FILTER                 |ChannelProcessingFilter                    |http/intercept-url@requires-channel    |#requiresChannel()     |
|   200 |  300 : CONCURRENT_SESSION_FILTER      |ConcurrentSessionFilter                    |session-management/concurrency-control |#sessionManagement()   |
|   300 |  400 : WEB_ASYNC_MANAGER_FILTER       |WebAsyncManagerIntegrationFilter           |                                       |                       |
|   400 |  200 : SECURITY_CONTEXT_FILTER        |SecurityContextPersistenceFilter           |http                                   |#securityContext()     |
|   500 |  500 : HEADERS_FILTER                 |HeaderWriterFilter                         |http/headers                           |#headers()             |
|   600 |  600 : CORS_FILTER                    |CorsFilter                                 |                                       |                       |
|   700 |  700 : CSRF_FILTER                    |CsrfFilter                                 |http/csrf                              |#csrf()                |
|   800 |  800 : LOGOUT_FILTER                  |LogoutFilter                               |http/logout                            |#logout()              |
|   900 |  900 : X509_FILTER                    |X509AuthenticationFilter                   |http/x509                              |#x509()                |
|       |                                       |OAuth2AuthenticationProcessingFilter       |                                       |                       |
|  1000 | 1000 : PRE_AUTH_FILTER                |AbstractPreAuthenticatedProcessingFilter   |N/A                                    |#addFilter...()        |
|  1100 | 1100 : CAS_FILTER                     |CasAuthenticationFilter                    |N/A                                    |#addFilter...()        |
|  1200 | 1200 : FORM_LOGIN_FILTER              |UsernamePasswordAuthenticationFilter       |http/form-login                        |#formLogin()           |
|  1300 |  300 : CONCURRENT_SESSION_FILTER      |ConcurrentSessionFilter                    |session-management/concurrency-control |#sessionManagement()   |
|  1400 | 1300 : OPENID_FILTER                  |OpenIDAuthenticationFilter                 |                                       |#openidLogin()         |
|  1500 | 1400 : LOGIN_PAGE_FILTER              |DefaultLoginPageGeneratingFilter           |                                       |                       |
|  1600 |  300 : CONCURRENT_SESSION_FILTER      |ConcurrentSessionFilter                    |session-management/concurrency-control |#sessionManagement()   |
|  1700 | 1500 : DIGEST_AUTH_FILTER             |DigestAuthenticationFilter                 |                                        |                       |
|       |                                       |ClientCredentialsTokenEndpointFilter       |                                       |                       |
|  1800 | 1600 : BASIC_AUTH_FILTER              |BasicAuthenticationFilter                  |http/http-basic                        |#httpBasic()           |
|  1900 | 1700 : REQUEST_CACHE_FILTER           |RequestCacheAwareFilter                    |                                       |#requestCache()        |
|  2000 | 1800 : SERVLET_API_SUPPORT_FILTER     |SecurityContextHolderAwareRequestFilter    |http/@servlet-api-provision            |#servletApi()          |
|  2100 | 1900 : JAAS_API_SUPPORT_FILTER        |JaasApiIntegrationFilter                   |http/@jaas-api-provision               |#addFilter...()        |
|  2200 | 2000 : REMEMBER_ME_FILTER             |RememberMeAuthenticationFilter             |http/remember-me                       |#rememberMe()          |
|  2300 | 2100 : ANONYMOUS_FILTER               |AnonymousAuthenticationFilter              |http/anonymous                         |#anonymous()           |
|  2400 | 2200 : SESSION_MANAGEMENT_FILTER      |SessionManagementFilter                    |session-management                     |#sessionManagement()   |
|  2500 | 2300 : EXCEPTION_TRANSLATION_FILTER   |ExceptionTranslationFilter                 |http                                   |#exceptionHandling()   |
|  2600 | 2400 : FILTER_SECURITY_INTERCEPTOR    |FilterSecurityInterceptor                  |http                                   |#                      |
|  2700 | 2500 : SWITCH_USER_FILTER             |SwitchUserFilter                           |N/A                                    |#addFilter...()        |
|  2800 | 2600 : LAST                           |                                           |                                       |                       |
|       |                                       |                                           |                                       |                       |



