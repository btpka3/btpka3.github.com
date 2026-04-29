package com.github.btpka3.first.spring.boot3.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class OpenAiChatModelTest {

    public void x() {
        String apiKey = "";
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .build();
        String answer = model.chat("Say 'Hello World'");
        System.out.println(answer); // Hello World
    }
}
