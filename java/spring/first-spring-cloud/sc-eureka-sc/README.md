
# service consume

服务消费者


# feign

```text
@EnableFeignClients
FeignClientsRegistrar   # 过滤出所有被 @FeignClient 标识的 Spring Bean
                        # 并通过 FeignClientFactoryBean 一一创建 spring bean

FeignAutoConfiguration
    @Bean FeignContext feignContext # 使用 spring 容器中所有的 FeignClientSpecification 进行初始化
                                    # 因此，如果要自定义配置，只需要自己多声明几个 @Bean FeignClientSpecification 
FeignClientFactoryBean#getObject()
    # 从 spring 容器中获取类型为 FeignContext 的bean，并初始化 Feign.Builder
```


## 测试


```bash
curl -v "https://kingsilk.net/qh/mall/api/common/sysConf?key=appendStaticResource"
curl -v "http://localhost:10000/qh/sysConf"
curl -v "http://localhost:10000/sc/sum"         # 已负载均衡：通过 DiscoveryClient 获取，@LoadBalanced RestTemplate
curl -v "http://localhost:10000/sc/sum2"        # 未负载均衡：noLbRestTemplate+明确的URL，固定节点上调用服务。
curl -v "http://localhost:10000/sc/sum3"        # 未负载均衡：手动指明要用那个 service provider 的 URL
curl -v "http://localhost:10000/sc/sum4"        # 已负载均衡：使用 feign 声明式的 client。
```

## 总结

- noLbRestTemplate 可以访问外部域名，无法访问服务的逻辑名
- @LoadBalanced 的 restTemplate 则不能访问外部域名，只能访问 逻辑名
