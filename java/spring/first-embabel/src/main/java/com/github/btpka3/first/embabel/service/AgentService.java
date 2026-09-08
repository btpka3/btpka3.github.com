package com.github.btpka3.first.embabel.service;

import com.embabel.agent.api.common.Ai;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.btpka3.first.embabel.memory.IntentToolMapping;
import com.github.btpka3.first.embabel.memory.MemoryStore;
import com.github.btpka3.first.embabel.memory.ToolCall;
import com.github.btpka3.first.embabel.tool.Tool;
import com.github.btpka3.first.embabel.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {

    private final Ai ai;
    private final ToolRegistry toolRegistry;
    private final MemoryStore memoryStore;
    private final ObjectMapper objectMapper;

    public AgentResult process(String input) {
        int llmCalls = 0;

        // 1. Try memory
        IntentToolMapping mapping = memoryStore.findMatch(input);
        List<ToolCall> plan;
        String matchedPattern;
        boolean fromMemory = false;
        if (mapping != null && mapping.getSuccessScore() > 0) {
            plan = mapping.getTools();
            matchedPattern = mapping.getPattern();
            fromMemory = true;
            log.info("Memory matched pattern '{}' for input: {}", matchedPattern, input);
        } else {
            // 2. Plan with LLM
            plan = planWithLlm(input);
            matchedPattern = derivePattern(input);
            llmCalls++;
        }

        // 3. Execute tools
        Map<String, String> toolOutputs = new LinkedHashMap<>();
        List<ToolCall> executed = new ArrayList<>();
        for (ToolCall call : plan) {
            Tool tool = toolRegistry.get(call.name());
            if (tool == null) {
                toolOutputs.put(call.name(), "Tool not found: " + call.name());
                executed.add(call);
                continue;
            }
            String output = tool.execute(call.args() != null ? call.args() : Map.of());
            toolOutputs.put(call.name(), output);
            executed.add(call);
        }

        // 4. Synthesize answer with LLM
        String answer = synthesize(input, toolOutputs);
        llmCalls++;

        // 5. Record success
        memoryStore.recordSuccess(matchedPattern, executed, input);

        return new AgentResult(answer, matchedPattern, executed, llmCalls, fromMemory);
    }

    @SneakyThrows
    private List<ToolCall> planWithLlm(String input) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an AI agent. Choose the right tools from the list below to answer the user.\n");
        prompt.append("Available tools:\n");
        for (Map.Entry<String, String> e : toolRegistry.descriptions().entrySet()) {
            prompt.append("- ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        prompt.append("\nUser input: ").append(input).append("\n");
        prompt.append("\nReturn ONLY a JSON object with a single field 'steps', where each step has 'name' and optional 'args' map.\n");
        prompt.append("Example: {\"steps\":[{\"name\":\"a1_whoami\"},{\"name\":\"dws_dept_members\",\"args\":{\"query\":\"九宫\"}}]}\n");

        return parsePlanResponse(ai.withDefaultLlm().generateText(prompt.toString()));
    }

    List<ToolCall> parsePlanResponse(String response) {
        List<String> candidates = jsonObjectCandidates(response);
        for (int i = candidates.size() - 1; i >= 0; i--) {
            try {
                JsonNode candidate = objectMapper.readTree(candidates.get(i));
                if (!candidate.isObject() || !candidate.has("steps") || !candidate.get("steps").isArray()) {
                    continue;
                }
                Plan plan = objectMapper.treeToValue(candidate, Plan.class);
                return plan.steps() != null ? plan.steps() : List.of();
            } catch (JsonProcessingException ignored) {
            }
        }
        throw new IllegalArgumentException("Model response did not contain a valid tool plan.");
    }

    private static List<String> jsonObjectCandidates(String text) {
        List<String> candidates = new ArrayList<>();
        for (int start = 0; start < text.length(); start++) {
            if (text.charAt(start) != '{') {
                continue;
            }
            int end = matchingJsonObjectEnd(text, start);
            if (end >= 0) {
                candidates.add(text.substring(start, end + 1));
            }
        }
        return candidates;
    }

    private static int matchingJsonObjectEnd(String text, int start) {
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        for (int index = start; index < text.length(); index++) {
            char character = text.charAt(index);
            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (character == '\\') {
                    escaped = true;
                } else if (character == '"') {
                    inString = false;
                }
                continue;
            }
            if (character == '"') {
                inString = true;
            } else if (character == '{') {
                depth++;
            } else if (character == '}' && --depth == 0) {
                return index;
            }
        }
        return -1;
    }

    @SneakyThrows
    private String synthesize(String input, Map<String, String> toolOutputs) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a helpful assistant. Answer the user's question in Chinese, using the tool outputs below.\n");
        prompt.append("Be concise and accurate. If a tool output contains names, list them naturally.\n\n");
        prompt.append("User: ").append(input).append("\n\n");
        prompt.append("Tool outputs:\n");
        for (Map.Entry<String, String> e : toolOutputs.entrySet()) {
            prompt.append("--- ").append(e.getKey()).append(" ---\n");
            prompt.append(e.getValue()).append("\n");
        }
        prompt.append("\nAnswer:");
        return ai.withDefaultLlm().generateText(prompt.toString());
    }

    String derivePattern(String input) {
        String lower = input.toLowerCase();
        if (lower.contains("我是谁") || lower.contains("我是誰")) {
            return "我是谁";
        }
        if (lower.contains("部门") || lower.contains("部門") || lower.contains("员工") || lower.contains("員工") || lower.contains("同事")) {
            return "我所在部门|部门员工|部门成员";
        }
        if (lower.contains("skill")) {
            return "skill";
        }
        if (lower.contains("mcp")) {
            return "mcp";
        }
        if (lower.contains("本体") || lower.contains("ontology")) {
            return "本体|ontology";
        }
        return input;
    }

    public record Plan(List<ToolCall> steps) {
    }
}
