package com.github.btpka3.first.embabel.mcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class EchoMcpServer {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Writer writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        String line;
        while ((line = reader.readLine()) != null) {
            JsonNode req = mapper.readTree(line);
            ObjectNode resp = handle(req, mapper);
            if (resp != null) {
                writer.write(mapper.writeValueAsString(resp));
                writer.write("\n");
                writer.flush();
            }
        }
    }

    private static ObjectNode handle(JsonNode req, ObjectMapper mapper) {
        String method = req.path("method").asText();
        int id = req.path("id").asInt();
        ObjectNode resp = mapper.createObjectNode();
        resp.put("jsonrpc", "2.0");
        resp.put("id", id);

        switch (method) {
            case "initialize" -> {
                ObjectNode result = resp.putObject("result");
                result.put("protocolVersion", "2024-11-05");
                result.set("capabilities", mapper.createObjectNode());
                ObjectNode serverInfo = result.putObject("serverInfo");
                serverInfo.put("name", "echo");
                serverInfo.put("version", "0.1.0");
            }
            case "notifications/initialized" -> {
                return null;
            }
            case "tools/list" -> {
                ObjectNode result = resp.putObject("result");
                ArrayNode tools = result.putArray("tools");
                ObjectNode tool = tools.addObject();
                tool.put("name", "echo");
                tool.put("description", "Echoes the input arguments back as text.");
                ObjectNode inputSchema = tool.putObject("inputSchema");
                inputSchema.put("type", "object");
                inputSchema.putObject("properties");
            }
            case "tools/call" -> {
                JsonNode arguments = req.path("params").path("arguments");
                ObjectNode result = resp.putObject("result");
                ArrayNode content = result.putArray("content");
                ObjectNode text = content.addObject();
                text.put("type", "text");
                text.put("text", "Echo: " + arguments.toString());
            }
            default -> {
                ObjectNode error = resp.putObject("error");
                error.put("code", -32601);
                error.put("message", "Method not found: " + method);
            }
        }
        return resp;
    }
}
