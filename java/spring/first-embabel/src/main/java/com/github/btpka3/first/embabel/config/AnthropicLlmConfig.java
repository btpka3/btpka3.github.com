package com.github.btpka3.first.embabel.config;

import com.embabel.agent.spi.support.springai.SpringAiLlmService;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "first-embabel.llm", name = "provider", havingValue = "anthropic")
public class AnthropicLlmConfig {

    @Bean
    public SpringAiLlmService chatLlmService(
            @Value("${first-embabel.llm.anthropic.base-url:${ANTHROPIC_BASE_URL:}}") String baseUrl,
            @Value("${first-embabel.llm.anthropic.model:${ANTHROPIC_MODEL:}}") String model,
            @Value("${ANTHROPIC_AUTH_TOKEN:}") String authToken,
            @Value("${first-embabel.llm.temperature:0.3}") double temperature,
            @Value("${first-embabel.llm.max-tokens:2000}") int maxTokens) {
        Assert.hasText(baseUrl, "Anthropic base URL is required. Configure it outside the repository.");
        Assert.hasText(model, "Anthropic model is required. Configure it outside the repository.");
        Assert.hasText(authToken, "ANTHROPIC_AUTH_TOKEN is required.");

        AnthropicChatOptions options = AnthropicChatOptions.builder()
                .baseUrl(baseUrl)
                .apiKey(authToken)
                .customHeaders(Map.of("Authorization", "Bearer " + authToken))
                .model(model)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
        AnthropicChatModel chatModel = AnthropicChatModel.builder()
                .options(options)
                .build();
        return new SpringAiLlmService(
                model,
                "Anthropic",
                chatModel,
                new AnthropicChatOptionsConverter(temperature, maxTokens)
        );
    }
}
