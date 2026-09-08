package com.github.btpka3.first.embabel.tool;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ToolRegistry {

    private final List<Tool> tools;
    private final Map<String, Tool> byName = new HashMap<>();

    @PostConstruct
    void init() {
        for (Tool tool : tools) {
            byName.put(tool.name(), tool);
        }
    }

    public Tool get(String name) {
        return byName.get(name);
    }

    public Collection<Tool> all() {
        return byName.values();
    }

    public Map<String, String> descriptions() {
        Map<String, String> map = new HashMap<>();
        for (Tool tool : byName.values()) {
            map.put(tool.name(), tool.description());
        }
        return map;
    }
}
