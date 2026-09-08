package com.github.btpka3.first.embabel.mcp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.btpka3.first.embabel.FirstEmbabelApplication;
import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class McpRegistry {

    private static final String INTERNAL_ECHO = "internal:echo";

    private final FirstEmbabelProperties properties;
    private final ObjectMapper objectMapper;
    private final List<McpClient> clients = new ArrayList<>();

    @PostConstruct
    void load() {
        Path config = Path.of(properties.getMcp().getConfig());
        if (!Files.exists(config)) {
            log.info("MCP config not found: {}", config);
            return;
        }
        try {
            List<McpServerConfig> configs = objectMapper.readValue(Files.readString(config), new TypeReference<>() {
            });
            for (McpServerConfig c : configs) {
                List<String> command = resolveCommand(c);
                if (command == null || command.isEmpty()) {
                    log.warn("Skipping MCP server {} with empty command", c.name());
                    continue;
                }
                StdioMcpClient client = new StdioMcpClient(c.name(), command,
                        c.env() != null ? c.env() : Collections.emptyMap(), objectMapper);
                try {
                    client.start();
                    clients.add(client);
                    log.info("Started MCP server: {}", c.name());
                } catch (Exception e) {
                    log.error("Failed to start MCP server: {}", c.name(), e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to load MCP config: {}", config, e);
        }
    }

    @PreDestroy
    void shutdown() {
        for (McpClient client : clients) {
            client.close();
        }
    }

    public List<String> serverNames() {
        return clients.stream().map(McpClient::serverName).toList();
    }

    public McpClient get(String name) {
        return clients.stream()
                .filter(c -> c.serverName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String callTool(String serverName, String toolName, Map<String, Object> arguments) throws Exception {
        McpClient client = get(serverName);
        if (client == null) {
            return "{\"error\":\"MCP server not found: " + serverName + "\"}";
        }
        return client.callTool(toolName, arguments);
    }

    private List<String> resolveCommand(McpServerConfig config) {
        if (config.command().size() == 1 && INTERNAL_ECHO.equals(config.command().get(0))) {
            String classpath = System.getProperty("java.class.path", "");
            String jar = classpath.split(java.io.File.pathSeparator)[0];
            List<String> cmd = new ArrayList<>();
            cmd.add("java");
            if (jar.endsWith(".jar")) {
                cmd.add("-jar");
                cmd.add(jar);
                cmd.add("--echo-server");
            } else {
                cmd.add("-cp");
                cmd.add(classpath);
                cmd.add(EchoMcpServer.class.getName());
            }
            return cmd;
        }
        return config.command();
    }
}
