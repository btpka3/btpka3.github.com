# first-openapi1st

演示 **OpenAPI Spec-First**（API First）方式：基于 AI 生成的 OpenAPI YAML，自动生成 Java 接口 + Model，手写 Controller 实现。

## 核心理念

1. **Spec 由 AI 全量生成**：需求描述 + 交互图 → AI 生成域级 OpenAPI YAML
2. **按业务域拆分 Spec**：降低并行开发冲突（pet / user / store 各自独立文件）
3. **Spec 作为 Maven 子模块**：`first-openapi1st-spec` 产出 bundled YAML artifact
4. **接口代码自动生成**：`openapi-generator-maven-plugin` 消费 spec 产出 Java 接口

## 项目结构

```
first-openapi1st/
├── first-openapi1st-api/      # 独立 Maven 项目：OpenAPI spec + 生成的 Java 接口（发布双 artifact）
├── first-openapi1st-service/  # 业务逻辑层（使用生成的 Model 类型）
└── first-openapi1st-web/      # @RestController + Spring Boot 主类（HTTP :8080）
```

### first-openapi1st-api（独立项目，不在 reactor 内）

```
first-openapi1st-api/
├── pom.xml                    # 独立 Maven 项目，packaging=jar
├── dist/openapi-full.yaml     # 预打包的完整 spec（所有 $ref 已解析）
├── domains/                   # 按业务域拆分的源文件（AI 生成到这里）
│   ├── pet/
│   ├── user/
│   └── store/
├── components/                # 跨域共享的 schema/parameters
├── prompts/                   # AI 生成 spec 的 prompt 模板
└── scripts/                   # bundle/validate/publish 脚本（CI 使用）
```

发布两个 Maven artifact：
- **JAR**（主 artifact）：生成的 Java 接口 + Model，后端直接依赖
- **YAML**（`classifier=openapi`）：完整 OpenAPI spec，前端/其他语言消费

## 构建和运行

```shell
# Step 1: 构建 api 项目并 install 到本地 Maven 仓库（需要 Node.js）
cd first-openapi1st-api
mvn clean install
cd ..

# Step 2: 编译并启动主项目
mvn clean install -DskipTests
mvn -pl first-openapi1st-web spring-boot:run
```

> **前置条件**：构建 `first-openapi1st-api` 需要 Node.js 环境（用于 `npx @redocly/cli bundle`）。
> 
> **注意**：`first-openapi1st-api` 是独立的 Maven 项目（模拟独立 Git 仓库发布），
> 修改 spec 后需重新 `mvn install`，主项目才能获取到最新版本。
> 
> 生成的接口方法带 `default` 实现（返回 HTTP 501），后端服务只需 override 要实现的方法。

## REST 测试

```shell
# 查询宠物列表（带分页）
curl -s "http://localhost:8080/pets?pageNum=1&pageSize=10" | jq

# 根据 ID 查询宠物
curl -s http://localhost:8080/pets/1 | jq

# 根据状态过滤
curl -s "http://localhost:8080/pets?status=available" | jq
```

## 关联项目

gRPC proto-first 演示已独立为 [`first-grpc-proto1st`](../first-grpc-proto1st/)：
- 以 `.proto` 文件为 API 契约
- 服务层直接使用 proto 生成的类型（无 Model 转换）
- gRPC Server 运行在 port 9090

## Spec 同步工作流（设计方案）

详见 `first-openapi1st-spec/README.md`，核心流程：

```
需求 + 交互图 → AI 生成域 spec → bundle + lint → 发布 Maven artifact
                                                      ↓
                              后端 pom.xml 引用 spec 版本 → 生成 Java 接口 → 实现
```

### 多需求并行开发

- 不同域的变更：改不同文件，git merge 无冲突
- 同域冲突：先合先赢，第二个需求 rebase 后让 AI 重生成
- 火车发布：feature 分支合入 release 分支 → CI bundle → 预发验证 → 合入 main
