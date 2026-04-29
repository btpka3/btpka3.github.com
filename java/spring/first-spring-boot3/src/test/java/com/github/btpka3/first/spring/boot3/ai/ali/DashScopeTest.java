package com.github.btpka3.first.spring.boot3.ai.ali;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.Hook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.HumanInTheLoopHook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.ToolConfig;
import com.alibaba.cloud.ai.graph.agent.hook.skills.SkillsAgentHook;
import com.alibaba.cloud.ai.graph.agent.hook.summarization.SummarizationHook;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
//import dev.langchain4j.model.openai.OpenAiChatModel;
import com.alibaba.cloud.ai.graph.skills.registry.SkillRegistry;
import com.alibaba.cloud.ai.graph.skills.registry.classpath.ClasspathSkillRegistry;
import com.alibaba.cloud.ai.graph.skills.registry.filesystem.FileSystemSkillRegistry;
import com.github.btpka3.first.spring.boot3.U;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class DashScopeTest {


    ChatModel getOpenAiChatModel() {
        String sysPropKeyOpenApiKey = "OPENAI_API_KEY";
        String openApiKey = System.getenv(sysPropKeyOpenApiKey);
        Assertions.assertTrue(StringUtils.isNotBlank(openApiKey), "java system env key=" + sysPropKeyOpenApiKey + " is required.");
        System.out.println("using open api key = " + U.maskPassword(openApiKey));

        String sysPropKeyOpenApiBaseUrl = "OPENAI_BASE_URL";
        String openApiUrl = System.getenv(sysPropKeyOpenApiBaseUrl);
        Assertions.assertTrue(StringUtils.isNotBlank(openApiUrl), "java system env key=" + sysPropKeyOpenApiBaseUrl + " is required.");
        System.out.println("using open api url = " + openApiUrl);

        String sysPropKeyOpenApiModelName = "MODEL_NAME";
        String modelName = System.getenv(sysPropKeyOpenApiModelName);
        Assertions.assertTrue(StringUtils.isNotBlank(openApiKey), "java system env key=" + sysPropKeyOpenApiModelName + " is required.");
        System.out.println("using open api modelName = " + modelName);

        return OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .apiKey(openApiKey)
                        .baseUrl(openApiUrl)
                        .build()
                )
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(modelName)
                        .temperature(0.5)
                        .maxTokens(1000)
                        .build())
                .build();
    }

    ChatModel getDashScopeChatModel() {
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .build();


        // 配置模型
        return DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder()
                        // Note: model must be set when use options build.
                        .withModel(DashScopeChatModel.DEFAULT_MODEL_NAME)
                        .withTemperature(0.5)
                        .withMaxToken(1000)
                        .build())
                .build();

    }

    @Test
    @SneakyThrows
    public void x() {

        // 定义系统提示
        String SYSTEM_PROMPT = """
                You are an expert weather forecaster, who speaks in puns.
                
                You have access to two tools:
                
                - get_weather_for_location: use this to get the weather for a specific location
                - get_user_location: use this to get the user's location
                
                If a user asks you for the weather, make sure you know the location.
                If you can tell from the question that they mean wherever they are,
                use the get_user_location tool to find their location.
                """;


        // 创建工具
        ToolCallback weatherTool = FunctionToolCallback.builder("get_weather", new WeatherTool())
                .description("Get weather for a given city")
                .inputType(WeatherToolParam.class)
                .build();

        // 创建 hook
        Hook humanInTheLoopHook = HumanInTheLoopHook.builder()
                .approvalOn("getWeatherTool", ToolConfig.builder().description("Please confirm tool execution.")
                        .build())
                .build();

        String customSchema = """
                请按照以下JSON格式输出：
                {
                    "title": "标题",
                    "content": "内容",
                    "style": "风格"
                }
                """;

        ChatModel chatModel = getOpenAiChatModel();
        ReactAgent agent = ReactAgent.builder()
                .name("weather_agent")
                .systemPrompt(SYSTEM_PROMPT)
                .model(chatModel)
                .tools(weatherTool)
                .hooks(humanInTheLoopHook)
                // 定义响应格式
//                .outputType(ResponseFormat.class)
//                .outputSchema(customSchema)
                .systemPrompt("You are a helpful assistant")
                // 添加记忆
                .saver(new MemorySaver())
                .build();

        AssistantMessage response = agent.call("广州的天气如何？");
        System.out.println(response.getText());
        System.out.println("======== done.");
    }

    @Data
    public static class WeatherToolParam {
        String city;
    }

    @Slf4j
    public static class WeatherTool implements BiFunction<WeatherToolParam, ToolContext, String> {
        @Override
        public String apply(WeatherToolParam param, ToolContext toolContext) {
            log.info("WeatherTool#apply invoked param={}", param);
            return param.getCity() + " 的天气是 特大沙尘暴 !";
        }
    }

    public static class ResponseFormat {
        // 一个双关语响应（始终必需）
        private String punnyResponse;

        // 如果可用的话，关于天气的任何有趣信息
        private String weatherConditions;
    }


    /**
     * 获取完整状态
     *
     * @param agent
     */
    @SneakyThrows
    public void x(ReactAgent agent) {
        String threadId = "thread_123";
        RunnableConfig runnableConfig = RunnableConfig.builder()
                .threadId(threadId)
                .addMetadata("key", "value")
                .build();


        Optional<OverAllState> result = agent.invoke("帮我写一首诗", runnableConfig);

        if (result.isPresent()) {
            OverAllState state = result.get();

            // 访问消息历史
            Optional<List<Message>> messages = state.value("messages");
            List<Message> messageList = messages.get();

            // 访问自定义状态
            Optional<Object> customData = state.value("custom_key");

            System.out.println("完整状态：" + state);
        }
    }



}
