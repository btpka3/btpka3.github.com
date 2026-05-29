# OpenAPI Spec-First 域级拆分示例

本目录模拟一个独立的 **API Spec 仓库**的结构，演示 "AI native 生成 + 域级拆分 + 组合构建" 的 spec-first 最佳实践。

## 核心理念

1. **Spec 由 AI 全量生成**，人不手动编辑 spec 文件
2. **按业务域拆分**，每个域独立一组文件，降低并行开发冲突
3. **共享类型集中管理**，通过 `$ref` 引用
4. **CI 自动组合 + 校验**，产出单一完整 spec 供前后端消费

## 目录结构

```
demo-openapi-specs/
├── pom.xml                     # Maven 发布配置（将 spec 发布为 artifact）
├── openapi-base.yaml           # 顶层 info/servers/tags 声明
├── openapi-full.yaml           # 组合入口（$ref 引用所有域）
├── bundle.config.yaml          # redocly lint/bundle 配置
├── consumer-example-pom.xml    # 后端消费示例（如何引用 spec artifact）
├── domains/                    # 按业务域拆分
│   ├── pet/
│   │   ├── pet.yaml            # Pet 域接口定义（paths）
│   │   └── models.yaml         # Pet 域私有 model
│   ├── user/
│   │   ├── user.yaml
│   │   └── models.yaml
│   └── store/
│       ├── store.yaml
│       └── models.yaml
├── components/                 # 跨域共享组件
│   ├── schemas/
│   │   ├── pagination.yaml     # PageMeta, PageRequest
│   │   ├── error.yaml          # ErrorResponse, FieldError
│   │   └── common.yaml         # ResourceId, Timestamp, SortOrder
│   ├── parameters/
│   │   └── common-params.yaml  # PageNum, PageSize, TraceId
│   └── security-schemes.yaml   # bearerAuth, apiKey
├── prompts/                    # AI 生成 spec 的 prompt 模板
│   ├── domain-template.md      # 标准 prompt 模板
│   └── context/                # 需求描述 + 交互图归档
├── scripts/
│   ├── bundle.sh               # 组合 + lint
│   ├── validate.sh             # breaking change 检测
│   └── publish.sh              # bundle + validate + mvn deploy
└── dist/                       # CI 产出（gitignore）
    └── openapi-full.yaml       # 解析完所有 $ref 的完整 spec
```

## 域拆分原则

| 原则 | 说明 |
|------|------|
| 一个域 = 一个聚合根 | Pet 域 = Pet CRUD + 查询，不含 Order |
| 域内自洽 | 域私有 model 放域目录下，不引用其他域的私有 model |
| 跨域引用走 components | Order 引用 userId 时，用 `components/schemas/common.yaml#/ResourceId` |
| 一个域一个 AI 上下文 | AI 生成时只加载目标域 + components，不需要全部域 |

## 工作流

### 日常开发（单需求）

```
需求描述 + 交互图
       ↓
AI 生成/更新对应域的 yaml 文件
       ↓
提交到 feature 分支，CI 自动 bundle + lint
       ↓
Code Review 后合入 main
       ↓
后端/前端各自拉取最新 dist/openapi-full.yaml 生成代码
```

### 多需求并行 + 火车发布

```
feature/add-pet-photos ──→ 改 domains/pet/
feature/user-avatar    ──→ 改 domains/user/
feature/order-refund   ──→ 改 domains/store/
                              ↓
              合入 release/2024-w12 分支
              （不同域文件，git merge 无冲突）
                              ↓
              CI bundle → 完整 spec → 部署预发
                              ↓
              验证通过 → 合入 main → 发布生产
```

### 同域冲突处理

当两个需求同时修改同一域时：

1. **优先协调**：需求评审阶段识别同域冲突，协商接口命名和 model 复用
2. **先合先赢**：第一个合入的 PR 修改基线，第二个 PR 需要 rebase
3. **AI 辅助 rebase**：将两个需求描述 + 当前基线一起喂给 AI，让 AI 重新生成该域的完整 spec

## 使用方式

### 1. 安装依赖

```bash
npm install -g @redocly/cli
```

### 2. 组合 + 校验

```bash
bash scripts/bundle.sh
```

### 3. Breaking Change 检测

```bash
bash scripts/validate.sh
```

### 4. AI 生成新域/新接口

参考 `prompts/domain-template.md` 中的模板，将需求和交互图输入 AI，获取生成的 yaml 后放入对应域目录。

## Spec 发布与消费（Maven Artifact）

### 发布侧（Spec 仓库）

Spec 仓库通过 `build-helper-maven-plugin` 将 `dist/openapi-full.yaml` 作为 Maven artifact 发布。

```bash
# CI 流水线执行
bash scripts/publish.sh
```

发布后的坐标：
```xml
<groupId>com.github.btpka3</groupId>
<artifactId>petstore-api-spec</artifactId>
<version>${version}</version>
<type>yaml</type>
<classifier>openapi</classifier>
```

### 版本号策略

| Spec 仓库分支 | 发布版本号 | 说明 |
|--------------|-----------|------|
| `main` | `1.2.0` | 正式版，已上线验证 |
| `feature/add-pet-photos` | `1.3.0-add-pet-photos-SNAPSHOT` | 开发快照，频繁更新 |
| `release/2024-w12` | `1.3.0-RC1` | 候选版，火车集成验证用 |

### 消费侧（后端仓库）

见 `consumer-example-pom.xml`，核心两步：

1. `maven-dependency-plugin` 从 Maven 仓库下载 spec yaml
2. `openapi-generator-maven-plugin` 读取下载的 yaml 生成代码

切换版本只需改一个 property：
```xml
<api-spec.version>1.3.0-add-pet-photos-SNAPSHOT</api-spec.version>
```

### 端到端流程

```
┌─────────────────────────────────────────────────────────────────┐
│ 需求阶段                                                         │
│   需求描述 + 交互图 → AI 生成域 spec → 提交 spec 仓库 feature 分支 │
└──────────────────────────────┬──────────────────────────────────┘
                               ↓
┌─────────────────────────────────────────────────────────────────┐
│ Spec CI                                                          │
│   bundle + lint + validate → mvn deploy SNAPSHOT                 │
└──────────────────────────────┬──────────────────────────────────┘
                               ↓
┌─────────────────────────────────────────────────────────────────┐
│ 后端仓库                                                         │
│   pom.xml 引用 SNAPSHOT → mvn generate-sources → Java 接口生成    │
│   → 开发者实现业务逻辑                                             │
└──────────────────────────────┬──────────────────────────────────┘
                               ↓
┌─────────────────────────────────────────────────────────────────┐
│ 火车发布                                                         │
│  1. Spec 仓库: features 合入 release 分支 → 发布 RC               │
│  2. 后端仓库: api-spec.version 升级到 RC → 编译验证 → 预发部署     │
│  3. 验证通过 → Spec 合入 main → 发布正式版                         │
│  4. 后端 main 升级到正式版 → 发布生产                              │
└─────────────────────────────────────────────────────────────────┘
```

## 与当前项目的关系

本模块是**独立的 Maven 模块**（不在 `first-openapi1st` reactor 内），模拟实际的独立 spec 仓库。

消费流程：
1. 先 `mvn install` 本模块到本地 Maven 仓库
2. `first-openapi1st-api` 通过 `maven-dependency-plugin` 从本地仓库下载 spec yaml
3. `openapi-generator-maven-plugin` 读取下载的 yaml 生成 Java 代码

```xml
<!-- first-openapi1st-api/pom.xml 关键配置 -->
<artifactItem>
    <groupId>com.github.btpka3</groupId>
    <artifactId>first-openapi1st-spec</artifactId>
    <version>${api-spec.version}</version>
    <type>yaml</type>
    <classifier>openapi</classifier>
</artifactItem>
```

在实际落地时，本模块应拆为独立 Git 仓库，通过 CI 发布到远程 Maven 仓库。
