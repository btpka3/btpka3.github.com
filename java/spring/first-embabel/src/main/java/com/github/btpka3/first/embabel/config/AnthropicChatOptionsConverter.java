package com.github.btpka3.first.embabel.config;

import com.embabel.common.ai.model.LlmOptions;
import com.embabel.common.ai.model.OptionsConverter;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.prompt.ChatOptions;

public final class AnthropicChatOptionsConverter implements OptionsConverter {

    private final double defaultTemperature;
    private final int defaultMaxTokens;

    public AnthropicChatOptionsConverter(double defaultTemperature, int defaultMaxTokens) {
        this.defaultTemperature = defaultTemperature;
        this.defaultMaxTokens = defaultMaxTokens;
    }

    @Override
    public ChatOptions convertOptions(LlmOptions options, String model) {
        return AnthropicChatOptions.builder()
                .model(model)
                .temperature(options.getTemperature() != null ? options.getTemperature() : defaultTemperature)
                .topK(options.getTopK())
                .topP(options.getTopP())
                .maxTokens(options.getMaxTokens() != null ? options.getMaxTokens() : defaultMaxTokens)
                .timeout(options.getTimeout())
                .build();
    }
}
