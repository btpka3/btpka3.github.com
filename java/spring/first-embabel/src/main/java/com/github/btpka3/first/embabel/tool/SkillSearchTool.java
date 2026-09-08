package com.github.btpka3.first.embabel.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.btpka3.first.embabel.skill.Skill;
import com.github.btpka3.first.embabel.skill.SkillLoader;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SkillSearchTool implements Tool {

    private final SkillLoader skillLoader;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "skill_search";
    }

    @Override
    public String description() {
        return "Search loaded skills by keyword. Args: keyword.";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        String keyword = args.getOrDefault("keyword", "");
        var results = skillLoader.search(keyword).stream()
                .map(s -> new SkillSummary(s.name(), s.description(), s.path().toString()))
                .collect(Collectors.toList());
        return objectMapper.writeValueAsString(results);
    }

    public record SkillSummary(String name, String description, String path) {
    }
}
