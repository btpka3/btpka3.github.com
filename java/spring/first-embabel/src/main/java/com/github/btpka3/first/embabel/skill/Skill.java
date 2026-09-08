package com.github.btpka3.first.embabel.skill;

import java.nio.file.Path;
import java.util.Map;

public record Skill(
        String name,
        String description,
        Map<String, Object> metadata,
        String content,
        Path path
) {
}
