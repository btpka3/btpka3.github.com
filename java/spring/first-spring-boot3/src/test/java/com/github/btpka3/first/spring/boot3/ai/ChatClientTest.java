package com.github.btpka3.first.spring.boot3.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/12/18
 */
public class ChatClientTest {


    public void test01() {
        ChatModel myChatModel = null;
        ChatClient chatClient = ChatClient.builder(myChatModel)
                .build();

        Resource resource = new ClassPathResource("classpath:correct-and-expand.st");
        PromptTemplate promptTemplate = new PromptTemplate(resource);
        String input = "";
        Prompt prompt = promptTemplate.create(Map.of("input", input));
        String s = chatClient.prompt()
                .advisors(advisor -> advisor.param("chat_memory_conversation_id", "678")
                        .param("chat_memory_response_size", 100))
                .user(input)
                .tools(new DateTimeTools())
                .call()
                .content();
    }


}
