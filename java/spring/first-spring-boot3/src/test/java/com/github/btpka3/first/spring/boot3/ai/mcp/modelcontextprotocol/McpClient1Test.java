package com.github.btpka3.first.spring.boot3.ai.mcp.modelcontextprotocol;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.time.Duration;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/28
 */
public class McpClient1Test {

    public void x() {
        HttpClientSseClientTransport.Builder builder = HttpClientSseClientTransport.builder("http://www.baidu.com");
        builder.sseEndpoint("/sse");
        builder.customizeRequest(requestBuilder -> {
            requestBuilder.header("Content-Type", "application/json");
            // [CHANGE] 2. Add Normandy Auth headers to the request headers
            //authMaterial.generateAuthHeaders().forEach(requestBuilder::header);
        });

        HttpClientSseClientTransport  p = builder.build();

    }

    public void listTools() {

        // [CHANGE] 1. Build MCP request auth material
        // [注意] 需要传入工号作为调用者
//        ClientMcpRequestAuth mcpRequestAuth = ClientMcpRequestAuth.of(McpOptions.NO_MCP_SERVER_URL, "", "your empId");
//        RequestHeaderAuthMaterial authMaterial = mcpRequestAuth.generateAuthMaterial();

        // 若使用 Streamable HTTP 调用方式，可使用 HttpClientStreamableHttpTransport，并使用 /mcp 为后缀的域名，如
        // HttpClientStreamableHttpTransport.Builder builder = HttpClientStreamableHttpTransport
        //     .builder("http://<server>.daily-mw-mcp.taobao.net/mcp")
        String mcpServerUrl = "http://xxx.com";//注意没有 /mcp
        HttpClientStreamableHttpTransport.Builder builder = HttpClientStreamableHttpTransport.builder(mcpServerUrl);
        builder.endpoint("/mcp");
        builder.customizeRequest(requestBuilder -> {
            requestBuilder.header("Content-Type", "application/json");
            // [CHANGE] 2. Add Normandy Auth headers to the request headers
            //authMaterial.generateAuthHeaders().forEach(requestBuilder::header);
        });


        // Create a sync client with custom configuration
        McpClient.SyncSpec sync = McpClient.sync(builder.build());
        sync.requestTimeout(Duration.ofSeconds(10));
        sync.capabilities(McpSchema.ClientCapabilities.builder()
                .roots(true)      // Enable roots capability
                .sampling()       // Enable sampling capability
                .build());
        McpSyncClient client = sync
                .build();

        // Initialize connection
        client.initialize();

        // List available tools
        McpSchema.ListToolsResult tools = client.listTools();
        for (McpSchema.Tool tool : tools.tools()) {
            System.out.printf("MCP Tools: %s", tool.name());
        }

        // Close client
        client.closeGracefully();
    }
}
