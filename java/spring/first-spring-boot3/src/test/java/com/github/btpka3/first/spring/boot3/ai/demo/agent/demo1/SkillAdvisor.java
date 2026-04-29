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

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.scheduler.Scheduler;

import java.util.List;

/**
 * Skill Advisor - 智能技能路由
 * <p>
 * 根据用户输入意图自动选择合适的技能（Skill），并将技能特定的指导提示词
 * 注入到 Prompt 的 SystemMessage 中，引导 AI 使用正确的工具完成请求。
 * <p>
 * 工作流程：
 * 1. 在 {@link #before} 中提取用户消息内容
 * 2. 通过 {@link SkillMatcher} 匹配最合适的技能
 * 3. 将技能的系统提示词追加（或创建）到 Prompt 的 SystemMessage 中
 * 4. 将修改后的请求传递给下游 Advisor 链
 * <p>
 * 基于 Spring AI 2.0.0-M4 的 {@link BaseAdvisor} 接口实现。
 *
 * @author example
 * @since 1.0.0
 */
@Component
public class SkillAdvisor implements BaseAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(SkillAdvisor.class);

    /**
     * 技能匹配器，根据用户消息匹配最合适的技能。
     */
    private final SkillMatcher skillMatcher;

    /**
     * 执行顺序，值越小优先级越高。
     * 默认设置为较高的优先级，确保在其他 Advisor 之前注入技能提示词。
     */
    private final int order;

    /**
     * 流式处理使用的调度器。
     */
    private final Scheduler scheduler;

    private SkillAdvisor(SkillMatcher skillMatcher, int order, Scheduler scheduler) {
        Assert.notNull(skillMatcher, "skillMatcher cannot be null");
        Assert.notNull(scheduler, "scheduler cannot be null");
        this.skillMatcher = skillMatcher;
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
        return "SkillAdvisor";
    }

    /**
     * 在调用链之前执行：匹配技能并注入系统提示词。
     */
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        Prompt prompt = chatClientRequest.prompt();

        // 获取用户最后一条消息
        String userMessage = extractUserMessage(prompt);
        if (userMessage == null || userMessage.isBlank()) {
            return chatClientRequest;
        }

        // 匹配技能
        List<MatchedSkill> matchedSkills = this.skillMatcher.match(userMessage);
        if (matchedSkills.isEmpty()) {
            logger.debug("No matching skill for user message: {}", userMessage);
            return chatClientRequest;
        }

        // 选择匹配度最高的技能
        MatchedSkill bestMatch = matchedSkills.get(0);
        logger.debug("Matched skill '{}' for user message", bestMatch.skill().name());

        // 构建技能提示词
        String skillPrompt = buildSkillPrompt(bestMatch);

        // 将技能提示词注入到 SystemMessage
        Prompt modifiedPrompt = prompt.augmentSystemMessage(
                existingSystem -> {
                    String existingText = existingSystem.getText();
                    String combined = (existingText != null && !existingText.isBlank())
                            ? existingText + "\n\n" + skillPrompt
                            : skillPrompt;
                    return existingSystem.mutate().text(combined).build();
                }
        );

        return chatClientRequest.mutate().prompt(modifiedPrompt).build();
    }

    /**
     * 在调用链之后执行：无需额外处理。
     */
    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    /**
     * 从 Prompt 中提取最后一条用户消息。
     */
    @Nullable
    private String extractUserMessage(Prompt prompt) {
        List<Message> messages = prompt.getInstructions();
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = messages.get(i);
            String text = message.getText();
            if (text != null && !text.isBlank()) {
                return text;
            }
        }
        return null;
    }

    /**
     * 构建技能特定的系统提示词。
     */
    private String buildSkillPrompt(MatchedSkill matchedSkill) {
        SkillRegistry.Skill skill = matchedSkill.skill();
        return """
                ## 当前激活技能
                名称：%s
                描述：%s
                可用工具方法：%s
                
                请按照该技能的职责和规则来响应用户请求，优先使用上述工具方法完成任务。
                如果工具调用失败，请向用户解释原因并提供替代方案。
                """.formatted(
                skill.name(),
                skill.description(),
                String.join(", ", skill.tools())
        );
    }

    /**
     * 创建 Builder。
     */
    public static Builder builder(SkillMatcher skillMatcher) {
        return new Builder(skillMatcher);
    }

    /**
     * Builder for {@link SkillAdvisor}.
     */
    public static final class Builder {

        private int order = Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER - 100;

        private Scheduler scheduler = BaseAdvisor.DEFAULT_SCHEDULER;

        private final SkillMatcher skillMatcher;

        private Builder(SkillMatcher skillMatcher) {
            Assert.notNull(skillMatcher, "skillMatcher cannot be null");
            this.skillMatcher = skillMatcher;
        }

        /**
         * 设置 Advisor 的执行顺序。
         *
         * @param order 顺序值，越小优先级越高
         * @return the builder
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * 设置流式处理的调度器。
         *
         * @param scheduler 调度器
         * @return the builder
         */
        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        /**
         * 构建 {@link SkillAdvisor}。
         *
         * @return the advisor
         */
        public SkillAdvisor build() {
            return new SkillAdvisor(this.skillMatcher, this.order, this.scheduler);
        }
    }

    // ============================================================================
    // 内部类型定义
    // ============================================================================

    /**
     * 技能注册中心接口。
     */
    public interface SkillMatcher {

        /**
         * 根据用户消息匹配技能列表，按匹配度降序排列。
         *
         * @param userMessage 用户消息
         * @return 匹配的技能列表（含匹配度分数）
         */
        List<MatchedSkill> match(String userMessage);
    }

    /**
     * 匹配结果，包含技能和匹配分数。
     *
     * @param skill 匹配的技能
     * @param score 匹配分数（越高越匹配）
     */
    public record MatchedSkill(SkillRegistry.Skill skill, double score) {
    }

//    /**
//     * 技能定义记录。
//     * @param id 技能唯一标识
//     * @param name 技能名称
//     * @param description 技能描述
//     * @param toolNames 使用的工具名称列表
//     * @param keywords 关键词（用于匹配用户意图）
//     */
//    public record SkillRegistry(
//            String id,
//            String name,
//            String description,
//            List<String> toolNames,
//            List<String> keywords
//    ) {
//    }
}
