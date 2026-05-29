# ADR: OpenAPI Spec 同步策略与域级拆分

**状态**: 已实施  
**日期**: 2026-05-29  
**决策者**: 当千

## 背景

采用 spec-first 方式开发时，OpenAPI JSON/YAML 由 AI 全量生成（人不手动编辑），前后端分仓独立开发。需要解决两个核心问题：

1. **Spec 如何在前后端之间同步？**
2. **多个需求并行开发时，spec 如何避免合并冲突？尤其是火车发布（多需求预发合并部署）场景。**

## 约束

- Spec 由 AI 生成，人不手动编辑 → 传统的 git merge 逐行解冲突不适用
- 火车发布模式 → 多个 feature 分支最终要合入同一个 release 分支
- API 数量会膨胀 → 单文件 spec 不可维护
- 前后端分仓 → spec 需要明确的同步机制

## 考虑的方案

### 方案 A：域级拆分 + 独立 Spec 仓库 + 组合构建 ✅ 已选

按业务域（pet / user / store）拆分成多个小 spec 文件，通过 redocly bundle 组合成完整 spec，以 Maven artifact 形式发布。

**优点**：
- 域级隔离，不同需求改不同文件，git merge 天然无冲突
- AI 生成时上下文小（只需加载目标域 + 共享组件），质量更高
- 标准 git flow 即可支撑火车发布
- Maven artifact 语义化版本，前后端解耦

**缺点**：
- 需要维护额外的 spec 仓库
- 域的划分边界需要前期设计
- 同域冲突仍需人工协调

### 方案 B：Spec 跟后端仓库 + AI 全量重生成 ❌ 未选

火车发布时不做 git merge，而是把所有需求描述汇总后让 AI 基于当前基线一次性重生成完整 spec。

**未选原因**：
- AI 重生成可能引入非预期变更（改了没让它改的接口）
- 需求越多，prompt 越大，出错概率上升
- 开发阶段用"草稿 spec"，和最终合并后的 spec 可能有差异，导致返工

### 方案 C：域级独立 Spec + 独立版本号 + 发布清单 ❌ 未选

每个域完全独立版本化，通过 release manifest 声明版本组合。

**未选原因**：
- 管理成本过高（域多了清单维护繁琐）
- 跨域一致性难保证
- 对当前阶段过度设计（可从方案 A 演进到 C）

## 决策

**采用方案 A**，具体实施：

### Spec 仓库结构

```
spec-repo/
├── domains/{domain}/{domain}.yaml + models.yaml   # 按域拆分
├── components/schemas/                             # 共享类型
├── dist/openapi-full.yaml                          # 组合产出
└── scripts/bundle.sh                               # CI 组合脚本
```

### 同步机制

**选择 Maven Artifact 发布**（而非 Git Submodule），原因：
- Submodule 在多需求并行时需要手动切换分支指向，容易出错
- Maven SNAPSHOT 机制天然支持并行开发（每个 feature 有自己的 SNAPSHOT）
- 版本号语义明确，不存在"指向哪个 commit"的困惑

### 版本号策略

| 分支 | 版本号 | 用途 |
|------|--------|------|
| feature/* | `x.y.z-{branch-slug}-SNAPSHOT` | 开发阶段 |
| release/* | `x.y.z-RCn` | 火车集成验证 |
| main | `x.y.z` | 正式发布 |

### 域拆分原则

- 一个域 = 一个聚合根的边界
- 域内 model 放域目录下，跨域引用走 `components/`
- AI 生成时只加载目标域 + components，不加载全部域

## 当前实施状态

在 demo 项目 `first-openapi1st` 中，spec 模块作为 reactor 子模块（`first-openapi1st-spec`）演示了域级拆分结构。后端通过相对路径引用 spec 产出。`consumer-example-pom.xml` 文档化了独立仓库场景下的 Maven artifact 消费方式。

## 后续演进路径

1. **当前**：spec 作为同 reactor 子模块，直接引用文件路径
2. **下一步**：spec 拆为独立 Git 仓库，CI 发布 Maven artifact，后端通过 `maven-dependency-plugin` 消费
3. **远期**：如果域足够成熟且发布节奏不同，可演进为方案 C（每域独立版本）
