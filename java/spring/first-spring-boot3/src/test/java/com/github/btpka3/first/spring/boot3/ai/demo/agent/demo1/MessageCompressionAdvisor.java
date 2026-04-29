/*
 * Copyright 2023-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.util.Assert;

/**
 * 消息压缩 Advisor
 *
 * 当对话历史中的消息数量超过配置的阈值时，自动将早期消息压缩为摘要，
 * 以节省 token 并提高响应速度。
 *
 * 压缩策略：
 * 1. 当消息数量超过 summaryTriggerThreshold 时触发
 * 2. 保留最近的 maxMessagesToKeep 条消息
 * 3. 将更早的消息通过 LLM 压缩为一条摘要
 * 4. 用摘要消息替换早期消息，确保 SystemMessage 始终在最前面
 *
 * 基于 Spring AI 2.0.0-M4 的 {@link BaseAdvisor} 接口实现。
 *
 * @author example
 * @since 1.0.0
 */
public class MessageCompressionAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(MessageCompressionAdvisor.class);

    /**
     * 触发压缩的消息数量阈值。当消息总数超过此值时触发压缩。
     */
    private final int summaryTriggerThreshold;

    /**
     * 压缩后保留的最近消息数量。
     */
    private final int maxMessagesToKeep;

    /**
     * 用于生成摘要的 ChatModel。
     */
    private final ChatModel chatModel;

    /**
     * 执行顺序，值越小优先级越高。
     */
    private final int order;

    /**
     * 流式处理使用的调度器。
     */
    private final Scheduler scheduler;

    private MessageCompressionAdvisor(int summaryTriggerThreshold, int maxMessagesToKeep,
            ChatModel chatModel, int order, Scheduler scheduler) {
        Assert.notNull(chatModel, "chatModel cannot be null");
        Assert.isTrue(summaryTriggerThreshold > 0, "summaryTriggerThreshold must be > 0");
        Assert.isTrue(maxMessagesToKeep > 0, "maxMessagesToKeep must be > 0");
        Assert.isTrue(summaryTriggerThreshold >= maxMessagesToKeep,
                "summaryTriggerThreshold must be >= maxMessagesToKeep");
        Assert.notNull(scheduler, "scheduler cannot be null");

        this.summaryTriggerThreshold = summaryTriggerThreshold;
        this.maxMessagesToKeep = maxMessagesToKeep;
        this.chatModel = chatModel;
        this.order = order;
        this.scheduler = scheduler;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public String getName() {
        return "MessageCompressionAdvisor";
    }

    /**
     * 在调用链之前执行：检查是否需要压缩消息，如果需要则压缩。
     */
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        List<Message> messages = chatClientRequest.prompt().getInstructions();

        if (!needsCompression(messages)) {
            return chatClientRequest;
        }

        logger.debug("Message count {} exceeds threshold {}, compressing history...",
                messages.size(), this.summaryTriggerThreshold);

        List<Message> compressedMessages = compressMessages(messages);

        return chatClientRequest.mutate()
                .prompt(chatClientRequest.prompt().mutate().messages(compressedMessages).build())
                .build();
    }

    /**
     * 在调用链之后执行：无需额外处理。
     */
    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        // 消息压缩只在请求侧处理，响应侧无需额外操作
        return chatClientResponse;
    }

    /**
     * 判断是否需要压缩。
     */
    private boolean needsCompression(List<Message> messages) {
        return messages.size() > this.summaryTriggerThreshold;
    }

    /**
     * 压缩消息列表。
     *
     * 策略：
     * 1. 分离 SystemMessage（始终保留在最前面）
     * 2. 将非 SystemMessage 消息分为需要摘要的和需要保留的
     * 3. 使用 LLM 生成早期消息的摘要
     * 4. 组合：SystemMessage + 摘要消息 + 最近消息
     */
    private List<Message> compressMessages(List<Message> messages) {
        // 分离 SystemMessage
        List<Message> systemMessages = new ArrayList<>();
        List<Message> nonSystemMessages = new ArrayList<>();

        for (Message msg : messages) {
            if (msg instanceof SystemMessage) {
                systemMessages.add(msg);
            } else {
                nonSystemMessages.add(msg);
            }
        }

        // 如果非系统消息数量未超过阈值，不需要压缩
        if (nonSystemMessages.size() <= this.summaryTriggerThreshold) {
            return messages;
        }

        // 分离需要摘要的消息和需要保留的最近消息
        int messagesToSummarizeCount = nonSystemMessages.size() - this.maxMessagesToKeep;
        List<Message> messagesToSummarize = nonSystemMessages.subList(0, messagesToSummarizeCount);
        List<Message> recentMessages = nonSystemMessages.subList(messagesToSummarizeCount, nonSystemMessages.size());

        // 生成摘要
        String summary = generateSummary(messagesToSummarize);

        // 构建压缩后的消息列表
        List<Message> compressed = new ArrayList<>();

        // 1. 先添加 SystemMessage
        compressed.addAll(systemMessages);

        // 2. 添加摘要消息
        compressed.add(new UserMessage("[对话历史摘要]\n" + summary));

        // 3. 添加最近的消息
        compressed.addAll(recentMessages);

        logger.debug("Compressed {} messages to {} messages (1 summary + {} recent)",
                nonSystemMessages.size(), compressed.size(), recentMessages.size());

        return compressed;
    }

    /**
     * 使用 LLM 生成消息摘要。
     */
    private String generateSummary(List<Message> messages) {
        StringBuilder conversationText = new StringBuilder();
        for (Message msg : messages) {
            conversationText.append("[")
                    .append(msg.getMessageType())
                    .append("]: ")
                    .append(msg.getText())
                    .append("\n");
        }

        String promptTemplateText = """
                请为以下对话历史生成简洁的摘要（200 字以内），保留关键信息和上下文，以便后续对话能够理解之前的讨论内容：

                {conversation}

                摘要：
                """;

        PromptTemplate template = new PromptTemplate(promptTemplateText);
        template.add("conversation", conversationText.toString());

        try {
            Prompt prompt = template.create();
            ChatResponse response = this.chatModel.call(prompt);
            if (response != null && response.getResult() != null && response.getResult().getOutput() != null) {
                return response.getResult().getOutput().getText();
            }
            throw new IllegalStateException("Empty response from chat model");
        } catch (Exception e) {
            logger.warn("Failed to generate summary using LLM, falling back to truncation: {}", e.getMessage());
            // 如果摘要生成失败，使用简单截取
            return conversationText.substring(0, Math.min(500, conversationText.length()));
        }
    }

    /**
     * 创建 Builder。
     */
    public static Builder builder(ChatModel chatModel) {
        return new Builder(chatModel);
    }

    /**
     * Builder for {@link MessageCompressionAdvisor}.
     */
    public static final class Builder {

        private int summaryTriggerThreshold = 8;

        private int maxMessagesToKeep = 5;

        private int order = Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER + 100;

        private Scheduler scheduler = BaseAdvisor.DEFAULT_SCHEDULER;

        private final ChatModel chatModel;

        private Builder(ChatModel chatModel) {
            Assert.notNull(chatModel, "chatModel cannot be null");
            this.chatModel = chatModel;
        }

        /**
         * 设置触发压缩的消息数量阈值。
         * @param threshold 消息数量阈值
         * @return the builder
         */
        public Builder summaryTriggerThreshold(int threshold) {
            Assert.isTrue(threshold > 0, "summaryTriggerThreshold must be > 0");
            this.summaryTriggerThreshold = threshold;
            return this;
        }

        /**
         * 设置压缩后保留的最近消息数量。
         * @param count 保留的消息数量
         * @return the builder
         */
        public Builder maxMessagesToKeep(int count) {
            Assert.isTrue(count > 0, "maxMessagesToKeep must be > 0");
            this.maxMessagesToKeep = count;
            return this;
        }

        /**
         * 设置 Advisor 的执行顺序。
         * @param order 顺序值，越小优先级越高
         * @return the builder
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * 设置流式处理的调度器。
         * @param scheduler 调度器
         * @return the builder
         */
        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        /**
         * 构建 {@link MessageCompressionAdvisor}。
         * @return the advisor
         */
        public MessageCompressionAdvisor build() {
            return new MessageCompressionAdvisor(
                    this.summaryTriggerThreshold,
                    this.maxMessagesToKeep,
                    this.chatModel,
                    this.order,
                    this.scheduler);
        }
    }
}
