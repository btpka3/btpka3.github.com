package com.github.btpka3.first.embabel.memory;

import java.util.Map;

public record ToolCall(
        String name,
        Map<String, String> args
) {
}
