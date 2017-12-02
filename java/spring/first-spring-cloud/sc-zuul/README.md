

# 参考
- org.springframework.cloud.netflix.zuul.ZuulServerAutoConfiguration 
- org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration
- org.springframework.cloud.netflix.zuul.filters.ZuulProperties


# 运行

```bash
./gradlew :sc-eureka-server:bootRun
# 浏览器访问 http://localhost:8080/ 进行确认 

./gradlew :sc-eureka-sp:bootRun
# 浏览器访问 http://localhost:9090/ 进行确认

./gradlew :sc-zuul:bootRun
# 浏览器访问 http://localhost:10030/ 进行确认

# 验证，可以看到从 sc-eureka-sp 返回的内容
curl -v http://localhost:10030/api/sc-eureka-sp/


```