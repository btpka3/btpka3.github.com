package com.github.btpka3.first.spring.boot3.ai;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.ToolContextToMcpMetaConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomToolContextToMcpMetaConverter implements ToolContextToMcpMetaConverter {

    @Override
    public Map<String, Object> convert(ToolContext toolContext) {
        if (toolContext == null || toolContext.getContext() == null) {
            return Map.of();
        }

        // Custom logic to convert tool context to MCP metadata
        Map<String, Object> metadata = new HashMap<>();

        // Example: Add custom prefix to all keys
        for (Map.Entry<String, Object> entry : toolContext.getContext().entrySet()) {
            if (entry.getValue() != null) {
                metadata.put("app_" + entry.getKey(), entry.getValue());
            }
        }

        // Example: Add additional metadata
        metadata.put("timestamp", System.currentTimeMillis());
        metadata.put("source", "spring-ai");

        return metadata;
    }
}