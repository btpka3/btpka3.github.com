# Eureka
1. 提供 Service Registry 和 Service Discovery 功能

# 参与角色

* Eureka Server：提供服务注册和发现
* Service Provider：服务提供方，将自身服务注册到Eureka，从而使服务消费方能够找到
* Service Consumer：服务消费方，从Eureka获取注册服务列表，从而能够消费服务。



```bash

EurekaController

# 浏览器访问 
#   http://localhost:8080/
#   http://localhost:8080/lastn
#   http://localhost:8080/service-registry/
#   http://localhost:8080/service-registry/instance-status

curl -v http://localhost:8080/info
curl -v http://localhost:8080/health

#curl -v http://localhost:8080/eureka/v2/apps



InetUtilsProperties                     - spring.cloud.inetutils
AutoServiceRegistrationProperties       - spring.cloud.service-registry.auto-registration
PropertySourceBootstrapProperties       - spring.cloud.config

RefreshEndpoint                         - endpoints.refresh
FeaturesEndpoint                        - endpoints.features
RefreshEndpointAutoConfiguration        - endpoints.pause
RestartEndpoint                         - endpoints.restart

LoadBalancerRetryProperties             - spring.cloud.loadbalancer.retry
CloudHypermediaAutoConfiguration        - spring.cloud.hypermedia
SimpleDiscoveryProperties               - spring.cloud.discovery.client.simple
KeyProperties                           - encrypt

EurekaDashboardProperties               - eureka.dashboard
EurekaInstanceConfigBean                - eureka.instance
InstanceRegistryProperties              - eureka.instance.registry
EurekaClientConfigBean                  - eureka.client
DefaultEurekaServerConfig               -
```


# 代码

```text
ApplicationResource#addInstance()
    PeerAwareInstanceRegistryImpl#register()
        PeerEurekaNode#register()

```
# 参考
 
* [spring-cloud-in-action](https://github.com/nobodyiam/spring-cloud-in-action)
* [深度剖析服务发现组件Netflix Eureka](http://geek.csdn.net/news/detail/130223)
* [Eureka REST operations](https://github.com/Netflix/eureka/wiki/Eureka-REST-operations)
* [spring-cloud-netflix@github](https://github.com/spring-cloud/spring-cloud-netflix/)


```text

SessionEdEurekaHttpClient
    clientFactory = RetryableEurekaHttpClient
        delegateFactory = RedirectingEurekaHttpClient
            delegateFactory = Jersey1TransportClientFactories
            
POST /eureka/apps/SC-EUREKA-SP 注册新应用

# 查询所有应用
curl -v http://localhost:8080/eureka/apps   

# 查询指定应用的所有实例
curl -v http://localhost:8080/eureka/apps/SC-EUREKA-SP

# 查询指定应用的指定实例
curl -v http://localhost:8080/eureka/apps/SC-EUREKA-SP/192.168.0.41:sc-eureka-sp:9090


# 查询实例信息 
curl -v http://localhost:8080/eureka/instances/192.168.0.41:sc-eureka-sp:9090


# 心跳
curl -v -X PUT http://localhost:8080/eureka/apps/SC-EUREKA-SP/192.168.0.41:sc-eureka-sp:9090

curl -v http://localhost:8080/eureka/vips/????
```