package com.github.btpka3.first.embabel.config;

import com.embabel.agent.spi.support.springai.SpringAiLlmService;
import com.embabel.common.ai.model.LlmOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.http.okhttp.OpenAiHttpClientBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class LlmProviderConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(OpenAiCompatibleLlmConfig.class, AnthropicLlmConfig.class)
            .withBean(ObjectMapper.class, ObjectMapper::new);

    @Test
    void shouldSelectOpenAiByDefaultAndProvideAnthropicConfiguration() {
        ConditionalOnProperty condition = OpenAiCompatibleLlmConfig.class.getAnnotation(ConditionalOnProperty.class);

        assertThat(condition).isNotNull();
        assertThat(condition.prefix()).isEqualTo("first-embabel.llm");
        assertThat(condition.name()).containsExactly("provider");
        assertThat(condition.havingValue()).isEqualTo("openai");
        assertThat(condition.matchIfMissing()).isTrue();
        assertThatCode(() -> Class.forName("com.github.btpka3.first.embabel.config.AnthropicLlmConfig"))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldCreateOnlyOpenAiServiceByDefault() {
        contextRunner
                .withPropertyValues(
                        "spring.ai.openai.base-url=https://example.test/v1",
                        "spring.ai.openai.chat.options.model=test-model")
                .run(context -> {
                    assertThat(context).hasSingleBean(SpringAiLlmService.class);
                    assertThat(context).hasSingleBean(OpenAiHttpClientBuilderCustomizer.class);

                    SpringAiLlmService service = context.getBean(SpringAiLlmService.class);
                    OpenAiChatOptions options = (OpenAiChatOptions) service.convertOptions(new LlmOptions());
                    assertThat(options.getTemperature()).isEqualTo(0.3);
                    assertThat(options.getMaxTokens()).isEqualTo(2000);
                });
    }

    @Test
    void shouldCreateOnlyAnthropicServiceWhenSelected() {
        contextRunner
                .withPropertyValues(
                        "first-embabel.llm.provider=anthropic",
                        "first-embabel.llm.anthropic.base-url=https://example.test",
                        "first-embabel.llm.anthropic.model=test-model",
                        "ANTHROPIC_AUTH_TOKEN=unit-test-token")
                .run(context -> {
                    assertThat(context).hasSingleBean(SpringAiLlmService.class);
                    assertThat(context).doesNotHaveBean(OpenAiHttpClientBuilderCustomizer.class);

                    SpringAiLlmService service = context.getBean(SpringAiLlmService.class);
                    AnthropicChatOptions options = ((AnthropicChatModel) service.getChatModel()).getOptions();
                    assertThat(options.getCustomHeaders()).containsEntry("Authorization", "Bearer unit-test-token");
                });
    }

    @Test
    void shouldRejectMissingAnthropicTokenWithoutExposingCredentialValues() {
        contextRunner
                .withPropertyValues(
                        "first-embabel.llm.provider=anthropic",
                        "first-embabel.llm.anthropic.base-url=https://example.test",
                        "first-embabel.llm.anthropic.model=test-model",
                        "ANTHROPIC_AUTH_TOKEN=")
                .run(context -> assertThat(context.getStartupFailure())
                        .hasMessageContaining("ANTHROPIC_AUTH_TOKEN is required"));
    }

    @Test
    void shouldNotExposeProvidedTokenWhenAnthropicConfigurationFails() {
        String token = "credential-sentinel";

        contextRunner
                .withPropertyValues(
                        "first-embabel.llm.provider=anthropic",
                        "first-embabel.llm.anthropic.base-url=",
                        "first-embabel.llm.anthropic.model=test-model",
                        "ANTHROPIC_AUTH_TOKEN=" + token)
                .run(context -> assertThat(context.getStartupFailure().toString())
                        .contains("Anthropic base URL is required")
                        .doesNotContain(token));
    }
}
