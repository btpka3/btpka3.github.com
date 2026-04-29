package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 提示词模板配置
 * 
 * 演示如何配置和管理各种提示词模板
 */
@Configuration
public class PromptTemplateConfig {

    /**
     * 系统提示词模板 - 定义 AI 的角色和行为
     */
    @Bean
    public SystemPromptTemplate agentSystemPrompt() {
        String template = """
                你是一个专业的 {role} 助手，名为 {name}。
                
                你的职责：
                1. {responsibility1}
                2. {responsibility2}
                3. {responsibility3}
                
                行为准则：
                - 始终保持专业和友好
                - 优先使用可用的工具获取准确信息
                - 如果不确定答案，坦诚说明
                - 用 {language} 回答
                
                当前时间：{current_time}
                """;
        
        return new SystemPromptTemplate(template);
    }

    /**
     * 代码审查专用提示词模板
     */
    @Bean
    public PromptTemplate codeReviewPrompt() {
        String template = """
                请审查以下代码，重点关注：
                
                1. **代码质量**：
                   - 命名规范
                   - 代码结构
                   - 可读性
                   
                2. **潜在问题**：
                   - Bug 和边界情况
                   - 性能问题
                   - 内存泄漏风险
                   - 线程安全问题
                   
                3. **安全漏洞**：
                   - 注入攻击风险
                   - 敏感信息泄露
                   - 权限控制
                   
                4. **改进建议**：
                   - 重构建议
                   - 设计模式应用
                   - 最佳实践
                
                代码：
                ```{language}
                {code}
                ```
                
                请提供详细的审查报告，包括问题和修复建议。
                """;
        
        return new PromptTemplate(template);
    }

    /**
     * 数据分析提示词模板
     */
    @Bean
    public PromptTemplate dataAnalysisPrompt() {
        String template = """
                作为数据分析师，请分析以下数据并提供洞察：
                
                **分析要求**：
                1. 数据概览：总结数据的基本特征
                2. 趋势分析：识别关键趋势和模式
                3. 异常检测：标记异常值或异常模式
                4. 关联分析：发现变量间的关系
                5.  actionable 建议：基于分析提供建议
                
                **数据**：
                {data}
                
                **分析维度**：{dimensions}
                
                请以结构化的方式呈现分析结果，包含具体数字和图表描述。
                """;
        
        return new PromptTemplate(template);
    }

    /**
     * Few-Shot 学习示例提示词
     */
    @Bean
    public PromptTemplate fewShotPrompt() {
        String template = """
                请将以下文本分类为相应的情感类别。
                
                示例：
                文本："这个产品非常好用，我非常满意！"
                情感：正面
                
                文本："质量太差了，完全不值这个价格。"
                情感：负面
                
                文本："产品还可以，但包装有点简陋。"
                情感：中性
                
                现在请分类以下文本：
                文本："{text}"
                情感：
                """;
        
        return new PromptTemplate(template);
    }

    /**
     * 工具使用指导提示词
     */
    @Bean
    public PromptTemplate toolUsageGuidancePrompt() {
        String template = """
                你是一个具备多种工具能力的 AI 助手。
                
                **可用工具**：
                {tool_descriptions}
                
                **使用规则**：
                1. 当用户询问实时信息时，优先调用相关工具
                2. 当需要计算时，使用计算器工具
                3. 当需要搜索信息时，使用搜索工具
                4. 工具调用失败时，告知用户并尝试替代方案
                
                **当前请求**：{user_query}
                
                请判断是否需要调用工具，如果需要，选择合适的工具并执行。
                """;
        
        return new PromptTemplate(template);
    }

    /**
     * 创建系统提示词的参数
     */
    public Map<String, Object> createAgentPromptParams() {
        return Map.of(
                "role", "技术顾问",
                "name", "AI Assistant",
                "responsibility1", "解答技术问题",
                "responsibility2", "提供代码建议和审查",
                "responsibility3", "调用工具获取实时信息",
                "language", "中文",
                "current_time", java.time.LocalDateTime.now().toString()
        );
    }
}
