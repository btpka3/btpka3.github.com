package com.github.btpka3.first.embabel.config;

import com.embabel.agent.spi.support.springai.SpringAiLlmService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.http.okhttp.OpenAiHttpClientBuilderCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.io.IOException;

// Some OpenAI-compatible gateways omit the "model" field in chat completion
// responses, which the official client rejects; the interceptor injects it
// back from the request body.
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "first-embabel.llm", name = "provider", havingValue = "openai", matchIfMissing = true)
public class OpenAiCompatibleLlmConfig {

    @Bean
    public OpenAiHttpClientBuilderCustomizer chatResponseModelCustomizer(ObjectMapper objectMapper) {
        return builder -> builder.interceptor(new ModelInjectingInterceptor(objectMapper));
    }

    @Bean
    public SpringAiLlmService chatLlmService(
            @Value("${spring.ai.openai.api-key:}") String apiKey,
            @Value("${spring.ai.openai.base-url:}") String baseUrl,
            @Value("${spring.ai.openai.chat.options.model:}") String model,
            @Value("${first-embabel.llm.temperature:0.3}") double temperature,
            @Value("${first-embabel.llm.max-tokens:2000}") int maxTokens,
            OpenAiHttpClientBuilderCustomizer customizer) {
        Assert.hasText(baseUrl, "spring.ai.openai.base-url is required. Configure it in .tmp/application.yml (see README.md).");
        Assert.hasText(model, "spring.ai.openai.chat.options.model is required. Configure it in .tmp/application.yml (see README.md).");
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(model)
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .options(options)
                .httpClientBuilderCustomizer(customizer)
                .build();
        return new SpringAiLlmService(
                model,
                "OpenAI",
                chatModel,
                new ConfiguredOpenAiChatOptionsConverter(temperature, maxTokens)
        );
    }

    static class ModelInjectingInterceptor implements Interceptor {

        private final ObjectMapper objectMapper;

        ModelInjectingInterceptor(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            ModelAndBody modelAndBody = extractModel(request);
            Response response = chain.proceed(modelAndBody.request());
            if (modelAndBody.model() == null || !isChatCompletion(response)) {
                return response;
            }
            ResponseBody body = response.body();
            if (body == null) {
                return response;
            }
            String contentType = body.contentType() != null ? body.contentType().toString() : "";
            if (!contentType.contains("json")) {
                return response;
            }
            String json = body.string();
            try {
                JsonNode root = objectMapper.readTree(json);
                if (root.isObject() && !root.has("model") && root.has("choices")) {
                    ((ObjectNode) root).put("model", modelAndBody.model());
                    json = objectMapper.writeValueAsString(root);
                    log.debug("Injected missing model '{}' into chat completion response", modelAndBody.model());
                }
            } catch (Exception e) {
                log.warn("Failed to parse response for model injection", e);
            }
            ResponseBody newBody = ResponseBody.create(json, body.contentType());
            return response.newBuilder().body(newBody).build();
        }

        private ModelAndBody extractModel(Request request) throws IOException {
            if (request.body() == null) {
                return new ModelAndBody(request, null);
            }
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            byte[] bytes = buffer.readByteArray();
            try {
                JsonNode root = objectMapper.readTree(bytes);
                if (root.isObject() && root.has("model")) {
                    String model = root.get("model").asText(null);
                    RequestBody newBody = RequestBody.create(bytes, request.body().contentType());
                    return new ModelAndBody(request.newBuilder().method(request.method(), newBody).build(), model);
                }
            } catch (Exception e) {
                log.debug("Failed to extract model from request", e);
            }
            return new ModelAndBody(request, null);
        }

        private record ModelAndBody(Request request, String model) {
        }

        private boolean isChatCompletion(Response response) {
            String path = response.request().url().encodedPath();
            return path.endsWith("/chat/completions");
        }
    }
}
