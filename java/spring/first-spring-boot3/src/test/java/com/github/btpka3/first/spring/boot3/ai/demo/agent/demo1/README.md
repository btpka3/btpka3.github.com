# Spring AI 自定义 AI Agent 示例

这是一个完整的 Spring AI 自定义 AI Agent 示例项目，演示了如何构建具备以下能力的智能助手：

- ✅ **提示词工程** - 系统提示词、模板化提示词、Few-Shot 学习
- ✅ **工具调用（Function Calling）** - 天气查询、数学计算、信息搜索
- ✅ **消息压缩** - 自动摘要长对话历史，节省 token
- ✅ **Skill 系统** - 技能注册、路由和动态注入

## 项目结构

```
spring-ai-custom-agent/
├── pom.xml                          # Maven 配置
├── src/main/java/com/example/ai/
│   ├── CustomAiAgentApplication.java        # 启动类
│   ├── config/
│   │   └── AiAgentConfig.java              # AI Agent 核心配置
│   ├── prompts/
│   │   └── PromptTemplateConfig.java       # 提示词模板配置
│   ├── tools/
│   │   ├── WeatherTool.java                # 天气查询工具
│   │   ├── CalculatorTool.java             # 数学计算工具
│   │   └── SearchTool.java                 # 信息搜索工具
│   ├── compression/
│   │   └── MessageCompressionAdvisor.java  # 消息压缩 Advisor
│   ├── skills/
│   │   ├── SkillRegistry.java              # 技能注册中心
│   │   └── SkillAdvisor.java               # 技能路由 Advisor
│   ├── service/
│   │   └── AiAgentService.java             # AI Agent 服务层
│   └── controller/
│       └── AiAgentController.java          # REST API 控制器
└── src/main/resources/
    └── application.yml                     # 应用配置
```

## 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.8+
- OpenAI API Key（或其他兼容的 LLM API）

### 2. 配置 API Key

编辑 `src/main/resources/application.yml` 或设置环境变量：

```bash
export OPENAI_API_KEY=your-api-key-here
export OPENAI_BASE_URL=https://api.openai.com  # 或其他兼容 API
export AI_MODEL=gpt-4o
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

### 4. 测试 API

```bash
# 简单对话
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "你好，请介绍一下你自己"}'

# 工具调用 - 天气查询
curl -X POST http://localhost:8080/api/ai/chat/tools \
  -H "Content-Type: application/json" \
  -d '{"message": "北京今天天气怎么样？"}'

# 工具调用 - 数学计算
curl -X POST http://localhost:8080/api/ai/chat/tools \
  -H "Content-Type: application/json" \
  -d '{"message": "计算 2 的 10 次方"}'

# 代码审查
curl -X POST http://localhost:8080/api/ai/code/review \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public void process() { List list = new ArrayList(); }",
    "language": "java"
  }'

# 情感分类
curl -X POST http://localhost:8080/api/ai/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "这个产品非常好用，我非常满意！"}'
```

## 核心功能说明

### 1. 提示词配置

参见 `PromptTemplateConfig.java`，演示了多种提示词模式：

```java
// 系统提示词模板
SystemPromptTemplate agentSystemPrompt()

// 代码审查提示词
PromptTemplate codeReviewPrompt()

// 数据分析提示词
PromptTemplate dataAnalysisPrompt()

// Few-Shot 学习提示词
PromptTemplate fewShotPrompt()
```

### 2. 工具调用（Function Calling）

每个工具使用 `@Tool` 注解声明：

```java
@Tool(description = "查询指定城市的当前天气信息")
public String getWeather(@ToolParam(description = "城市名称") String city) {
    // 实现逻辑
}
```

在配置中注册到 ChatClient：

```java
.defaultFunctions("getWeather", "search", "calculate")
```

### 3. 消息压缩

`MessageCompressionAdvisor` 实现了自动压缩逻辑：

- 当对话消息数量超过阈值（默认 8 条）时触发
- 保留最近 N 条消息（默认 10 条）
- 使用 LLM 将早期消息压缩为摘要
- 用摘要消息替换早期消息

### 4. Skill 系统

Skill 是比 Tool 更高层次的抽象，可以组合多个工具和提示词：

```java
// 技能定义
Skill skill = new Skill(
    "weather",              // ID
    "天气查询",             // 名称
    "查询城市天气信息",     // 描述
    List.of("getWeather"),  // 使用的工具
    List.of("天气", "气温") // 关键词
);

// 技能路由
SkillAdvisor 根据用户意图自动匹配并注入相应的系统提示词
```

## API 端点

| 端点 | 方法 | 说明 |
|------|------|------|
| `/api/ai/chat` | POST | 简单对话 |
| `/api/ai/chat/stream` | POST | 流式对话（SSE） |
| `/api/ai/chat/tools` | POST | 带工具调用的对话 |
| `/api/ai/code/review` | POST | 代码审查 |
| `/api/ai/sentiment` | POST | 情感分类 |
| `/api/ai/chat/multi-turn` | POST | 多轮对话 |

## 扩展指南

### 添加新工具

1. 创建工具类并使用 `@Tool` 注解：

```java
@Component
public class MyTool {
    @Tool(description = "工具描述")
    public String myFunction(@ToolParam(description = "参数描述") String param) {
        // 实现
    }
}
```

2. 在 `AiAgentConfig.java` 中注册：

```java
.defaultFunctions("myFunction")
```

### 添加新技能

在 `SkillRegistry.java` 中注册：

```java
register(new Skill(
    "my_skill",
    "技能名称",
    "技能描述",
    List.of("tool1", "tool2"),
    List.of("关键词1", "关键词2")
));
```

### 自定义消息压缩策略

实现 `MessageCompressionAdvisor` 接口，调整压缩逻辑：

```java
public class MyCompressionAdvisor implements CallAroundAdvisor {
    // 自定义压缩逻辑
}
```

## 技术栈

- Spring Boot 3.3.0
- Spring AI 1.0.0-M6
- OpenAI API（兼容各种 LLM）
- Lombok
- Jackson

## 注意事项

1. **API Key 安全**：不要将 API Key 提交到代码仓库，使用环境变量
2. **Token 消耗**：长对话会消耗较多 token，建议启用消息压缩
3. **工具调用延迟**：Function Calling 会增加响应时间，合理设计工具
4. **生产环境**：聊天记忆建议使用 Redis 或数据库，而非内存存储

## 许可证

MIT License
