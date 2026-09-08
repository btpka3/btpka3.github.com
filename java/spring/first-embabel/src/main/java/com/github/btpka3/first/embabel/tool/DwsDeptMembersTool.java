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
public class DwsDeptMembersTool implements Tool {

    private final CommandExecutor commandExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "dws_dept_members";
    }

    @Override
    public String description() {
        return "List members of the current user's DingTalk department. Optionally args: query (department keyword), deptId (direct department id).";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        long deptId;
        String deptName = null;
        String deptIdArg = args.get("deptId");
        if (deptIdArg != null && !deptIdArg.isBlank()) {
            deptId = Long.parseLong(deptIdArg.trim());
        } else {
            String query = args.getOrDefault("query", null);
            String searchOutput = commandExecutor.execute(
                    "dws contact dept search --query \"" + (query != null ? query : "九宫") + "\" -f json --yes");
            JsonNode searchNode = objectMapper.readTree(searchOutput);
            JsonNode deptList = searchNode.path("deptList");
            if (!deptList.isArray() || deptList.isEmpty()) {
                return "{\"error\":\"no department found\"}";
            }
            deptId = deptList.get(0).path("deptId").asLong();
            deptName = deptList.get(0).path("deptName").asText(null);
        }

        String membersOutput = commandExecutor.execute(
                "dws contact dept list-members --dept-id " + deptId + " -f json --yes");
        JsonNode membersNode = objectMapper.readTree(membersOutput);
        JsonNode deptUserList = membersNode.path("deptUserList");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("deptId", deptId);
        if (deptName != null) {
            result.put("deptName", deptName);
        }
        result.put("members", deptUserList);
        return objectMapper.writeValueAsString(result);
    }
}
