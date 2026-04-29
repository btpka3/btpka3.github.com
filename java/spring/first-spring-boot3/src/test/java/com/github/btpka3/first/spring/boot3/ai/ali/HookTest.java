package com.github.btpka3.first.spring.boot3.ai.ali;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.Hook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.HumanInTheLoopHook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.ToolConfig;
import com.alibaba.cloud.ai.graph.agent.hook.modelcalllimit.ModelCallLimitHook;
import com.alibaba.cloud.ai.graph.agent.hook.skills.SkillsAgentHook;
import com.alibaba.cloud.ai.graph.agent.hook.summarization.SummarizationHook;
import com.alibaba.cloud.ai.graph.skills.registry.SkillRegistry;
import com.alibaba.cloud.ai.graph.skills.registry.classpath.ClasspathSkillRegistry;
import com.alibaba.cloud.ai.graph.skills.registry.filesystem.FileSystemSkillRegistry;
import org.springframework.ai.chat.model.ChatModel;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class HookTest {


    public void skillDemo() {
        SkillRegistry registry1 = FileSystemSkillRegistry.builder()
                .projectSkillsDirectory(System.getProperty("user.dir") + "/skills")
                .build();
        SkillsAgentHook hook1 = SkillsAgentHook.builder()
                .skillRegistry(registry1)
                .build();

        SkillRegistry registry2 = ClasspathSkillRegistry.builder()
                .classpathPath("skills")
                .build();
        SkillsAgentHook hook2 = SkillsAgentHook.builder()
                .skillRegistry(registry1)
                .build();

        ReactAgent agent = ReactAgent.builder()
                .hooks(List.of(hook1, hook2))
                .build();
    }


    /**
     * 消息压缩
     */
    public void summarizationDemo() {
        ChatModel chatModel = null;
        SummarizationHook summarizationHook = SummarizationHook.builder()
                .model(chatModel)
                .maxTokensBeforeSummary(4000)
                .messagesToKeep(20)
                .build();
    }


    /**
     * Human-in-the-Loop（人机协同）
     */
    public Hook humanInTheLoopHookDemo() {
        return HumanInTheLoopHook.builder()
                .approvalOn("sendEmailTool", ToolConfig.builder().description("Please confirm sending the email.").build())
                .approvalOn("deleteDataTool", "")
                .build();
    }

    /**
     * 模型调用限制（Model Call Limit）
     *
     * @return
     */

    public Hook modelCallLimitHookDemo() {
        return ModelCallLimitHook.builder().runLimit(5).build();
    }


}
