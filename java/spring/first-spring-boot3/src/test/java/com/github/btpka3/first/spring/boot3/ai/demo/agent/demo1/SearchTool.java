package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 搜索工具
 * 
 * 演示如何调用外部 API 实现搜索功能
 * 注意：这是示例实现，实际使用时需要配置真实的搜索 API
 */
@Component
public class SearchTool {

    private final HttpClient httpClient;

    public SearchTool() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * 搜索信息
     * 
     * 实际应用中，这里可以调用：
     * - Google Search API
     * - Bing Search API
     * - 内部搜索引擎
     * - 知识库检索
     */
    @Tool(description = "搜索相关信息。可用于查询实时数据、新闻、技术文档等。")
    public String search(
            @ToolParam(description = "搜索关键词或查询语句") String query,
            @ToolParam(description = "搜索类型：general(通用), news(新闻), tech(技术), docs(文档)", required = false) String searchType) {
        
        String type = searchType != null ? searchType : "general";

        // 模拟搜索结果（实际应用应调用真实 API）
        return switch (type.toLowerCase()) {
            case "general" -> """
                    🔍 搜索结果："%s"
                    ━━━━━━━━━━━━━━━
                    
                    【结果 1】Spring AI 官方文档
                    Spring AI 是 Spring 生态系统中的 AI 工程框架，提供统一的 API 与主流 AI 模型集成。
                    支持 Chat、Embedding、Image、Audio 等模型抽象。
                    文档地址：https://docs.spring.io/spring-ai/reference/
                    
                    【结果 2】Spring AI 教程
                    包含 ChatClient、Tool Calling、RAG、Memory 等核心功能的使用示例。
                    支持 OpenAI、Ollama、Azure OpenAI、Anthropic 等多种模型提供商。
                    
                    【提示】以上为模拟结果，实际使用请配置真实的搜索 API。
                    """.formatted(query);

            case "tech" -> """
                    🔍 技术搜索结果："%s"
                    ━━━━━━━━━━━━━━━
                    
                    【技术文章】Spring AI 架构解析
                    - ChatModel: 对话模型抽象
                    - ChatClient: 高级对话客户端
                    - Tool Calling: 函数调用机制
                    - Advisors: 拦截器和顾问模式
                    
                    【GitHub 示例】
                    https://github.com/spring-projects/spring-ai-examples
                    
                    【最佳实践】
                    1. 使用 ChatClient 而非直接使用 ChatModel
                    2. 通过 FunctionCallback 注册自定义工具
                    3. 使用 MessageChatMemoryAdvisor 管理对话历史
                    """.formatted(query);

            case "news" -> """
                    📰 新闻搜索结果："%s"
                    ━━━━━━━━━━━━━━━
                    
                    【最新资讯】
                    Spring AI 1.0.0-M6 发布，新增以下特性：
                    - 改进的 ChatClient API
                    - 结构化输出支持
                    - 向量数据库集成增强
                    - 多模态模型支持
                    
                    【发布日期】2024-06-15
                    """.formatted(query);

            default -> "❌ 不支持的搜索类型：%s。支持的类型：general, news, tech".formatted(type);
        };
    }

    /**
     * 获取文档内容（示例：如何调用外部 HTTP API）
     */
    @Tool(description = "获取指定 URL 的文档内容摘要")
    public String fetchDocumentContent(
            @ToolParam(description = "文档 URL") String url) {
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("User-Agent", "Spring-AI-Agent/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // 实际应用中应对内容进行解析和摘要
                String content = response.body();
                // 截取前 500 字符作为示例
                String preview = content.length() > 500 
                        ? content.substring(0, 500) + "..." 
                        : content;
                
                return "✅ 成功获取文档内容\n" +
                       "URL: %s\n" +
                       "内容预览:\n%s".formatted(url, preview);
            } else {
                return "❌ 获取文档失败，HTTP 状态码：%d".formatted(response.statusCode());
            }
        } catch (Exception e) {
            return "❌ 请求失败：%s".formatted(e.getMessage());
        }
    }
}
