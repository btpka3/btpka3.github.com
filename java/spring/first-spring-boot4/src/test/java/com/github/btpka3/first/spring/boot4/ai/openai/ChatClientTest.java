package com.github.btpka3.first.spring.boot4.ai.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.ToolCallAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openaisdk.OpenAiSdkEmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class ChatClientTest {

    public ChatClient initChatClient() {
        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .apiKey("openApiKey")
                        .baseUrl("https://idealab.alibaba-inc.com/api/openai")
                        .build()
                )
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("Qwen3.6-Plus-DogFooding")
                        .temperature(0.5)
                        .maxTokens(1000)
                        .build())
                .build();

        ChatMemoryRepository chatMemoryRepo = new InMemoryChatMemoryRepository();
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepo)
                .maxMessages(100)
                .build();

        EmbeddingModel embeddingModel = new OpenAiSdkEmbeddingModel();
        VectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

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
                        PromptChatMemoryAdvisor.builder(chatMemory).build(),
                        ToolCallAdvisor.builder().build(),

                        QuestionAnswerAdvisor.builder(vectorStore).build()

                )
                // 注册所有工具（Function Calling）
//                .defaultToolNames(
//                        skillRegistry.getWeatherToolFunction(),
//                        skillRegistry.getSearchToolFunction(),
//                        skillRegistry.getCalculatorToolFunction()
//                )
                .build();
    }

}
