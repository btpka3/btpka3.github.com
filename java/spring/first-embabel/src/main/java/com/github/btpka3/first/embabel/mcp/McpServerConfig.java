package com.github.btpka3.first.embabel.mcp;

import java.util.List;
import java.util.Map;

public record McpServerConfig(
        String name,
        String transport,
        List<String> command,
        Map<String, String> env
) {
}
