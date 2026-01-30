package com.github.btpka3.first.spring.boot3.ai.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/26
 */
public class McpServerTest {

    @Bean
    public ToolCallbackProvider myTools() {
        List<ToolCallback> tools = null;
        return ToolCallbackProvider.from(tools);
    }

    @Bean
    public List<McpServerFeatures.SyncToolSpecification> myTools2() {
        List<McpServerFeatures.SyncToolSpecification> tools = null;
        return tools;
    }

    /**
     * 方式1： 通过实现 ToolCallback 接口，并
     */
    @Service
    public static class MyTool1 implements ToolCallback {

        @Override
        public ToolDefinition getToolDefinition() {
            return null;
        }

        @Override
        public String call(String toolInput) {
            ToolContext toolContext = null;
            Optional<McpSyncServerExchange> x = McpToolUtils.getMcpExchange(toolContext);
            x.ifPresent(exchange -> {

            });

            return "";
        }
    }

    /**
     * 方式2： 通过 使用 @Tool+ @ToolParam
     */

    @Service
    public static class MyTool2 {
        @Tool(description = "Get the temperature (in celsius) for a specific location")
        public String getTemperature(
                @ToolParam(description = "The location latitude") double latitude,
                @ToolParam(description = "The location longitude") double longitude,
                ToolContext toolContext
        ) {
            // TODO
            return null;
        }
    }

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> myResources() {
        var systemInfoResource = new McpSchema.Resource("", "", "", "", "", null, null);
        var resourceSpecification = new McpServerFeatures.SyncResourceSpecification(systemInfoResource, (exchange, request) -> {
            try {
                var systemInfo = Map.of("os", "Linux", "java.version", "17");
                String jsonContent = new ObjectMapper().writeValueAsString(systemInfo);
                return new McpSchema.ReadResourceResult(
                        List.of(new McpSchema.TextResourceContents(request.uri(), "application/json", jsonContent)));
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate system info", e);
            }
        });

        return List.of(resourceSpecification);
    }
}
