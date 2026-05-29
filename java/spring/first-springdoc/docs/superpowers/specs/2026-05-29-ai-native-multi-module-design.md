# AI Native 多模块 SpringDoc 设计文档

## 概述

将现有单模块 SpringDoc 示例改造为多模块项目，实现 AI Native 开发流程：
1. **API 模块**（`first-springdoc-api`）：AI Agent 生成 Java Interface + DTO + OpenAPI 注解，独立产出 OpenAPI JSON 文档
2. **实现模块**（`first-springdoc-service`）：后端 AI Agent 依据 interface 契约实现业务逻辑

前端 AI Agent 拿到 OpenAPI JSON 即可并行开发，无需等待后端实现完成。

## 技术选型

- Maven 多模块（parent pom）
- Java 21 + Spring Boot 3.4.4
- springdoc-openapi-starter-webmvc-api 2.8.17（不含 Swagger UI）
- Lombok 1.18.38
- Spring Cloud OpenFeign 兼容（接口可复用为 Feign Client）

## 核心设计决策

### 使用 Java Interface 而非 Class

- Interface 天然是契约，与 API-First 理念一致
- Spring MVC 注解（`@RequestMapping` 等）可被实现类继承
- OpenAPI 注解（`@Operation` 等）可被 springdoc 从接口上发现
- 与 OpenFeign `@FeignClient` 完美兼容
- **`@RestController` 不能放在 interface 上**——Spring 组件扫描不继承接口上的 `@Component` 系列注解，实现类必须自行标注

### 文档生成方式

API 模块 `src/test` 中放置 stub `@RestController`（方法体抛 `UnsupportedOperationException`），通过 `@SpringBootTest` + MockMvc 请求 `/v3/api-docs` 生成 JSON 文件输出到 `target/openapi/openapi.json`。

## 项目结构

```
first-springdoc/                        # parent pom (packaging: pom)
├── pom.xml
├── first-springdoc-api/                # API 契约模块
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/github/btpka3/first/springdoc/
│       │   ├── api/
│       │   │   ├── UserApi.java        # Java Interface + Spring MVC + OpenAPI 注解
│       │   │   └── OrderApi.java
│       │   ├── model/
│       │   │   ├── User.java           # @Schema 注解的 DTO
│       │   │   ├── Order.java
│       │   │   ├── OrderStatus.java
│       │   │   └── ApiError.java
│       │   └── config/
│       │       └── OpenApiConfig.java  # @Bean OpenAPI + GroupedOpenApi
│       └── test/java/com/github/btpka3/first/springdoc/
│           ├── stub/
│           │   ├── UserApiStub.java    # @RestController implements UserApi
│           │   └── OrderApiStub.java
│           ├── StubApplication.java    # 测试用 @SpringBootApplication
│           └── OpenApiDocTest.java     # 生成文档 + 写入文件
└── first-springdoc-service/            # 实现模块
    ├── pom.xml
    └── src/
        ├── main/java/com/github/btpka3/first/springdoc/
        │   ├── FirstSpringdocApplication.java
        │   └── controller/
        │       ├── UserController.java     # @RestController implements UserApi
        │       └── OrderController.java
        ├── main/resources/
        │   └── application.yml
        └── test/java/com/github/btpka3/first/springdoc/
            └── UserControllerTest.java     # 业务集成测试
```

## 依赖关系

### parent pom

```xml
<packaging>pom</packaging>
<modules>
    <module>first-springdoc-api</module>
    <module>first-springdoc-service</module>
</modules>
```

集中管理 Spring Boot、springdoc、Lombok 版本。

### first-springdoc-api

| 依赖 | scope | 说明 |
|------|-------|------|
| spring-boot-starter-web | provided | 编译需要 Spring MVC 注解，运行由消费方提供 |
| spring-boot-starter-validation | provided | 编译需要 Jakarta Validation 注解 |
| springdoc-openapi-starter-webmvc-api | compile | OpenAPI 注解 + springdoc 运行时 |
| lombok | provided + annotationProcessor | DTO 简化 |
| spring-boot-starter-test | test | 测试容器 |

### first-springdoc-service

| 依赖 | scope | 说明 |
|------|-------|------|
| first-springdoc-api | compile | 依赖 API 契约 |
| spring-boot-starter-web | compile | WebMVC 运行时 |
| spring-boot-starter-validation | compile | 校验运行时 |
| spring-boot-starter-test | test | 集成测试 |

## API Interface 设计

### UserApi

```java
@Tag(name = "用户管理", description = "用户 CRUD 操作")
@RequestMapping("/api/users")
public interface UserApi {

    @GetMapping
    @Operation(summary = "分页查询用户列表")
    List<User> list(
        @Parameter(description = "页码，从 0 开始", example = "0") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 查询用户")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "查询成功"),
        @ApiResponse(responseCode = "404", description = "用户不存在",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<User> getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "创建用户")
    @ApiResponse(responseCode = "201", description = "创建成功")
    ResponseEntity<User> create(@Valid @RequestBody User user);

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "根据 ID 更新用户，用户不存在时返回 404")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "404", description = "用户不存在",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user);

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    @ApiResponse(responseCode = "204", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "用户不存在",
        content = @Content(schema = @Schema(implementation = ApiError.class)))
    ResponseEntity<?> delete(@PathVariable Long id);
}
```

### OrderApi

同样的 interface 模式，包含 list（支持状态过滤）、getById、create 三个方法。

## OpenAPI 全局配置

`OpenApiConfig.java` 放在 API 模块 `src/main` 中（契约的一部分）：

- API 信息：title、version、description、contact、license
- 安全方案：Bearer JWT
- 分组：`user-api`（`/api/users/**`）、`order-api`（`/api/orders/**`）

## Stub Controller（API 模块 src/test）

每个 API Interface 对应一个 stub 实现：
- 标注 `@RestController`
- 所有方法体抛 `UnsupportedOperationException`
- 仅用于让 springdoc 发现端点并生成文档

`StubApplication.java`：测试用 `@SpringBootApplication`，扫描 stub 包。

## 文档生成测试

`OpenApiDocTest.java`：
1. `@SpringBootTest` 启动 stub 容器
2. MockMvc 请求 `/v3/api-docs`，验证 JSON 结构正确
3. 将完整 JSON 写入 `target/openapi/openapi.json`
4. 分组文档分别写入 `target/openapi/user-api.json` 和 `target/openapi/order-api.json`

前端 AI Agent 从 `target/openapi/` 目录获取 API 规范。

## 实现模块

### Controller

```java
@RestController  // 必须标注，不能从 interface 继承
public class UserController implements UserApi {
    // 纯业务逻辑，不需要任何 Spring MVC / OpenAPI 注解
}
```

### 数据层

保持内存存储（`ConcurrentHashMap`），示例项目不引入数据库。

### 配置

`application.yml` 放在 service 模块，配置 `springdoc.swagger-ui.enabled: false`。

## Feign Client 扩展

其他微服务只需依赖 `first-springdoc-api`：

```java
@FeignClient(name = "user-service")
public interface UserClient extends UserApi {
}
```

零额外代码复用接口定义。当前示例项目不引入 Feign 依赖，仅保持 interface 设计兼容。

## 注解继承规则总结

| 注解类别 | interface → 实现类 | 说明 |
|---------|-------------------|------|
| `@RequestMapping` / `@GetMapping` 等 | 继承 | Spring MVC 主动查找接口 |
| `@Operation` / `@Tag` / `@Schema` 等 | 继承 | springdoc 主动查找接口 |
| `@RestController` / `@Controller` | **不继承** | 组件扫描不查找接口注解 |

## 不包含

- Spring Security 依赖（仅 OpenAPI 层面声明安全方案）
- 数据库 / JPA
- Swagger UI
- Spring Cloud OpenFeign 依赖（仅保持接口设计兼容）
- Docker / CI 配置
