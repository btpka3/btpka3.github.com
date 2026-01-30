package com.github.btpka3.first.spring.boot3.ai;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class CustomMcpSyncClientCustomizer implements McpSyncClientCustomizer {
    @Override
    public void customize(String serverConfigurationName, McpClient.SyncSpec spec) {

        List<McpSchema.Root> roots = null;

        // Customize the request timeout configuration
        spec.requestTimeout(Duration.ofSeconds(30));

        // Sets the root URIs that this client can access.
        spec.roots(roots);

        // Sets a custom sampling handler for processing message creation requests.
        spec.sampling((McpSchema.CreateMessageRequest messageRequest) -> {
            // Handle sampling
            McpSchema.CreateMessageResult result = null;
            return result;
        });

        // Sets a custom elicitation handler for processing elicitation requests.
        spec.elicitation((McpSchema.ElicitRequest request) -> {
            // handle elicitation
            return new McpSchema.ElicitResult(McpSchema.ElicitResult.Action.ACCEPT, Map.of("message", request.message()));
        });

        // Adds a consumer to be notified when progress notifications are received.
        spec.progressConsumer((McpSchema.ProgressNotification progress) -> {
            // Handle progress notifications
        });

        // Adds a consumer to be notified when the available tools change, such as tools
        // being added or removed.
        spec.toolsChangeConsumer((List<McpSchema.Tool> tools) -> {
            // Handle tools change
        });

        // Adds a consumer to be notified when the available resources change, such as resources
        // being added or removed.
        spec.resourcesChangeConsumer((List<McpSchema.Resource> resources) -> {
            // Handle resources change
        });

        // Adds a consumer to be notified when the available prompts change, such as prompts
        // being added or removed.
        spec.promptsChangeConsumer((List<McpSchema.Prompt> prompts) -> {
            // Handle prompts change
        });

        // Adds a consumer to be notified when logging messages are received from the server.
        spec.loggingConsumer((McpSchema.LoggingMessageNotification log) -> {
            // Handle log messages
        });
    }
}