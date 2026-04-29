package com.github.btpka3.first.spring.boot4.ai.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openaisdk.OpenAiSdkEmbeddingModel;
import org.springframework.ai.openaisdk.OpenAiSdkEmbeddingOptions;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class OpenAIClientTest {

    public void x() {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .input("Say this is a test")
                .model(ChatModel.GPT_5_2)
                .build();
        Response response = client.responses().create(params);

        var embeddingOptions = OpenAiSdkEmbeddingOptions.builder()
                .model("text-embedding-ada-002")
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();

        var embeddingModel = new OpenAiSdkEmbeddingModel(embeddingOptions);

        EmbeddingResponse embeddingResponse = embeddingModel
                .embedForResponse(List.of("Hello World", "World is big and salvation is near"));
    }
}
