package com.github.btpka3.first.embabel.mcp;

import java.util.Map;

public interface McpClient {

    String serverName();

    boolean initialized();

    String listTools() throws Exception;

    String callTool(String toolName, Map<String, Object> arguments) throws Exception;

    void close();
}
