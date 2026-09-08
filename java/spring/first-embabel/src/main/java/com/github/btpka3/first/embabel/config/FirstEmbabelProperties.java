package com.github.btpka3.first.embabel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "first-embabel")
public class FirstEmbabelProperties {

    private Skills skills = new Skills();
    private Memory memory = new Memory();
    private Mcp mcp = new Mcp();
    private Commands commands = new Commands();

    @Data
    public static class Skills {
        private List<String> paths = new ArrayList<>();
    }

    @Data
    public static class Memory {
        private String file = "./.tmp/memory/intent-tool-mappings.yaml";
    }

    @Data
    public static class Mcp {
        private String config = "./.tmp/mcp-servers.json";
    }

    @Data
    public static class Commands {
        private List<String> whitelist = new ArrayList<>();
        private Map<String, String> env = new HashMap<>();
    }
}
