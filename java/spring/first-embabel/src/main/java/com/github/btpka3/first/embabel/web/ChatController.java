package com.github.btpka3.first.embabel.web;

import com.github.btpka3.first.embabel.memory.ToolCall;
import com.github.btpka3.first.embabel.service.AgentResult;
import com.github.btpka3.first.embabel.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final AgentService agentService;

    public record ChatRequest(String message) {
    }

    public record ChatResponse(
            String answer,
            String matchedPattern,
            boolean fromMemory,
            int llmCalls,
            List<ToolCall> executedTools
    ) {
    }

    @PostMapping("/api/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        AgentResult result = agentService.process(request.message());
        return new ChatResponse(
                result.answer(),
                result.matchedPattern(),
                result.fromMemory(),
                result.llmCalls(),
                result.executedTools()
        );
    }
}
