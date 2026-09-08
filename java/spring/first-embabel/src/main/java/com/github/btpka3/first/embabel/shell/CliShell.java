package com.github.btpka3.first.embabel.shell;

import com.github.btpka3.first.embabel.memory.MemoryStore;
import com.github.btpka3.first.embabel.mcp.McpRegistry;
import com.github.btpka3.first.embabel.service.AgentResult;
import com.github.btpka3.first.embabel.service.AgentService;
import com.github.btpka3.first.embabel.skill.SkillLoader;
import com.github.btpka3.first.embabel.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Argument;

import java.io.Console;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class CliShell {

    private final AgentService agentService;
    private final ToolRegistry toolRegistry;
    private final SkillLoader skillLoader;
    private final McpRegistry mcpRegistry;
    private final MemoryStore memoryStore;
    private final AtomicReference<String> lastPattern = new AtomicReference<>();

    @Command(name = "chat", description = "Start an interactive chat session")
    @SneakyThrows
    public void chat() {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available. Use 'ask \"question\"' instead.");
            return;
        }
        System.out.println("first-embabel chat started. Type /exit to quit.");
        while (true) {
            String line = console.readLine("> ");
            if (line == null || "/exit".equalsIgnoreCase(line.trim())) {
                break;
            }
            String response = handle(line.trim());
            if (response != null) {
                System.out.println(response);
            }
        }
        System.out.println("Bye.");
    }

    @Command(name = "ask", description = "Ask a single question")
    public String ask(@Argument(index = 0) String question) {
        return handle(question);
    }

    @Command(name = "tools", description = "List available tools")
    public String tools() {
        StringBuilder sb = new StringBuilder("Available tools:\n");
        toolRegistry.descriptions().forEach((name, desc) -> sb.append("- ").append(name).append(": ").append(desc).append("\n"));
        return sb.toString().trim();
    }

    @Command(name = "skills", description = "List loaded skills")
    public String skills() {
        StringBuilder sb = new StringBuilder("Loaded skills:\n");
        skillLoader.all().forEach(s -> sb.append("- ").append(s.name()).append(": ").append(s.description()).append("\n"));
        return sb.toString().trim();
    }

    @Command(name = "mcps", description = "List configured MCP servers")
    public String mcps() {
        StringBuilder sb = new StringBuilder("Configured MCP servers:\n");
        mcpRegistry.serverNames().forEach(name -> sb.append("- ").append(name).append("\n"));
        return sb.toString().trim();
    }

    @Command(name = "memory", description = "Show intent-tool memory")
    public String memory() {
        StringBuilder sb = new StringBuilder("Intent-tool mappings:\n");
        memoryStore.all().forEach(m -> sb.append(String.format("- pattern=%s score=%.1f hits=%d tools=%s%n",
                m.getPattern(), m.getSuccessScore(), m.getHitCount(), m.getTools())));
        return sb.toString().trim();
    }

    @Command(name = "good", description = "Rate the last answer as good")
    public String good() {
        String pattern = lastPattern.get();
        if (pattern == null) {
            return "No answer to rate yet.";
        }
        memoryStore.updateScore(pattern, 0.5);
        return "Thanks, recorded as good for pattern: " + pattern;
    }

    @Command(name = "bad", description = "Rate the last answer as bad")
    public String bad() {
        String pattern = lastPattern.get();
        if (pattern == null) {
            return "No answer to rate yet.";
        }
        memoryStore.updateScore(pattern, -0.5);
        return "Thanks, recorded as bad for pattern: " + pattern;
    }

    private String handle(String input) {
        return switch (input.toLowerCase()) {
            case "/tools" -> tools();
            case "/skills" -> skills();
            case "/mcps" -> mcps();
            case "/memory" -> memory();
            case "/good" -> good();
            case "/bad" -> bad();
            case "/help" -> help();
            default -> {
                AgentResult result = agentService.process(input);
                lastPattern.set(result.matchedPattern());
                yield String.format("%s%n[memory=%s, llmCalls=%d, tools=%s]",
                        result.answer(), result.fromMemory(), result.llmCalls(), result.executedTools());
            }
        };
    }

    public String help() {
        return """
                Available commands:
                - chat        : start interactive chat
                - ask "..."   : single question
                - tools       : list tools
                - skills      : list loaded skills
                - mcps        : list MCP servers
                - memory      : show learned mappings
                - good / bad  : rate last answer
                - /exit       : quit chat
                """.trim();
    }
}
