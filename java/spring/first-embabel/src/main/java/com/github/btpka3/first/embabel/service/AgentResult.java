package com.github.btpka3.first.embabel.service;

import com.github.btpka3.first.embabel.memory.ToolCall;

import java.util.List;

public record AgentResult(
        String answer,
        String matchedPattern,
        List<ToolCall> executedTools,
        int llmCalls,
        boolean fromMemory
) {
}
