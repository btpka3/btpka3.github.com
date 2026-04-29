package com.github.btpka3.first.spring.boot3.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 方式一: 如何通过同步http 把 ChatClient 的调用包装成 REST 接口。
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
@RestController
public class Chat1Controller {
    private final ChatClient chatClient;

    public Chat1Controller(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/api/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String response = chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
        return Map.of("response", response);
    }
}