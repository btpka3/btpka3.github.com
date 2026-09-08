package com.github.btpka3.first.embabel.config;

import com.embabel.common.ai.model.LlmOptions;
import com.embabel.common.ai.model.OptionsConverter;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;

public final class ConfiguredOpenAiChatOptionsConverter implements OptionsConverter {

    private final double defaultTemperature;
    private final int defaultMaxTokens;

    public ConfiguredOpenAiChatOptionsConverter(double defaultTemperature, int defaultMaxTokens) {
        this.defaultTemperature = defaultTemperature;
        this.defaultMaxTokens = defaultMaxTokens;
    }

    @Override
    public ChatOptions convertOptions(LlmOptions options, String model) {
        return OpenAiChatOptions.builder()
                .model(model)
                .temperature(options.getTemperature() != null ? options.getTemperature() : defaultTemperature)
                .topP(options.getTopP())
                .maxTokens(options.getMaxTokens() != null ? options.getMaxTokens() : defaultMaxTokens)
                .presencePenalty(options.getPresencePenalty())
                .frequencyPenalty(options.getFrequencyPenalty())
                .build();
    }
}
