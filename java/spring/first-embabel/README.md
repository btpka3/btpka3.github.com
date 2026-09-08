# first-embabel

基于 [Embabel Agent Framework](https://hub.embabel.com/) 的自我进化 AI Agent 实验工程。

核心能力：

- **意图-工具记忆**：相同问题再次提问时直接复用已验证的工具序列，跳过 LLM 规划（LLM 调用从 2 次降到 1 次），支持 `good` / `bad` 反馈调节评分
- **只读工具白名单**：Agent 只能执行白名单内的查询类 CLI 命令，其余一律拒绝
- **Skill 加载**：加载本机 `SKILL.md` 技能文件作为知识来源
- **MCP 接入**：通过 stdio 接入 MCP server（内置 echo server 验证链路）
- **CLI 与 Web UI 两种交互方式**

工程内**不包含**任何 LLM endpoint、API Key 等环境相关信息，全部放在 `.tmp/` 目录（不入 git）。

## 环境要求

- JDK 21+
- Maven 3.9+

## 首次配置（.tmp/ 目录）

在 `first-embabel/` 下创建 `.tmp/` 目录，包含 3 个文件（该目录已被 `.gitignore` 忽略，不会提交）：

**`.tmp/env.sh`** — LLM API Key（也可以直接 `export LLM_API_KEY=<你的key>`）：

```sh
export LLM_API_KEY="<你的 API Key>"
```

**`.tmp/application.yml`** — LLM endpoint、模型、本机 skill 目录、命令白名单等环境相关配置：

```yaml
spring:
  ai:
    openai:
      base-url: <OpenAI 兼容 endpoint，例如 https://example.com/api/openai/v1>
      chat:
        options:
          model: <模型名>

embabel:
  models:
    default-llm: <模型名，与上面一致>

first-embabel:
  skills:
    paths:
      - <本机 SKILL.md 所在目录，可多个>
  commands:
    whitelist:
      - <允许 Agent 执行的只读命令前缀，如 "xxx auth whoami">
```

**`.tmp/mcp-servers.json`** — MCP server 配置（可选；`internal:echo` 为内置测试 server）：

```json
[
  { "name": "echo", "transport": "stdio", "command": ["internal:echo"], "env": {} }
]
```

运行时产生的记忆文件在 `.tmp/memory/intent-tool-mappings.yaml`。

## 构建

```sh
cd first-embabel
mvn clean package -DskipTests
```

## 运行（CLI 模式）

单次提问（非交互）：

```sh
cd first-embabel
source .tmp/env.sh
java -jar target/first-embabel-0.1.0-SNAPSHOT.jar \
  --spring.config.additional-location=file:.tmp/ \
  --spring.main.web-application-type=none \
  ask "我是谁"
```

交互式 REPL（不带子命令直接启动）：

```sh
java -jar target/first-embabel-0.1.0-SNAPSHOT.jar \
  --spring.config.additional-location=file:.tmp/ \
  --spring.main.web-application-type=none
```

REPL 内可用命令：

| 命令 | 说明 |
|---|---|
| `chat` | 进入连续对话 |
| `ask "问题"` | 单次提问 |
| `tools` | 列出可用工具 |
| `skills` | 列出已加载 skill |
| `mcps` | 列出已配置 MCP server |
| `memory` | 查看已学习的意图-工具映射 |
| `good` / `bad` | 对上一个回答打分（影响记忆评分） |

## 运行（Web UI 模式）

```sh
cd first-embabel
source .tmp/env.sh
java -jar target/first-embabel-0.1.0-SNAPSHOT.jar \
  --spring.config.additional-location=file:.tmp/ \
  --spring.autoconfigure.exclude=org.springframework.shell.core.autoconfigure.SpringShellAutoConfiguration
```

浏览器打开 <http://localhost:8080>，页面内可直接对话；回答下方会标注「记忆命中 / 首次执行」与 LLM 调用次数，用于观察自我进化效果。

接口：`POST /api/chat`，请求体 `{"message": "我是谁"}`。

## 远端沙箱运行（Anthropic Messages API）

此模式仅适用于已由受管环境注入模型访问权限的远端沙箱。端点、模型和认证令牌只能在该沙箱内读取和调用：不得写入 `.tmp/`、Git、终端日志或本机，也不得通过 SSH 隧道、端口转发或代理在其他机器调用。

远端环境应提供以下变量，其中 `ANTHROPIC_AUTH_TOKEN` 由受管环境注入，不能复制、打印或手动保存：

```sh
export FIRST_EMBABEL_LLM_PROVIDER=anthropic
export ANTHROPIC_BASE_URL=<远端网关的 Anthropic Messages API 基地址>
export ANTHROPIC_MODEL=<受管环境允许的模型名>
[ -n "$ANTHROPIC_AUTH_TOKEN" ] || { printf '%s\\n' 'ANTHROPIC_AUTH_TOKEN is not available in this sandbox.' >&2; exit 1; }
```

远端 `.tmp/application.yml` 只保存非敏感配置：

```yaml
embabel:
  models:
    default-llm: ${ANTHROPIC_MODEL}

first-embabel:
  llm:
    provider: anthropic
    anthropic:
      base-url: ${ANTHROPIC_BASE_URL}
      model: ${ANTHROPIC_MODEL}
```

若远端网关使用额外 CA，请仅在远端私有目录创建 PKCS12 truststore。不要修改 JDK 的 `cacerts`，不要使用 trust-all `TrustManager`，也不要关闭 hostname verification。truststore 与其密码必须保留在远端私有目录或进程环境中：

```sh
umask 077
export REMOTE_GATEWAY_TRUSTSTORE="$HOME/.private/remote-gateway-truststore.p12"
export REMOTE_GATEWAY_TRUSTSTORE_PASSWORD=<仅在远端进程环境设置的密码>
keytool -importcert -noprompt \
  -alias remote-extra-ca \
  -file "$NODE_EXTRA_CA_CERTS" \
  -keystore "$REMOTE_GATEWAY_TRUSTSTORE" \
  -storetype PKCS12 \
  -storepass "$REMOTE_GATEWAY_TRUSTSTORE_PASSWORD"
```

在远端沙箱中运行 CLI：

```sh
java \
  "-Djavax.net.ssl.trustStore=$REMOTE_GATEWAY_TRUSTSTORE" \
  "-Djavax.net.ssl.trustStorePassword=$REMOTE_GATEWAY_TRUSTSTORE_PASSWORD" \
  -Djavax.net.ssl.trustStoreType=PKCS12 \
  -jar target/first-embabel-0.1.0-SNAPSHOT.jar \
  --spring.config.additional-location=file:.tmp/ \
  --spring.main.web-application-type=none \
  --embabel.models.default-llm="$ANTHROPIC_MODEL" \
  ask "我是谁"
```

在远端沙箱中运行 Web UI：

```sh
java \
  "-Djavax.net.ssl.trustStore=$REMOTE_GATEWAY_TRUSTSTORE" \
  "-Djavax.net.ssl.trustStorePassword=$REMOTE_GATEWAY_TRUSTSTORE_PASSWORD" \
  -Djavax.net.ssl.trustStoreType=PKCS12 \
  -jar target/first-embabel-0.1.0-SNAPSHOT.jar \
  --spring.config.additional-location=file:.tmp/ \
  --embabel.models.default-llm="$ANTHROPIC_MODEL" \
  --spring.autoconfigure.exclude=org.springframework.shell.core.autoconfigure.SpringShellAutoConfiguration
```

## MCP echo server 单独运行

```sh
java -jar target/first-embabel-0.1.0-SNAPSHOT.jar --echo-server
```

## 运行测试

```sh
mvn test
```

## 说明

- **两种模式的差别**：CLI 模式加 `--spring.main.web-application-type=none` 不启动 Web 容器；Web UI 模式加 `--spring.autoconfigure.exclude=...SpringShellAutoConfiguration` 排除 Spring Shell（否则 Shell 会尝试把 `--spring.*` 参数当作 shell 命令执行而报 `CommandNotFoundException`）。
- LLM 通过 `OpenAiCompatibleLlmConfig` 手工构建：部分 OpenAI 兼容网关的响应缺少 `model` 字段，官方客户端会解析失败，这里通过 OkHttp 拦截器把请求中的 `model` 回填到响应里。
- 所有配置文件中的相对路径（如 `./.tmp/...`）基于运行时工作目录，请务必在 `first-embabel/` 目录下执行启动命令。
- `NonInteractiveRunner` 使用 `ApplicationRunner` 读取非选项参数，因此 `ask "问题"` 与 `--spring.*` 配置参数可以混用。
