package com.github.btpka3.first.embabel.config;

import com.embabel.common.ai.model.LlmOptions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.anthropic.AnthropicChatOptions;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class AnthropicChatOptionsConverterTest {

    private final AnthropicChatOptionsConverter converter = new AnthropicChatOptionsConverter(0.3, 2000);

    @Test
    void shouldUseConfiguredDefaultsWhenEmbabelOptionsAreUnset() {
        AnthropicChatOptions options = (AnthropicChatOptions) converter.convertOptions(new LlmOptions(), "test-model");

        assertThat(options.getModel()).isEqualTo("test-model");
        assertThat(options.getTemperature()).isEqualTo(0.3);
        assertThat(options.getMaxTokens()).isEqualTo(2000);
    }

    @Test
    void shouldMapEmbabelChatOptions() {
        LlmOptions source = new LlmOptions();
        source.setTemperature(0.7);
        source.setMaxTokens(512);
        source.setTopK(40);
        source.setTopP(0.9);
        source.setTimeout(Duration.ofSeconds(30));

        AnthropicChatOptions options = (AnthropicChatOptions) converter.convertOptions(source, "test-model");

        assertThat(options.getModel()).isEqualTo("test-model");
        assertThat(options.getTemperature()).isEqualTo(0.7);
        assertThat(options.getMaxTokens()).isEqualTo(512);
        assertThat(options.getTopK()).isEqualTo(40);
        assertThat(options.getTopP()).isEqualTo(0.9);
        assertThat(options.getTimeout()).isEqualTo(Duration.ofSeconds(30));
    }
}
