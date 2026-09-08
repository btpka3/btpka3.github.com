package com.github.btpka3.first.embabel.skill;

import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillLoader {

    private final FirstEmbabelProperties properties;
    private final List<Skill> skills = new ArrayList<>();

    @PostConstruct
    void load() {
        Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        for (String dir : properties.getSkills().getPaths()) {
            Path root = Path.of(dir);
            if (!Files.exists(root)) {
                log.warn("Skill path does not exist: {}", root);
                continue;
            }
            try (Stream<Path> walk = Files.walk(root)) {
                walk.filter(p -> p.getFileName().toString().equalsIgnoreCase("SKILL.md"))
                        .forEach(path -> loadOne(path, yaml));
            } catch (IOException e) {
                log.error("Failed to walk skill path: {}", root, e);
            }
        }
        log.info("Loaded {} skills from {}", skills.size(), properties.getSkills().getPaths());
    }

    @SuppressWarnings("unchecked")
    private void loadOne(Path path, Yaml yaml) {
        try {
            String text = Files.readString(path);
            String front = "";
            String content = text;
            Map<String, Object> metadata = Collections.emptyMap();
            if (text.startsWith("---")) {
                int end = text.indexOf("---", 3);
                if (end > 0) {
                    front = text.substring(3, end).trim();
                    content = text.substring(end + 3).trim();
                    metadata = yaml.load(front);
                    if (metadata == null) {
                        metadata = Collections.emptyMap();
                    }
                }
            }
            String name = String.valueOf(metadata.getOrDefault("name", path.getParent().getFileName().toString()));
            String description = String.valueOf(metadata.getOrDefault("description", ""));
            skills.add(new Skill(name, description, metadata, content, path));
        } catch (IOException e) {
            log.error("Failed to load skill: {}", path, e);
        }
    }

    public List<Skill> all() {
        return Collections.unmodifiableList(skills);
    }

    public List<Skill> search(String keyword) {
        String lower = keyword.toLowerCase();
        return skills.stream()
                .filter(s -> s.name().toLowerCase().contains(lower)
                        || s.description().toLowerCase().contains(lower)
                        || s.content().toLowerCase().contains(lower))
                .toList();
    }

    public Skill findByName(String name) {
        return skills.stream()
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
