

# 目的

Feign + JaxRS-2.0 + Swagger + Jersey + WebScoket



# 问题

* Feign 的 JAXRSContract 针对的是 Jax-RS 1.1, 而非 2.0。 且仅仅支持
    * `@HttpMethod`: `@GET`， `@POST` 等
    * 方法级别上的注解：`@Path`, `@Produces`, `@Consumes`
    * 参数级别上的注解: `@PathParam`, `@QueryParam`, `@HeaderParam`, `@FormParam`
* 不支持：
    * `@BeanParam`
    * `@FormDataParam` 等
* FIXME:
    * 如何提交 `application/x-www-form-urlencoded` ?
    * 如何提交 `multipart/form-data` ?
    * 如何提交 `application/octet-stream` ?

# 结论

* 至少针对 Jax-RS 规范相关部分，还非常不成熟。
* 还是手动写，使用 RestTemplate + HttpMessageConverter 方便些。

