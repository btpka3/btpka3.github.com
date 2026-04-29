package com.github.btpka3.first.spring.boot3.ai.mcp.modelcontextprotocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpServerTransportProvider;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/28
 */
public class McpService1Test {
    McpServerTransportProvider getMcpServerTransportProvider() {
        ObjectMapper objectMapper = new ObjectMapper();
        McpJsonMapper jsonMapper = new JacksonMcpJsonMapper(objectMapper);
        StdioServerTransportProvider p = new StdioServerTransportProvider(
                jsonMapper
        );
        return p;
    }

    public void x() {
        McpServerTransportProvider transportProvider = getMcpServerTransportProvider();
        McpSchema.JsonSchema schema = new McpSchema.JsonSchema(
                "type",
                Collections.emptyMap(),
                Collections.emptyList(),
                false,
                Collections.emptyMap(),
                Collections.emptyMap()
        );
        McpSchema.Tool tool = McpSchema.Tool.builder()
                .name("calculator")
                .title("Performs calculations")
                .inputSchema(schema)
                .build();
        BiFunction<McpSyncServerExchange, McpSchema.CallToolRequest, McpSchema.CallToolResult> handler = (exchange, request) ->
                McpSchema.CallToolResult.builder()
                        .content(List.of(new McpSchema.TextContent("xxxContent")))
                        .isError(false)
                        .build();
        McpServer.sync(transportProvider)
                .serverInfo("my-server", "1.0.0")
                .toolCall(tool, handler)
                .build();
    }
}
