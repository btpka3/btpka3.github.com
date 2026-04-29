package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * AI Agent 服务层
 * 
 * 演示如何使用 ChatClient 进行各种对话场景
 */
@Service
public class AiAgentService {

    private final ChatClient chatClient;
    private final PromptTemplateConfig promptTemplateConfig;
    private final SkillAdvisor skillAdvisor;

    public AiAgentService(
            ChatClient chatClient,
            PromptTemplateConfig promptTemplateConfig,
            SkillAdvisor skillAdvisor
    ) {
        this.chatClient = chatClient;
        this.promptTemplateConfig = promptTemplateConfig;
        this.skillAdvisor = skillAdvisor;
    }

    /**
     * 简单对话 - 基础问答
     */
    public String simpleChat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 流式对话 - 实时输出
     */
    public Flux<String> streamChat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    /**
     * 使用系统提示词的对话
     */
    public String chatWithSystemPrompt(String message, String role, String language) {
        Map<String, Object> params = promptTemplateConfig.createAgentPromptParams();
        params.put("role", role);
        params.put("language", language);

        return chatClient.prompt()
                .system(s -> s
                        .text(promptTemplateConfig.agentSystemPrompt().getTemplate())
                        .params(params)
                )
                .user(message)
                .call()
                .content();
    }

    /**
     * 代码审查示例
     */
    public String reviewCode(String code, String language) {
        return chatClient.prompt()
                .system("你是一个资深的代码审查专家，请仔细检查以下代码。")
                .user(u -> u
                        .text(promptTemplateConfig.codeReviewPrompt().getTemplate())
                        .params(Map.of(
                                "code", code,
                                "language", language
                        ))
                )
                .call()
                .content();
    }

    /**
     * 数据分析示例
     */
    public String analyzeData(String data, String dimensions) {
        return chatClient.prompt()
                .system("你是一个专业的数据分析师，擅长从数据中发现洞察。")
                .user(u -> u
                        .text(promptTemplateConfig.dataAnalysisPrompt().getTemplate()   )
                        .params(Map.of(
                                "data", data,
                                "dimensions", dimensions
                        ))
                )
                .call()
                .content();
    }

    /**
     * Few-Shot 情感分类示例
     */
    public String classifySentiment(String text) {
        return chatClient.prompt()
                .user(u -> u
                        .text(promptTemplateConfig.fewShotPrompt().getTemplate())
                        .params(Map.of("text", text))
                )
                .call()
                .content();
    }

    /**
     * 带工具调用的对话（ChatClient 已默认注册所有工具）
     * 
     * 示例：询问天气、计算、搜索等
     */
    public String chatWithTools(String message) {
        // ChatClient 已配置 defaultFunctions，会自动调用工具
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 使用特定技能的对话
     */
    public String chatWithSkill(String message, String skillId) {
        // 这里可以通过 SkillAdvisor 动态选择技能
        return chatClient.prompt()
                .system("当前使用技能：" + skillId)
                .user(message)
                .call()
                .content();
    }

    /**
     * 多轮对话示例（带会话 ID）
     */
    public String multiTurnChat(String conversationId, String message) {
        return chatClient.prompt()
                .system("你是一个友好的助手，记住之前的对话内容。")
                .user(message)
                .advisors(a -> a
                        .param("conversationId", conversationId)
                )
                .call()
                .content();
    }

    /**
     * 结构化输出示例
     */
    public String structuredOutput(String query) {
        return chatClient.prompt()
                .system("请以 JSON 格式返回结果，包含以下字段：answer, confidence, sources")
                .user(query)
                .call()
                .content();
    }
}
