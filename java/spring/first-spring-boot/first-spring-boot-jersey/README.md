 
 
## 参考

* [swagger-samples/ava-spring-boot](https://github.com/swagger-api/swagger-samples/tree/master/java/java-spring-boot)
* [Swagger-Core Annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)
* [Swagger Core JAX RS Project Setup 1.5.X](https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-JAX-RS-Project-Setup-1.5.X)
* [Feign JAXRS](https://github.com/OpenFeign/feign/tree/master/jaxrs)
* [jaxrs-api vs. jsr311-api vs. javax.ws.rs-api vs. jersey-core](https://stackoverflow.com/questions/32106428/jaxrs-api-vs-jsr311-api-vs-javax-ws-rs-api-vs-jersey-core)
* [What's New in JAX-RS 2.0?](https://www.infoq.com/news/2013/06/Whats-New-in-JAX-RS-2.0)

## 演示

``` 
gradle bootRun

# 访问 
http://localhost:8080/webjars/swagger-ui/2.2.10/index.html
http://localhost:8080/api/rest
http://localhost:8080/api/swagger.json
http://localhost:8080/api/swagger.yaml
```



## JAX-RS 2.0 有什么新功能？


* Client API
    JAX-RS 1.0 是针对服务器 API 进行设计的。
    而 JAX-RS 2.0 为 client 端增加了 "builder" 工具

    ```java
    Client client = ClientFactory.newClient();
    String shares=client.target("http://.../portfolio/123")
        .pathParam("identifier", "IBM")
        .queryParameter("identifierType", "ticker")
        .request("text/plain).get(String.class");
    ```

* Async
* HATEOAS (Hypermedia)
* Annotations
* Validation
* Filters and Handlers
* Content negotiation



|       |JAX-RS 1.x             |JAX-RS 2.x |
|-------|-----------------------|-----------|
|JSR    | 311                   |339        |
|GAV    | javax.ws.rs:jsr311-api|javax.ws.rs:javax.ws.rs-api    |
|Jersey | 1.x                   | 2.x |


## 总结

应该使用哪些注解？

|annotation type|Annotations        |JAX-RS 2.x |feign  |swagger    |my suggestion|
|---------------|-------------------|-----------|-------|-----------|-------------|
|Type/Class     |@Path              |           |Yes    |           |             |
|Method         |@HttpMethod        |           |Yes    |           |             |
|Method         |@Produces          |           |Yes    |           |             |
|Method         |@Consumes          |           |Yes    |           |             |
|Parameter      |@PathParam         |           |Yes    |           |             |
|Parameter      |@QueryParam        |           |Yes    |           |             |
|Parameter      |@HeaderParam       |           |Yes    |           |             |
|Parameter      |@FormParam         |           |Yes    |           |             |
|Parameter      |@CookieParam       |           |       |           |             |
|Parameter      |@MatrixParam       |           |       |           |             |
|               |@BeanParam         |Yes        |       |           |             |
|               |@ConstrainedTo     |Yes        |       |           |             |
|               |@NameBinding       |Yes        |       |           |             |
|               |@PreMatching       |Yes        |       |           |             |
|               |@Suspended         |Yes        |       |           |             |
|               |@Context           |           |       |           |             |
|               |@Provider          |           |       |           |             |
|               |@ApplicationPath   |           |       |           |             |
|               |@DefaultValue      |           |       |           |             |
|               |@Encoded           |           |       |           |             |





 


