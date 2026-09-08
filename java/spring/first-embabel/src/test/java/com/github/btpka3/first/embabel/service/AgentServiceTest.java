package com.github.btpka3.first.embabel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.btpka3.first.embabel.memory.ToolCall;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AgentServiceTest {

    @Test
    void shouldDeriveDistinctPatternsForIdentityAndDepartment() {
        AgentService service = new AgentService(null, null, null, null);

        assertThat(service.derivePattern("我是谁")).isEqualTo("我是谁");
        assertThat(service.derivePattern("我所在的部门是哪个？有哪些员工？"))
                .isEqualTo("我所在部门|部门员工|部门成员");
        assertThat(service.derivePattern("skill 列表")).isEqualTo("skill");
        assertThat(service.derivePattern("mcp 配置")).isEqualTo("mcp");
        assertThat(service.derivePattern("加载 ontology 本体")).isEqualTo("本体|ontology");
        assertThat(service.derivePattern("任意其他输入")).isEqualTo("任意其他输入");
    }

    @Test
    void shouldUseFinalPlanWhenReasoningContainsJsonLikeText() {
        AgentService service = new AgentService(null, null, null, new ObjectMapper());

        List<ToolCall> plan = service.parsePlanResponse("Reasoning selected {a1_whoami} before producing the plan.\n"
                + "{\"steps\":[{\"name\":\"a1_whoami\",\"args\":{}}]}");

        assertThat(plan).containsExactly(new ToolCall("a1_whoami", Map.of()));
    }

    @Test
    void shouldNotIncludeModelOutputInInvalidPlanError() {
        AgentService service = new AgentService(null, null, null, new ObjectMapper());
        String response = "private model text";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.parsePlanResponse(response))
                .withMessage("Model response did not contain a valid tool plan.");
    }
}
