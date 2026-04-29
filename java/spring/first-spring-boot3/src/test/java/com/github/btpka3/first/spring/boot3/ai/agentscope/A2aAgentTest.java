package com.github.btpka3.first.spring.boot3.ai.agentscope;

import io.agentscope.core.a2a.agent.A2aAgent;
import io.agentscope.core.a2a.agent.card.WellKnownAgentCardResolver;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.MsgRole;

import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/21
 */
public class A2aAgentTest {

    public void x() {
        A2aAgent agent = A2aAgent.builder()
                .name("remote-agent")
                .agentCardResolver(WellKnownAgentCardResolver.builder()
                        .baseUrl("http://127.0.0.1:8080")
                        .relativeCardPath("/.well-known/agent-card.json")
                        .authHeaders(Map.of())
                        .build()
                )
                .build();

        // 调用远程 Agent
        Msg userMsg = Msg.builder()
                .name("name")
                .id("id")
                .role(MsgRole.USER)
                .textContent("xxx")
                .build();
        Msg response = agent.call(userMsg).block();
    }
}
