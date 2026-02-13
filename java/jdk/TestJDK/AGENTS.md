# TestJDK 项目指南

## 项目概述

TestJDK 是一个多模块 Maven 项目，专门用于测试和验证不同 Java JDK 版本的功能特性。该项目包含多个子模块，涵盖了从 Java 9 到 Java 25 的各种新特性和功能测试，特别是针对 Java 模块系统、预览功能、以及各种 Java 技术栈的集成。

### 主要特性

- **多模块架构**：项目分为 build-tools、hello 和 apt 三个主要模块
- **最新 Java 支持**：目标 Java 版本为 25，并启用了预览功能
- **丰富的技术栈**：集成了 Groovy、GraalVM JavaScript 引擎、Nashorn、BeanShell 等多种脚本引擎
- **现代化框架**：支持 Spring Boot、Netty、Reactor、JavaFX 等现代 Java 框架
- **代码质量工具**：集成了 Spotless 格式化、Checkstyle、PMD 等代码质量检查工具

## 项目结构

```
TestJDK/
├── pom.xml                 # 主 POM 文件，定义多模块结构
├── README.md              # 项目说明和构建命令
├── build-tools/           # 构建工具模块
├── hello/                 # 主要测试模块，包含各种 Java 功能测试
├── apt/                   # 注解处理器模块
└── target/                # 构建输出目录
```

### 子模块说明

#### hello 模块
- 项目的主要测试模块
- 包含各种 Java 新特性示例和测试
- 集成了多种脚本引擎和运行时环境
- 使用 JavaFX 进行桌面应用测试

#### build-tools 模块
- 提供项目的构建工具配置
- 包含代码质量检查规则
- 用于项目构建过程中的辅助工具

#### apt 模块
- 注解处理工具模块
- 用于测试 Java 编译时注解处理功能
- 集成 Lombok 等编译时代码生成工具

## 构建与运行

### 构建命令

```bash
# 清理并打包项目（跳过测试和代码格式检查）
mvn -Dspotless.check.skip=true -DskipTests=true clean package

# 清理并验证项目
mvn -Dspotless.check.skip=true -DskipTests=true clean verify

# 运行 JavaFX 应用
mvn clean javafx:run -pl hello
```

### 代码格式化

```bash
# 检查代码格式
mvn spotless:check

# 应用代码格式化
mvn spotless:apply

# 安装 Git 预推送钩子
mvn spotless:install-git-pre-push-hook
```

### Java 版本要求

- **目标版本**：Java 25
- **预览功能**：已启用（--enable-preview）
- **编译器插件**：配置为禁用预览功能（根据最近提交记录）

## 技术栈与依赖

### 核心技术
- **Groovy**：4.0.25 - 用于脚本和动态功能测试
- **GraalVM JavaScript**：24.1.2 - 现代化 JavaScript 引擎
- **Nashorn**：15.4 - 传统的 Java 内置 JavaScript 引擎
- **BeanShell**：2.0b6 - 轻量级 Java 脚本解决方案

### 框架与库
- **Spring Boot**：2.7.8 - 企业级应用开发框架
- **Netty**：4.2.1.Final - 高性能网络编程框架
- **Project Reactor**：响应式编程模型
- **JavaFX**：21.0.1 - 桌面应用界面框架
- **Lombok**：1.18.42 - 代码简化工具
- **MapStruct**：1.5.0.Final - 代码生成映射框架

### JSON/XML 处理
- **FastJSON2**：2.0.49 - 阿里巴巴高性能 JSON 库
- **Jackson**：2.17.1 - 社区主流 JSON 处理库
- **XMLUnit**：2.9.1 - XML 比较和测试工具

### 测试框架
- **JUnit 5**：5.12.2 - 现代化测试框架
- **AssertJ**：3.24.2 - 流式断言库
- **Mockito**：5.8.0 - Mock 对象框架
- **ArchUnit**：1.4.1 - 架构测试工具
- **JSON Unit**：3.2.7 - JSON 断言库

### 性能与工具
- **JMH**：1.37 - Java 微基准测试套件
- **Kryo**：4.0.3/5.6.0 - 高性能序列化库
- **Apache Commons** - 各种实用工具库集合
- **GraalVM Polyglot** - 多语言互操作性

## 开发约定

### 编码规范
- 使用 Palantir Java 格式化风格
- 代码中使用 UTF-8 编码
- 遵循 Java 最佳实践和现代特性使用

### 依赖管理
- 通过主 pom.xml 的 dependencyManagement 统一管理版本
- 使用 BOM（Bill of Materials）确保版本一致性
- 严格控制依赖冲突，使用 Maven Enforcer 插件

### 测试策略
- 单元测试使用 JUnit 5 + AssertJ
- 集成测试覆盖多组件协作场景
- 架构测试确保代码结构符合设计
- 性能测试使用 JMH 进行基准测试

## 特殊配置

### 预览功能
项目当前配置中预览功能被禁用（根据 Git 提交历史），但在某些模块中可能仍需启用特定的预览特性进行测试。

### 编译器设置
- 启用了 Lombok 和 MapStruct 注解处理器
- 使用 Error Prone 进行静态分析
- 配置了详细的编译器参数和系统属性

### 安全与合规
- 集成了 Bouncy Castle 加密库
- 使用 JSR-305 注解进行空值安全检查
- 遵循开源协议要求（如 FastJSON 许可证）

## 使用场景

这个项目主要用于：
1. 测试 Java 新版本的各种特性和 API
2. 验证不同脚本引擎在 JVM 上的表现
3. 探索现代化 Java 框架的集成方式
4. 进行性能基准测试和对比
5. 验证 Java 模块系统的实际应用