 
 
# 参考

1. [swagger-samples/ava-spring-boot](https://github.com/swagger-api/swagger-samples/tree/master/java/java-spring-boot)
1. [Swagger-Core Annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)

# 演示

``` 
gradle bootRun

# 访问 
http://localhost:8080/v2/api-docs
http://localhost:8080/swagger-ui.html
```

# FIXME

1. metaClass 属性？
    OptimizedModelPropertiesProvider#onApplicationEvent 总是启用最后一个 ObjectMapperConfigured 中的 objectMapper
    OptimizedModelPropertiesProvider#beanDescription 
    使用的还是 objectMapper.getDeserializationConfig()，
    而不是  objectMapper.getSerializationConfig()
    
    
    POJOPropertyBuilder#getPrimaryMember() 会根据是否用于序列化来调用不同方法。
    其中 getMutator() 有返回值，而 getAccessor() 没有返回值。
    
    注意：`docket.ignoredParameterTypes(MetaClass)` 解决该问题

# 总结

1. springfox 是针对 spring web，而非 JAX-RS
1. 统一使用 @RestController
1. 不要使用 cookie
1. @SwaggerDefinition 不起作用
1. 'application/x-www-form-urlencoded' 需要 @ModelAttribute, 
    或者 POST+ consume= MediaType.APPLICATION_FORM_URLENCODED_VALUE。
    FIXME: 目前只能通过参数一个一个定义，不能使用command bean。
1. "multipart/form-data" 只支持/适用于文件上传，不适用于 部分解析成对象，比如： 
`public ResponseEntity create(@RequestPart User user)`
1. tag 
    1. 先使用 docket#tags 注册，
    2. 再在 @Api/@ApiOperation 中引用该tag
