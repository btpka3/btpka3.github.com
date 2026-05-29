# first-openapi1st 设计文档

## 目标

演示 OpenAPI spec-first（API first）方式：从 Petstore swagger.json 自动生成 Java 接口 + Model，手写 Controller 实现接口，Spring Boot 3.5.x 运行。

## 技术选型

| 项目         | 选择                              |
|------------|-----------------------------------|
| Spring Boot | 3.5.x (parent pom)               |
| Java        | 21 (通过 .sdkmanrc 软链接管理)      |
| 代码生成插件  | openapi-generator-maven-plugin    |
| OpenAPI 源   | https://petstore.swagger.io/v2/swagger.json (构建时在线下载) |
| 生成策略     | 只生成接口 + Model，手写 Controller 实现 |
| 生成输出     | target/generated-sources/openapi/ (不入 git) |

## 项目结构

```
first-openapi1st/
├── .sdkmanrc -> ../first-spring-boot3/.sdkmanrc
├── pom.xml
├── README.md
└── src/main/java/com/github/btpka3/firstopenapi1st/
    ├── FirstOpenapi1stApplication.java
    └── controller/
        └── PetController.java
```

## 构建流程

1. `openapi-generator-maven-plugin` 在 `generate-sources` 阶段从远程 URL 下载 swagger.json 并生成代码
2. 生成物输出到 `target/generated-sources/openapi/`
3. 配置 `interfaceOnly=true`，只生成接口和 Model
4. 手写的 `PetController` implements 生成的 `PetApi` 接口

## 关键配置

- **groupId**: `com.github.btpka3`
- **artifactId**: `first-openapi1st`
- **生成器**: `spring`
- **关键参数**: `interfaceOnly=true`, `useSpringBoot3=true`, `useTags=true`
- **依赖**: spring-boot-starter-web, swagger-annotations (jakarta), jackson-databind-nullable

## 演示范围

只实现 `PetApi` 中的 `getPetById` 和 `findPetsByStatus` 两个方法，返回 mock 数据。目的是验证 spec-first 流程跑通，不做完整业务实现。
