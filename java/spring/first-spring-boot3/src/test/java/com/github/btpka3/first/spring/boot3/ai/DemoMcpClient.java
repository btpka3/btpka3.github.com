package com.github.btpka3.first.spring.boot3.ai;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.json.McpJsonMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/17
 * @see <a href="https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html">MCP Client Boot Starter</a>
 *
 */
public class DemoMcpClient {

    McpSyncClient client;
    // @Autowired
    private SyncMcpToolCallbackProvider toolCallbackProvider = SyncMcpToolCallbackProvider.builder()
            .addMcpClient(client)
            .build();


    ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();

    public void x() {
        var winArgs = new ArrayList<>(Arrays.asList(
                "/c", "npx", "-y", "@modelcontextprotocol/server-filesystem", "target"));
        ServerParameters stdioParams = ServerParameters.builder("cmd.exe")
                .args(winArgs)
                .build();
        McpSyncClient client = McpClient.sync(new StdioClientTransport(stdioParams, McpJsonMapper.createDefault()))
                .requestTimeout(Duration.ofSeconds(10))
                .build();
        client.initialize();

    }

    public void y() {
        ChatModel chatModel = null;
        String response = ChatClient.create(chatModel)
                .prompt("Tell me more about the customer with ID 42")
                .toolContext(Map.of("progressToken", "my-progress-token"))
                .call()
                .content();
    }

    @Value("${ai.user.input}")
    private String userInput;

    @Bean
    public CommandLineRunner predefinedQuestions(
            ChatClient.Builder chatClientBuilder,
            ConfigurableApplicationContext context
    ) {

        return args -> {

            var chatClient = chatClientBuilder
                    .defaultTools(toolCallbackProvider)
                    .build();

            System.out.println("\n>>> QUESTION: " + userInput);
            System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());

            context.close();
        };
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
