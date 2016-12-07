
## 运行

```
# 运行测试
cd first-spring-boot
./gradlew  :first-spring-boot-web:test

# 该命令会一直后台运行
gradle bootRun

# 使用 spring-boot-devtools 可以自动重启。可手动触发IDE生成新的class。
# 如果是命令行,可以
gradle build
```
参考： [spring boot gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html)


## 提供静态资源
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
|http://localhost:8080/a.css  |`classpath:/static/`              |
|http://localhost:8080/a.js   |`classpath:/public/`             |
 

## 自定义错误响应

需要自定义一个实现了 ErrorController 的 Bean。但是默认已经提供了 BasicErrorController。
已经能够满足大部分需求了。


## 源码分析

```
@SpringBootApplication
    -> @EnableAutoConfiguration
        -> EmbeddedServletContainerAutoConfiguration // 配置内嵌的 tomcat    
            #BeanPostProcessorsRegistrar        // 注册 bean: ErrorPageRegistrarBeanPostProcessor.class
            #EmbeddedUndertow                   // 注册 bean: EmbeddedServletContainerFactory.class (它实现了 ErrorPageRegistry)
        -> DispatcherServletAutoConfiguration
            # DispatcherServletConfiguration    // 注册 bean "dispatcherServlet"
                                                // 为 bean : MultipartResolver.clss 修正 bean 的名称
            # DispatcherServletRegistrationConfiguration
                #dispatcherServletRegistration  // 将 bean "dispatcherServlet" 注册到 Servlet 容器中
        -> JacksonAutoConfiguration             // 注册 bean: ObjectMapper.class, Jackson2ObjectMapperBuilder.class
        -> ErrorMvcAutoConfiguration
            #basicErrorController()             // 条件注册 bean: BasicErrorController.class
                                                // 如果找不到可用的 view， 则默认是 `error`
            #errorPageCustomizer()              // 注册 bean : ErrorPageRegistry.class
                ErrorPageRegistry#addErrorPages()
            #conventionErrorViewResolver()      // 注册 bean: DefaultErrorViewResolver.class 
                                                // 按照 http 状态码去找 view (`/error/5xx.<ext>`)
        -> WebMvcAutoConfiguration
            #hiddenHttpMethodFilter()           // 注册 bean : OrderedHiddenHttpMethodFilter.class
            #httpPutFormContentFilter()         // 注册 bean : OrderedHttpPutFormContentFilter.class
            WebMvcAutoConfigurationAdapter      // 实现了 WebMvcConfigurer/WebMvcConfigurerAdapter
                #configureContentNegotiation()  // 仅仅从 appliction.yml 中读取 `spring.mvc.media-types.*`
                #addResourceHandlers()          // 追加 "/webjars/**" 静态资源路径
            EnableWebMvcConfiguration           // 实现了 DelegatingWebMvcConfiguration, 追加多种 bean
            
            #defaultViewResolver()
            #beanNameViewResolver()
            #viewResolver()                     // 注册 bean : ContentNegotiatingViewResolver.class
            #localeResolver()   
        -> HttpMessageConvertersAutoConfiguration
        -> JacksonHttpMessageConvertersConfiguration
        -> JacksonAutoConfiguration
        -> WebClientAutoConfiguration           // 注册 bean : RestTemplateBuilder.class
        -> GroovyTemplateAutoConfiguration
        

DelegatingWebMvcConfiguration                   // 会提供许多bean，
    #setConfigurers(List<WebMvcConfigurer>)     // 自动依赖注入所有的 WebMvcConfigurer/WebMvcConfigurerAdapter
    #requestMappingHandlerMapping
    #mvcContentNegotiationManager()
        #getDefaultMediaTypes()                 // 默认只注册了 xml 和 json 两种
        #configureContentNegotiation(ContentNegotiationConfigurer) 
            WebMvcConfigurerComposite#configureAsyncSupport()   // 使用所有的 WebMvcConfigurer bean 对 ContentNegotiationConfigurer 进行配置
    #handlerExceptionResolver()                 // 注册 bean : HandlerExceptionResolver.class
        #addDefaultHandlerExceptionResolvers
            # 加入 ExceptionHandlerExceptionResolver, 处理 @ExceptionHandler
            # 加入 ResponseStatusExceptionResolver, 处理 @ResponseStatus
            # 加入 DefaultHandlerExceptionResolver, 处理特定的异常, 仅仅设定了状态码，而未设置 view
    #resourceHandlerMapping()
        #addResourceHandlers()                  // WebMvcAutoConfigurationAdapter#addResourceHandlers() 追加了 "/webjars/**"
        ResourceHandlerRegistry.getHandlerMapping()
        

@EnableWebMvc                                   // 如果启用该注解，则要完全手动配置 MVC 各个方面。
    DelegatingWebMvcConfiguration

DispatcherServlet#doService()
    #doDispatch()
        #checkMultipart()
        #getHandler()
        #getHandlerAdapter()
        HandlerExecutionChain#applyPreHandle() 
            HandlerInterceptor#preXxx()
        HandlerAdapter#handle()                 // 
            RequestMappingHandlerAdapter#handleInternel()
                #invokeHandlerMethod()
                    ServletInvocableHandlerMethod#invokeAndHandler()
        #applyDefaultViewName()
        HandlerExecutionChain#applyPostHandle()
            HandlerInterceptor#afterXxx()
        #processDispatchResult()
            #processHandlerException
                HandlerExceptionResolver.resolveException
            #render()

HandlerMethodReturnValueHandler
HandlerMethodArgumentResolver

RequestMappingInfo
PatternsRequestCondition
ErrorPageFilter         // 处理 DispatcherServlet 以外的错误，ErrorPage 类似于 web.xml 中的配置。

# 404
tomcat -> DispatcherServlet -> view = null  -> 返回到 tomcat -> ErrorPage("/error") -> ErrorController
# 5xx，同 404 的流程， 只不顾 DispatcherServlet 通过 HandlerExceptionResolver 设置了状态码，但是还是没有设置 view


HandlerMapping
    SimpleUrlHandlerMapping, 
    RequestMappingHandlerMapping, 
    BeanNameUrlHandlerMapping
    SimpleUrlHandlerMapping, 
    WebMvcConfigurationSupport$EmptyHandlerMapping
```

FIXME: 静态资源 Response 的 http 头： Content-Type 是如何自动设定的？