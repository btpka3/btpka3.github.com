package com.github.btpka3.first.spring.boot4.ai;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class PromptTemplateTest {


    public void x() {
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template("""
                        Tell me the names of 5 movies whose soundtrack was composed by <composer>.
                        """)
                .build();
        String promptStr = promptTemplate.render(Map.of("composer", "John Williams"));

    }


    public void y() {
        String adjective = null;
        String topic = null;
        PromptTemplate promptTemplate = new PromptTemplate("Tell me a {adjective} joke about {topic}");
        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));
        ChatModel chatModel = null;
        chatModel.call(prompt).getResult();
    }

    public void xx() {
        String userText = """
                Tell me about three famous pirates from the Golden Age of Piracy and why they did.
                Write at least a sentence for each pirate.
                """;

        Message userMessage = new UserMessage(userText);

        String systemText = """
                You are a helpful AI assistant that helps people find information.
                Your name is {name}
                You should reply to the user's request with your name and also in the style of a {voice}.
                """;

        String name = null;
        String voice = null;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        ChatModel chatModel = null;
        List<Generation> response = chatModel.call(prompt).getResults();
    }
}
