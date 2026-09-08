# Docusaurus + AsciiDoc 静态站点示例

基于 [Docusaurus 3.7](https://docusaurus.io/) + [AsciiDoc](https://asciidoctor.org/) 的文档站点示例，仿照 Spring 官方文档风格。

## 特性

- AsciiDoc 原生支持（通过 `@asciidoc-js/docusaurus-asciidoc` 插件）
- PlantUML / Mermaid 图表渲染
- 本地全文搜索（`@easyops-cn/docusaurus-search-local`）
- i18n 多语言：中文（默认）、English
- 文档版本化（docsVersionDropdown）
- 代码块多语言高亮（Java / Shell / JavaScript 等）

## 环境要求

- Node.js >= 18
- npm >= 9

## 快速开始

```bash
# 1. 安装依赖（含 patch-package 补丁）
npm install

# 2. 本地开发模式（热重载，默认 http://localhost:3000）
npm run start

# 3. 构建生产版本（输出到 build/）
npm run build

# 4. 预览生产构建
npm run serve
```

## 常用命令

| 命令 | 说明 |
|------|------|
| `npm run start` | 启动开发服务器（http://localhost:3000/js/docusaurus/docusaurus-asciidoc-demo/，支持热重载） |
| `npm run build` | 构建静态站点到 `build/` 目录 |
| `npm run serve` | 预览生产构建（http://localhost:3000/js/docusaurus/docusaurus-asciidoc-demo/） |
| `npm run clear` | 清除 Docusaurus 缓存和构建产物 |
| `npm run swizzle` | 弹出（自定义）Docusaurus 主题组件 |
| `npm run write-translations` | 提取可翻译的文本 |

## 目录结构

```
.
├── docs/                   # 中文文档（默认语言）
├── i18n/                   # 翻译文件
├── patches/                # patch-package 补丁
├── src/                    # 自定义 React 组件 / CSS
├── static/                 # 静态资源
├── versioned_docs/         # 历史版本文档
├── docusaurus.config.mjs   # Docusaurus 配置
├── sidebars.mjs            # 侧边栏配置
└── remark-plantuml.mjs     # PlantUML remark 插件
```

## 注意事项

- 首次 `npm install` 会自动执行 `patch-package`，对 `@asciidoc-js/docusaurus-asciidoc` 和 `@asciidoc-js/adast-util-to-mdast` 打补丁。
- AsciiDoc 文件需放在 `docs/` 目录下，扩展名为 `.adoc`，与 `.md` 文件共存。
