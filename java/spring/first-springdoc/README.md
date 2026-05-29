


```shell

# 先安装
./mvnw install -DskipTests -q
# 再运行
./mvnw -pl first-springdoc-web spring-boot:run


```

启动后：
- http://localhost:8080/v3/api-docs — 完整 OpenAPI JSON
- http://localhost:8080/v3/api-docs/user-api — 用户分组
- http://localhost:8080/v3/api-docs/order-api — 订单分组