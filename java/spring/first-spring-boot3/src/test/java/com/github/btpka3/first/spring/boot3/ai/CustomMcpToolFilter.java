package com.github.btpka3.first.spring.boot3.ai;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.McpConnectionInfo;
import org.springframework.ai.mcp.McpToolFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomMcpToolFilter implements McpToolFilter {

    @Override
    public boolean test(McpConnectionInfo connectionInfo, McpSchema.Tool tool) {
        // Filter logic based on connection information and tool properties
        // Return true to include the tool, false to exclude it

        // Example: Exclude tools from a specific client
        if (connectionInfo.clientInfo().name().equals("restricted-client")) {
            return false;
        }

        // Example: Only include tools with specific names
        if (tool.name().startsWith("allowed_")) {
            return true;
        }

        // Example: Filter based on tool description or other properties
        if (tool.description() != null &&
            tool.description().contains("experimental")) {
            return false;
        }

        return true; // Include all other tools by default
    }
}