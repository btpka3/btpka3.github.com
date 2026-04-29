package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * AI Agent 核心配置类
 * <p>
 * 配置内容包括：
 * 1. ChatClient - 主要对话客户端
 * 2. ChatMemory - 对话历史记忆
 * 3. MessageCompression - 消息压缩
 * 4. Function Calling - 工具注册
 */
@Configuration
public class AiAgentConfig {

    @Value("${custom.ai.message-compression.max-history-messages:10}")
    private int maxHistoryMessages;

    @Value("${custom.ai.message-compression.summary-trigger-threshold:8}")
    private int summaryTriggerThreshold;

    /**
     * 对话记忆 - 存储对话上下文
     */
    @Bean
    public ChatMemory chatMemory() {
        // 使用内存存储，生产环境可替换为 Redis/数据库实现
        //return new InMemoryChatMemory();
        ChatMemoryRepository repo = new InMemoryChatMemoryRepository();
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repo)
                .maxMessages(100)
                .build();
    }

    @Bean
    ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .apiKey("ak")
                        .baseUrl("http://a.com/v1")
                        .build()
                )
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("xxxModelName")
                        .temperature(0.5)
                        .maxTokens(1000)
                        .build())
                .build();
    }

    /**
     * 消息压缩顾问 - 当对话过长时自动压缩
     */
    @Bean
    public MessageCompressionAdvisor messageCompressionAdvisor(ChatModel chatModel) {
        Scheduler scheduler = Schedulers.immediate();

        return MessageCompressionAdvisor.builder(chatModel)
                .scheduler(scheduler)
                .build();
    }

    /**
     * 主 ChatClient - 集成所有能力
     * <p>
     * 包含：
     * - 系统提示词
     * - 对话记忆
     * - 消息压缩
     * - 工具调用（Function Calling）
     */
    @Bean
    public ChatClient chatClient(
            ChatModel chatModel,
            ChatMemory chatMemory,
            MessageCompressionAdvisor compressionAdvisor,
            SkillRegistry skillRegistry) {

        return ChatClient.builder(chatModel)
                // 默认系统提示词
                .defaultSystem("""
                        你是一个专业的 AI 助手，具备以下能力：
                        1. 理解和回答技术问题
                        2. 调用外部工具获取实时信息
                        3. 执行特定的技能（Skills）来完成复杂任务
                        
                        请用中文回答，保持专业和友好。
                        """)

                .defaultAdvisors(
                        // 对话记忆支持
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        // 注册消息压缩
                        compressionAdvisor
                )
                // 注册所有工具（Function Calling）
                .defaultToolNames(
                        skillRegistry.getWeatherToolFunction(),
                        skillRegistry.getSearchToolFunction(),
                        skillRegistry.getCalculatorToolFunction()
                )
                .build();
    }
}
