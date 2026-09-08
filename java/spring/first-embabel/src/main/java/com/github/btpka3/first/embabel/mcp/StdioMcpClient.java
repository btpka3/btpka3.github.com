package com.github.btpka3.first.embabel.mcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class StdioMcpClient implements McpClient {

    private final String serverName;
    private final List<String> command;
    private final Map<String, String> env;
    private final ObjectMapper objectMapper;
    private Process process;
    private Writer writer;
    private BufferedReader reader;
    private final AtomicInteger idGen = new AtomicInteger(0);
    private boolean initialized = false;

    public StdioMcpClient(String serverName, List<String> command, Map<String, String> env, ObjectMapper objectMapper) {
        this.serverName = serverName;
        this.command = command;
        this.env = env;
        this.objectMapper = objectMapper;
    }

    @Override
    public String serverName() {
        return serverName;
    }

    @Override
    public boolean initialized() {
        return initialized;
    }

    public void start() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().putAll(env);
        pb.redirectErrorStream(true);
        process = pb.start();
        writer = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);
        reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        ObjectNode init = objectMapper.createObjectNode();
        init.put("jsonrpc", "2.0");
        init.put("id", idGen.incrementAndGet());
        init.put("method", "initialize");
        ObjectNode params = init.putObject("params");
        params.put("protocolVersion", "2024-11-05");
        params.set("capabilities", objectMapper.createObjectNode());
        ObjectNode clientInfo = params.putObject("clientInfo");
        clientInfo.put("name", "first-embabel");
        clientInfo.put("version", "0.1.0");

        JsonNode response = request(init);
        if (response == null || response.has("error")) {
            throw new RuntimeException("MCP initialize failed: " + response);
        }
        initialized = true;

        ObjectNode notified = objectMapper.createObjectNode();
        notified.put("jsonrpc", "2.0");
        notified.put("method", "notifications/initialized");
        send(notified);
    }

    @Override
    public String listTools() throws Exception {
        ObjectNode req = objectMapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", idGen.incrementAndGet());
        req.put("method", "tools/list");
        req.set("params", objectMapper.createObjectNode());
        JsonNode resp = request(req);
        return resp != null ? resp.path("result").toString() : "{}";
    }

    @Override
    public String callTool(String toolName, Map<String, Object> arguments) throws Exception {
        ObjectNode req = objectMapper.createObjectNode();
        req.put("jsonrpc", "2.0");
        req.put("id", idGen.incrementAndGet());
        req.put("method", "tools/call");
        ObjectNode params = req.putObject("params");
        params.put("name", toolName);
        ObjectNode argsNode = params.putObject("arguments");
        for (Map.Entry<String, Object> e : arguments.entrySet()) {
            argsNode.set(e.getKey(), objectMapper.valueToTree(e.getValue()));
        }
        JsonNode resp = request(req);
        if (resp == null) {
            return "{}";
        }
        JsonNode result = resp.path("result");
        ArrayNode content = (ArrayNode) result.path("content");
        StringBuilder sb = new StringBuilder();
        for (JsonNode c : content) {
            if ("text".equals(c.path("type").asText())) {
                sb.append(c.path("text").asText()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public void close() {
        if (process != null && process.isAlive()) {
            process.destroyForcibly();
        }
    }

    private JsonNode request(JsonNode req) throws IOException {
        send(req);
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                JsonNode node = objectMapper.readTree(line);
                if (node.has("id") && node.path("id").asInt() == req.path("id").asInt()) {
                    return node;
                }
            } catch (Exception e) {
                log.warn("Non-JSON line from MCP server {}: {}", serverName, line);
            }
        }
        return null;
    }

    private void send(JsonNode node) throws IOException {
        String line = objectMapper.writeValueAsString(node);
        log.debug("MCP -> {}: {}", serverName, line);
        writer.write(line);
        writer.write("\n");
        writer.flush();
    }
}
