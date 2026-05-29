# first-grpc-proto1st

演示 **gRPC Proto-First** 方式：以 `.proto` 文件为 API 契约，自动生成 gRPC Stub，手写 Service 实现。

## 核心理念

1. **Proto 即契约**：`.proto` 文件定义 service + message，是前后端的唯一 truth
2. **服务层直接使用 proto 类型**：无需 Model 转换层，proto 生成的类就是领域模型
3. **Spring Boot 集成**：通过 `grpc-server-spring-boot-starter` 自动注册 gRPC 服务

## 项目结构

```
first-grpc-proto1st/
├── first-grpc-proto1st-proto/     # .proto → gRPC Java Stub + Protobuf Message
├── first-grpc-proto1st-service/   # 业务逻辑层（直接使用 proto 生成的类型）
└── first-grpc-proto1st-web/       # @GrpcService + Spring Boot 主类（gRPC :9090）
```

## 技术栈

| 项目 | 版本 |
|------|------|
| Spring Boot | 3.5.14 |
| Java | 21 |
| gRPC | 1.81.0 |
| Protobuf | 4.35.0 |
| grpc-spring-boot-starter | 3.1.0.RELEASE |

## 构建和运行

```shell
# 编译全部模块
mvn clean install -DskipTests

# 启动 gRPC 服务
mvn -pl first-grpc-proto1st-web spring-boot:run
```

## gRPC 测试

```shell
# 列出所有 gRPC 服务
grpcurl -plaintext localhost:9090 list

# 根据 ID 查询宠物
grpcurl -plaintext -d '{"pet_id": 42}' localhost:9090 petstore.PetService/GetPetById

# 根据状态查询宠物
grpcurl -plaintext -d '{"status": ["available"]}' localhost:9090 petstore.PetService/FindPetsByStatus
```

## 关联项目

OpenAPI spec-first HTTP 演示见 [`first-openapi1st`](../first-openapi1st/)。

## 与 first-openapi1st 的区别

| 维度 | first-openapi1st | first-grpc-proto1st |
|------|------------------|---------------------|
| 契约来源 | OpenAPI YAML（AI 生成） | .proto 文件（手写） |
| 传输协议 | HTTP/REST | gRPC (HTTP/2) |
| 代码生成 | openapi-generator → Java interface | protoc → gRPC Stub |
| 领域模型 | 生成的 POJO（getter/setter） | Protobuf Message（Builder 模式） |
| 服务端口 | 8080 | 9090 |
