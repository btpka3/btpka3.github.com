# first-embabel 领域词汇表

> 本文件只记录领域语言，不写实现细节。实现决策见 `docs/adr/`。

## Agent

first-embabel 承载的 AI 助手。职责：理解用户意图、规划行动、调用工具、根据反馈沉淀经验。

## Intent（意图）

用户通过自然语言提出的目标。例如：

- "我是谁，我在哪个部门，部门有哪些员工？"
- "加载 riskctl_base 域的本体 schema"
- "列出当前配置的所有 MCP server"

## Tool（工具）

Agent 可调用的原子能力。首版只包含只读工具，例如：

- 查询当前登录用户（`a1 auth whoami`）
- 查询当前用户与部门（`dws contact user get-self`）
- 查询部门成员（`dws contact dept list-members`）
- 检索已加载 skill 内容
- 调用已配置 MCP server 的工具
- 加载 UModel 本体 schema（`umctl query run` / `umctl axiom list`）

## Skill（技能）

以 `SKILL.md` 形式沉淀的人工经验。首版只加载本地 skill，解析其 frontmatter 与正文，作为 Agent 检索和规划时的知识来源。例如：

- `gong9-ops-mcp-release`
- `g9`
- `ontology-loading`

Skill 本身不直接执行；Agent 通过检索 skill 内容来辅助决策。

## MCP Server

通过 Model Context Protocol 暴露工具的外部服务。首版通过 stdio 方式接入，默认带一个 `echo` 测试 server 验证链路，真实 server 先配置、不默认调用。

## Memory / Intent-Tool Mapping（记忆 / 意图-工具映射）

Agent 在多次交互中沉淀的"意图模式 → 成功工具序列"映射。目标是：遇到相似意图时直接复用已有工具链，减少 LLM 规划调用，实现"更快、更便宜、更准"。

映射内容包含：

- 意图模式（文本或关键词）
- 对应的工具序列
- 命中次数
- 成功评分
- 最近使用时间

## CLI（命令行交互）

用户与 Agent 交互的入口。首版同时支持：

- REPL 持续对话
- 单次执行模式
- 内建诊断命令（`/tools`、`/skills`、`/mcps`、`/memory`、`/reset`）

## Evolution（自我进化）

Agent 对重复任务的优化能力。首版限定为：

- 自动记录工具执行结果
- 用户通过 `/good` / `/bad` 反馈
- 相似意图复用已验证的工具序列
- 以 LLM 调用次数和响应时间为成本指标

## 只读命令白名单

允许 Agent 自动执行、无需人工确认的外部 CLI 命令集合。首版仅包含查询类命令，任何不在白名单中的命令只能生成计划，不能自动执行。
