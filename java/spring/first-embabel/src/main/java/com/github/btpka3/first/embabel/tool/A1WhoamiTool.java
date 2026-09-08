package com.github.btpka3.first.embabel.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class A1WhoamiTool implements Tool {

    private final CommandExecutor commandExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "a1_whoami";
    }

    @Override
    public String description() {
        return "Query the current logged-in Aone user: account, name, email, emp_id, nickname.";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        String output = commandExecutor.execute("a1 auth whoami -f json");
        JsonNode node = objectMapper.readTree(output);
        return objectMapper.writeValueAsString(node);
    }
}
