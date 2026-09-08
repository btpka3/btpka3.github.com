# Antora + AsciiDoc 静态站点示例

基于 [Antora 3.1](https://antora.org/) + [AsciiDoc](https://asciidoctor.org/) + [antora-ui-spring](https://github.com/spring-io/antora-ui-spring) 生成静态文档站点的示例，风格参照 Spring 官方文档。

## 特性

- 使用 Spring 同款 UI 主题（antora-ui-spring）
- Kroki 图表渲染（PlantUML / Mermaid / Graphviz 等）
- 本地全文搜索（pagefind）
- i18n 多语言：中文（默认）、English
- 多版本文档（docs / docs-v1）
- 代码块多语言高亮（Java / Shell / JavaScript 等），含行号

## 环境要求

- Node.js >= 18
- npm >= 9

## 快速开始

```bash
# 1. 安装依赖
npm install

# 2. 构建静态站点（输出到 build/site/，含 pagefind 搜索索引）
npm run build

# 3. 本地预览（http://localhost:8888）
npm run serve
```

## 常用命令

| 命令 | 说明 |
|------|------|
| `npm run build` | Antora 构建 + pagefind 索引，输出到 `build/site/` |
| `npm run serve` | 启动 http-server 预览（端口 8888） |

> **注意**：Antora 没有内置的开发热重载服务器。每次修改文档后需重新执行 `npm run build`。

## 目录结构

```
.
├── docs/                   # 中文文档（默认版本）
├── docs-v1/                # v1 版本文档
├── docs-en/                # 英文文档
├── supplemental-ui/        # UI 覆盖文件
├── build/                  # 构建输出
│   └── site/               # 最终静态站点
├── antora-playbook.yml     # Antora playbook 配置
└── package.json
```

## 注意事项

- Antora playbook（`antora-playbook.yml`）中的 `content.sources` 使用相对路径指向本仓库根目录，`start_path` 分别指向 `docs/`、`docs-v1/`、`docs-en/`。
- UI 主题从 `spring-io/antora-ui-spring` GitHub Release 远程拉取（`snapshot: true` 表示每次构建都获取最新版本）。
- 搜索索引由 [pagefind](https://pagefind.app/) 在构建后自动生成，索引范围为 `article.doc` 元素内容。

---

## 原始需求

该工程是 antora + asciidoc + [antora-ui-spring](https://github.com/spring-io/antora-ui-spring) 生成静态文档站点的示例。

需要包含以下内容：

* 浏览器前端 local search
* i18n：中文（默认），英文（可以是简单示例说明）
* 多版本。默认版本需要详细，其他版本仅简单示例说明即可
* 常见的代码块要能支持多语言着色（java, shell, javascript 等），最好能支持行号等高级特性
* 给出一个尽可能详细的 asciidoc 示例，比如包含列表、表格、PlantUML、mermaid、kbd 等
* 对宽屏（比如 2560 x 1440）能显示优化，使得空白区域减少
* 给出一个从零构建上述完整功能的说明文档
* 提示：Spring 相关的文档网站有使用 antora-ui-spring
