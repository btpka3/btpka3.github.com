package com.github.btpka3.first.embabel.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MyDeptMembersTool implements Tool {

    private final CommandExecutor commandExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "my_dept_members";
    }

    @Override
    public String description() {
        return "List members of the current user's own DingTalk department (no arguments needed).";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        String selfOutput = commandExecutor.execute("dws contact user get-self -f json --yes");
        JsonNode selfNode = objectMapper.readTree(selfOutput);
        JsonNode dept = selfNode.path("result").get(0).path("orgEmployeeModel").path("depts").get(0);
        long deptId = dept.path("deptId").asLong();
        String deptName = dept.path("deptName").asText("unknown");

        String membersOutput = commandExecutor.execute(
                "dws contact dept list-members --dept-id " + deptId + " -f json --yes");
        JsonNode membersNode = objectMapper.readTree(membersOutput);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("deptId", deptId);
        result.put("deptName", deptName);
        result.put("members", membersNode.path("deptUserList"));
        return objectMapper.writeValueAsString(result);
    }
}
