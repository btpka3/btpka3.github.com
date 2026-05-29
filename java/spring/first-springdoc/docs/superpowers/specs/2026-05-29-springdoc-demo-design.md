# SpringDoc OpenAPI Demo 设计文档

## 概述

在 `first-springdoc` 目录初始化一个 Spring Boot 3 示例项目，演示 springdoc-openapi 的全面用法。仅生成 OpenAPI JSON 文档，不包含 Swagger UI。

## 技术选型

- **构建**：Gradle 9.2 (Groovy DSL)，Gradle Wrapper
- **框架**：Spring Boot 3.4.4，Java 21
- **Web 层**：WebMVC（Servlet）
- **OpenAPI**：`springdoc-openapi-starter-webmvc-api`（不含 UI）
- **辅助**：Lombok
- **Maven 仓库**：阿里云镜像 + Maven Central

## 方案：注解 + Java Config 混合

Controller/DTO 上用注解描述接口级别的元数据（`@Operation`、`@Schema`、`@Tag` 等），全局信息（API Info、Security Scheme、分组）用 Java Config `@Bean` 配置。

## 项目结构

```
com.github.btpka3.first.springdoc
├── FirstSpringdocApplication.java
├── config/
│   └── OpenApiConfig.java          # @Bean OpenAPI + GroupedOpenApi
├── controller/
│   ├── UserController.java         # /api/users CRUD
│   └── OrderController.java        # /api/orders CRUD
├── model/
│   ├── User.java                   # @Schema 注解的 DTO
│   ├── Order.java
│   ├── OrderStatus.java            # 枚举
│   └── ApiError.java               # 统一错误响应
```

## OpenAPI 全局配置（Java Config）

`OpenApiConfig.java` 通过 `@Bean` 配置：

- **API 信息**：title、version、description、contact、license
- **安全方案**：Bearer JWT（`SecurityScheme.Type.HTTP`，scheme=bearer）
- **API 分组**：
  - `user-api` — 匹配 `/api/users/**`
  - `order-api` — 匹配 `/api/orders/**`

访问路径：
- `/v3/api-docs` — 默认全量文档
- `/v3/api-docs/user-api` — 用户分组
- `/v3/api-docs/order-api` — 订单分组

## Controller 设计

### UserController (`/api/users`，`@Tag(name = "用户管理")`)

| 方法 | 路径 | 说明 | 注解亮点 |
|------|------|------|---------|
| GET | `/api/users` | 分页查询 | `@Parameter` 描述 query 参数 |
| GET | `/api/users/{id}` | 按 ID 查询 | `@PathVariable` + 404 响应声明 |
| POST | `/api/users` | 创建用户 | `@RequestBody` + 201 响应 |
| PUT | `/api/users/{id}` | 更新用户 | `@Operation(summary/description)` |
| DELETE | `/api/users/{id}` | 删除用户 | 204 无内容响应 |

### OrderController (`/api/orders`，`@Tag(name = "订单管理")`)

| 方法 | 路径 | 说明 | 注解亮点 |
|------|------|------|---------|
| GET | `/api/orders` | 查询列表 | `@Parameter` 枚举状态过滤 |
| GET | `/api/orders/{id}` | 按 ID 查询 | `@ApiResponse` 多状态码 |
| POST | `/api/orders` | 创建订单 | 嵌套对象 `@Schema` |

## Model 设计

- **User**：`@Schema(description, example, requiredMode)` + 字段约束（`@Size`、`@Email`）
- **Order**：嵌套引用 User，`@Schema(enumAsRef=true)` 展示枚举处理
- **OrderStatus**：枚举，展示 `@Schema(description)` 在枚举上的用法
- **ApiError**：统一错误格式，通过 `@ApiResponse` 在 Controller 全局引用

## 数据层

无数据库，Controller 内用 `ConcurrentHashMap` 做内存存储，保持示例自包含。

## 配置（application.yml）

- `springdoc.api-docs.path: /v3/api-docs`
- `springdoc.swagger-ui.enabled: false`

## 验证

- `GET /v3/api-docs` 返回完整 OpenAPI 3.0 JSON
- `GET /v3/api-docs/user-api` 和 `/v3/api-docs/order-api` 返回分组文档
- `/swagger-ui.html` 返回 404
- 一个 `@SpringBootTest` + `MockMvc` 集成测试验证 `/v3/api-docs` 返回 200 且包含预期 paths/components

## 不包含

- Spring Security 依赖（仅 OpenAPI 层面声明安全方案）
- 数据库 / JPA
- Swagger UI（不引入 `springdoc-openapi-starter-webmvc-ui`）
- Docker / CI 配置
