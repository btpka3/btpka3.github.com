
演示使用 spec first 基于已有的 openAPI json/yaml 生成 使用 spring mvc 的 java 后端的 http api

要求：创建maven项目，使用 https://petstore.swagger.io/v2/swagger.json 这个openAPI json文件。


## 项目结构

```
first-openapi1st/
├── first-openapi1st-api/      # OpenAPI → Java 接口 + Model（openapi-generator, spring 生成器）
├── first-openapi1st-proto/    # 手写 .proto → gRPC Java Stub（protobuf-maven-plugin）
├── first-openapi1st-service/  # PetService 业务逻辑（REST 和 gRPC 共享）
├── first-openapi1st-grpc/     # @GrpcService 实现 + Model↔Protobuf 转换
└── first-openapi1st-web/      # @RestController + Spring Boot 主类（双端口：REST :8080 + gRPC :9090）
```

## 测试

```shell
# 编译、启动
mvn clean install -DskipTests
mvn -pl first-openapi1st-web spring-boot:run


# REST 测试

# 1. 根据 ID 查询宠物
curl -s http://localhost:8080/pet/1 | jq

# 2. 根据状态查询宠物
curl -s "http://localhost:8080/pet/findByStatus?status=available" | jq

# 3. 查询不存在的宠物（验证默认返回）
curl -s http://localhost:8080/pet/999 | jq

# 4. 添加宠物（未实现，预期 500 UnsupportedOperationException）
curl -s -X POST http://localhost:8080/pet \
  -H "Content-Type: application/json" \
  -d '{"id":10,"name":"fido","status":"available","photoUrls":["url1"]}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

# 5. 删除宠物（未实现，预期 500 UnsupportedOperationException）
curl -s -X DELETE http://localhost:8080/pet/1 \
  -w "\nHTTP_STATUS: %{http_code}\n"


# gRPC 测试

# 列出所有 gRPC 服务
grpcurl -plaintext localhost:9090 list

# 根据 ID 查询宠物
grpcurl -plaintext -d '{"pet_id":42}' localhost:9090 petstore.PetService/GetPetById

# 根据状态查询宠物
grpcurl -plaintext -d '{"status":["available"]}' localhost:9090 petstore.PetService/FindPetsByStatus
```


## OpenAPI → .proto 自动生成工具调研

本项目的 .proto 文件（`first-openapi1st-proto/src/main/proto/petstore.proto`）是手写的，提交在 git 仓库中。
以下是对"能否从 OpenAPI spec 自动生成 .proto"这一问题的调研结论。

### 可选工具对比

| 工具 | 维护者 | 方向 | 现状 |
|------|--------|------|------|
| [openapi-generator `protobuf-schema`](https://openapi-generator.tech/docs/generators/protobuf-schema) | OpenAPI Generator 社区 | OpenAPI → .proto | Beta，有功能性 bug（见下文） |
| [gnostic](https://github.com/google/gnostic) + gnostic-grpc | Google | OpenAPI → protobuf 描述 | 核心库活跃，但 gnostic-grpc 子项目实验性质，更新很少 |
| [openapi2proto](https://github.com/NYTimes/openapi2proto) | NYTimes | OpenAPI → .proto | 基本停更（最后实质性提交 ~2020 年） |
| [protoc-gen-openapi](https://github.com/google/gnostic/tree/main/cmd/protoc-gen-openapi) | Google | .proto → OpenAPI | 活跃，但方向相反 |
| [gRPC-Gateway](https://github.com/grpc-ecosystem/grpc-gateway) | 社区 | .proto → REST proxy + OpenAPI | 活跃，但方向相反 |

### openapi-generator `protobuf-schema` 生成器实测

使用 openapi-generator 7.22.0 的 `protobuf-schema` 生成器对 Petstore swagger.json 进行了实测。

**配置：**
```json
{
  "startEnumsWithUnspecified": true,
  "numberedFieldNumberList": true
}
```

**可用的部分：**
- Service 定义完整（8 个 RPC 方法全部生成）
- Request message 结构合理（路径参数、查询参数正确映射为字段）
- 字段编号顺序正确（需开启 `numberedFieldNumberList`，否则字段编号是 hash 值）
- 枚举有 UNSPECIFIED 零值（需开启 `startEnumsWithUnspecified`）

**存在的问题：**

| 问题 | 具体表现 | 严重程度 |
|------|---------|---------|
| 列表响应丢失 `repeated` | `FindPetsByStatusResponse` 中 `Pet pet_200 = 1` 应为 `repeated Pet`，丢失列表语义 | 功能性 bug |
| 枚举字段未使用枚举类型 | `Pet.status` 定义了 `Status` 枚举但字段类型是 `string` | 类型安全缺失 |
| Response 用 oneof 包装错误码 | `oneof response { Pet pet_200 = 1; Empty empty_400 = 2; }` | 不符合 gRPC 惯例（应使用 `google.rpc.Status`） |
| 不支持 schema 组合 | `allOf`/`anyOf`/`oneOf`/`not` 均不支持 | 复杂 spec 无法使用 |

### 结论

**目前没有生产级的、持续维护的 OpenAPI → .proto 自动生成工具。**

根本原因是 OpenAPI（REST 语义：HTTP method + path + query/header params）和 gRPC（RPC 语义：service + method + request/response message）之间的映射不是自然的 1:1 关系。行业实际做法通常是**反方向**：以 .proto 为源，通过 gRPC-Gateway 或 protoc-gen-openapi 生成 OpenAPI spec。

**本项目采用的方案：** 手写 .proto，提交 git，OpenAPI spec 变更时手动同步并 diff 审查。这是当前最可靠的做法。
