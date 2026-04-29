package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;

/**
 * AI Agent REST API 控制器
 * 
 * 提供 HTTP 接口供前端或其他服务调用
 */
@RestController
@RequestMapping("/api/ai")
public class AiAgentController {

    private final AiAgentService aiAgentService;

    public AiAgentController(AiAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    /**
     * 简单对话接口
     * 
     * POST /api/ai/chat
     * Body: { "message": "你好" }
     */
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest request) {
        String response = aiAgentService.simpleChat(request.message());
        return Map.of("response", response);
    }

    /**
     * 流式对话接口
     * 
     * POST /api/ai/chat/stream
     * 返回 SSE (Server-Sent Events)
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@RequestBody ChatRequest request) {
        SseEmitter emitter = new SseEmitter();
        Flux<String> response = aiAgentService.streamChat(request.message());

        response.subscribe(
                content -> {
                    try {
                        emitter.send(SseEmitter.event().data(content));
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                },
                emitter::completeWithError,
                emitter::complete
        );

        return emitter;
    }

    /**
     * 带工具调用的对话
     * 
     * POST /api/ai/chat/tools
     * Body: { "message": "北京今天天气怎么样？" }
     */
    @PostMapping("/chat/tools")
    public Map<String, String> chatWithTools(@RequestBody ChatRequest request) {
        String response = aiAgentService.chatWithTools(request.message());
        return Map.of("response", response);
    }

    /**
     * 代码审查接口
     * 
     * POST /api/ai/code/review
     * Body: { "code": "...", "language": "java" }
     */
    @PostMapping("/code/review")
    public Map<String, String> reviewCode(@RequestBody CodeReviewRequest request) {
        String response = aiAgentService.reviewCode(request.code(), request.language());
        return Map.of("response", response);
    }

    /**
     * 情感分类接口
     * 
     * POST /api/ai/sentiment
     * Body: { "text": "这个产品非常好用" }
     */
    @PostMapping("/sentiment")
    public Map<String, String> classifySentiment(@RequestBody SentimentRequest request) {
        String response = aiAgentService.classifySentiment(request.text());
        return Map.of("sentiment", response);
    }

    /**
     * 多轮对话接口
     * 
     * POST /api/ai/chat/multi-turn
     * Body: { "conversationId": "conv-001", "message": "你好" }
     */
    @PostMapping("/chat/multi-turn")
    public Map<String, String> multiTurnChat(@RequestBody MultiTurnRequest request) {
        String response = aiAgentService.multiTurnChat(request.conversationId(), request.message());
        return Map.of("response", response);
    }

    // ========== Request Records ==========

    public record ChatRequest(String message) {}

    public record CodeReviewRequest(String code, String language) {}

    public record SentimentRequest(String text) {}

    public record MultiTurnRequest(String conversationId, String message) {}
}
