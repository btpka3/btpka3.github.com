# first-openapi1st-api

独立 Maven 项目：**OpenAPI spec（域级拆分）+ 自动生成的 Java 接口**。

发布两个 Maven artifact：
- **JAR**（主 artifact）：生成的 Java 接口 + Model，后端直接依赖
- **YAML**（`classifier=openapi`）：bundled 完整 spec，前端/其他语言消费

## 前置条件

- Java 21
- Node.js（用于 `npx @redocly/cli bundle`）

## 构建

```shell
mvn clean install
```

构建流程：
1. `initialize`：`npx @redocly/cli bundle` 将多文件 spec 打包为 `target/dist/openapi-full.yaml`
2. `generate-sources`：`openapi-generator` 从 bundled spec 生成 Java 接口
3. `package`：打 JAR + 附加 YAML artifact

## 目录结构

```
first-openapi1st-api/
├── pom.xml
└── src/main/spec/             # OpenAPI spec 源文件（AI 生成维护）
    ├── openapi-full.yaml      # 入口文件（引用各域 + components）
    ├── openapi-base.yaml      # 顶层 info/servers/tags（备注用，已内联到入口）
    ├── domains/               # 按业务域拆分
    │   ├── pet/
    │   │   ├── pet.yaml       # Pet 域 paths
    │   │   └── models.yaml    # Pet 域 schemas
    │   ├── user/
    │   └── store/
    ├── components/            # 跨域共享
    │   ├── schemas/           # pagination, error, common
    │   ├── parameters/        # PageNum, PageSize
    │   └── security-schemes.yaml
    └── prompts/               # AI 生成 spec 的 prompt 模板
        └── domain-template.md
```

## 消费方式

### Java 后端（直接依赖 JAR）

```xml
<dependency>
    <groupId>com.github.btpka3</groupId>
    <artifactId>first-openapi1st-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

生成的接口方法带 `default` 实现（返回 HTTP 501），后端只需 override 要实现的方法。

### 前端 / 其他语言（下载 YAML）

```xml
<!-- Maven 下载 -->
<artifactItem>
    <groupId>com.github.btpka3</groupId>
    <artifactId>first-openapi1st-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <type>yaml</type>
    <classifier>openapi</classifier>
</artifactItem>
```

或手动：
```shell
mvn dependency:copy -Dartifact=com.github.btpka3:first-openapi1st-api:0.0.1-SNAPSHOT:yaml:openapi -DoutputDirectory=.
```

## CI 集成

### Breaking Change 检测

在 CI 流水线中，合入前检测是否有破坏性变更：

```yaml
# CI pipeline 示例
steps:
  - name: Bundle current spec
    run: npx @redocly/cli bundle src/main/spec/openapi-full.yaml -o current.yaml

  - name: Get baseline spec (main branch)
    run: |
      git show main:src/main/spec/openapi-full.yaml > /dev/null 2>&1 && \
      git stash && git checkout main && \
      npx @redocly/cli bundle src/main/spec/openapi-full.yaml -o baseline.yaml && \
      git checkout - && git stash pop || echo "No baseline (first build)"

  - name: Detect breaking changes
    run: npx @openapitools/openapi-diff baseline.yaml current.yaml --fail-on-incompatible
```

工具选择：
- [`@openapitools/openapi-diff`](https://github.com/OpenAPITools/openapi-diff) — 检测 breaking change
- [`@redocly/cli lint`](https://redocly.com/docs/cli/) — spec 规范校验

### Lint 校验

```shell
npx @redocly/cli lint src/main/spec/openapi-full.yaml
```

## 与 Petstore Swagger 的关系

本项目的 spec **不是**从 `https://petstore.swagger.io/v2/swagger.json` 拆分而来，而是复用 Petstore 业务域（pet/user/store）作为素材，面向"spec-first + AI 生成"场景重新设计的。

主要差异：
- OpenAPI 3.1.0（原始为 Swagger 2.0）
- 11 个精简接口（原始 20 个），去掉了 uploadFile、inventory、login/logout、批量创建等
- RESTful 复数路径（`/pets` 而非 `/pet`）
- 统一分页（`pageNum/pageSize` + `PageMeta` 响应体）和错误格式（`ErrorResponse`）
- 列表接口返回包装体 `{data: [...], page: {...}}`，而非裸数组

## 域拆分原则

| 原则 | 说明 |
|------|------|
| 一个域 = 一个聚合根 | Pet 域 = Pet CRUD，不含 Order |
| 域内自洽 | 域私有 model 放域目录下 |
| 跨域引用走 components | 通过入口文件的 `#/components/schemas/` 引用 |
| 一个域一个 AI 上下文 | AI 生成时只需关注目标域文件 |
