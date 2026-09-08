package com.github.btpka3.first.embabel.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DwsUserSelfTool implements Tool {

    private final CommandExecutor commandExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "dws_user_self";
    }

    @Override
    public String description() {
        return "Query the current DingTalk user profile and department via dws contact user get-self.";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        String output = commandExecutor.execute("dws contact user get-self -f json --yes");
        JsonNode node = objectMapper.readTree(output);
        JsonNode result = node.path("result").get(0).path("orgEmployeeModel");
        return objectMapper.writeValueAsString(result);
    }
}
