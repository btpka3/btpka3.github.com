package com.github.btpka3.first.spring.boot3.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

/**
 * 方式二: SSE 流式输出（Spring WebFlux，推荐）
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class Chat2Controller {
    private final ChatClient chatClient;

    public Chat2Controller(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/api/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();  // Flux<String>，每生成一段就推送给前端
    }
}
