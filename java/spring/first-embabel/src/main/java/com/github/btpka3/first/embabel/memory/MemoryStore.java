package com.github.btpka3.first.embabel.memory;

import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryStore {

    private final FirstEmbabelProperties properties;
    private final List<IntentToolMapping> mappings = new ArrayList<>();
    private boolean dirty = false;

    @PostConstruct
    void load() {
        Path file = Path.of(properties.getMemory().getFile());
        if (!Files.exists(file)) {
            return;
        }
        try {
            Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
            Map<String, Object> root = yaml.load(Files.readString(file));
            if (root == null || !root.containsKey("mappings")) {
                return;
            }
            List<Map<String, Object>> list = (List<Map<String, Object>>) root.get("mappings");
            for (Map<String, Object> map : list) {
                mappings.add(fromMap(map));
            }
            log.info("Loaded {} intent-tool mappings from {}", mappings.size(), file);
        } catch (IOException e) {
            log.error("Failed to load memory: {}", file, e);
        }
    }

    @PreDestroy
    void save() {
        if (!dirty) {
            return;
        }
        Path file = Path.of(properties.getMemory().getFile());
        try {
            Files.createDirectories(file.getParent());
            Map<String, Object> root = new LinkedHashMap<>();
            root.put("mappings", mappings.stream().map(this::toMap).toList());
            Yaml yaml = new Yaml();
            Files.writeString(file, yaml.dumpAsMap(root));
            log.info("Saved {} intent-tool mappings to {}", mappings.size(), file);
        } catch (IOException e) {
            log.error("Failed to save memory: {}", file, e);
        }
    }

    public IntentToolMapping findMatch(String input) {
        String lower = input.toLowerCase();
        return mappings.stream()
                .filter(m -> matches(m.getPattern(), lower))
                .max(Comparator.comparingDouble(IntentToolMapping::getSuccessScore)
                        .thenComparingInt(IntentToolMapping::getHitCount))
                .orElse(null);
    }

    public void recordSuccess(String pattern, List<ToolCall> tools, String note) {
        IntentToolMapping existing = mappings.stream()
                .filter(m -> m.getPattern().equalsIgnoreCase(pattern))
                .findFirst()
                .orElse(null);
        if (existing == null) {
            existing = new IntentToolMapping();
            existing.setPattern(pattern);
            existing.setTools(tools);
            existing.setNote(note);
            mappings.add(existing);
        }
        existing.setSuccessScore(Math.min(1.0, existing.getSuccessScore() + 0.5));
        existing.setHitCount(existing.getHitCount() + 1);
        existing.setLastUsed(Instant.now());
        dirty = true;
    }

    public void updateScore(String pattern, double delta) {
        mappings.stream()
                .filter(m -> m.getPattern().equalsIgnoreCase(pattern))
                .findFirst()
                .ifPresent(m -> {
                    m.setSuccessScore(Math.max(-1.0, Math.min(1.0, m.getSuccessScore() + delta)));
                    m.setLastUsed(Instant.now());
                    dirty = true;
                });
    }

    public List<IntentToolMapping> all() {
        return List.copyOf(mappings);
    }

    private boolean matches(String pattern, String input) {
        if (pattern == null || pattern.isBlank()) {
            return false;
        }
        for (String part : pattern.split("\\|")) {
            if (input.contains(part.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private IntentToolMapping fromMap(Map<String, Object> map) {
        IntentToolMapping m = new IntentToolMapping();
        m.setPattern(String.valueOf(map.get("pattern")));
        m.setHitCount(((Number) map.getOrDefault("hitCount", 0)).intValue());
        m.setSuccessScore(((Number) map.getOrDefault("successScore", 0.0)).doubleValue());
        Object lastUsed = map.get("lastUsed");
        if (lastUsed != null) {
            m.setLastUsed(Instant.parse(String.valueOf(lastUsed)));
        }
        m.setNote(String.valueOf(map.getOrDefault("note", "")));
        List<Map<String, Object>> toolList = (List<Map<String, Object>>) map.getOrDefault("tools", List.of());
        List<ToolCall> calls = new ArrayList<>();
        for (Map<String, Object> t : toolList) {
            calls.add(new ToolCall(
                    String.valueOf(t.get("name")),
                    (Map<String, String>) t.getOrDefault("args", Map.of())
            ));
        }
        m.setTools(calls);
        return m;
    }

    private Map<String, Object> toMap(IntentToolMapping m) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("pattern", m.getPattern());
        map.put("tools", m.getTools().stream().map(t -> {
            Map<String, Object> tm = new LinkedHashMap<>();
            tm.put("name", t.name());
            tm.put("args", t.args());
            return tm;
        }).toList());
        map.put("hitCount", m.getHitCount());
        map.put("successScore", m.getSuccessScore());
        map.put("lastUsed", m.getLastUsed() != null ? m.getLastUsed().toString() : null);
        map.put("note", m.getNote());
        return map;
    }
}
