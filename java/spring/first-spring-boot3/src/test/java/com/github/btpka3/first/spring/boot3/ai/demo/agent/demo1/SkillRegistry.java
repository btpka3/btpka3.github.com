package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Skill 注册中心
 * 
 * 管理所有可用技能（工具）的注册和发现。
 * 
 * Skill 与 Tool 的区别：
 * - Tool: 单一功能函数（如查询天气）
 * - Skill: 组合多个 Tool 和 Prompt 的复杂能力（如代码审查流程）
 */
@Component
public class SkillRegistry {

    private final Map<String, Skill> skills = new ConcurrentHashMap<>();

    private final WeatherTool weatherTool;
    private final SearchTool searchTool;
    private final CalculatorTool calculatorTool;

    public SkillRegistry(WeatherTool weatherTool, SearchTool searchTool, CalculatorTool calculatorTool) {
        this.weatherTool = weatherTool;
        this.searchTool = searchTool;
        this.calculatorTool = calculatorTool;
        
        registerDefaultSkills();
    }

    /**
     * 注册默认技能
     */
    private void registerDefaultSkills() {
        // 天气查询技能
        register(new Skill(
                "weather",
                "天气查询",
                "查询城市天气信息，包括温度、湿度、天气状况",
                Arrays.asList("getWeather", "getSupportedCities"),
                List.of("天气", "气温", "湿度", "城市")
        ));

        // 计算技能
        register(new Skill(
                "calculator",
                "数学计算",
                "执行精确数学运算，支持加减乘除、幂运算、开方等",
                Arrays.asList("calculate", "getCalculationHistory", "clearHistory"),
                List.of("计算", "数学", "运算")
        ));

        // 搜索技能
        register(new Skill(
                "search",
                "信息搜索",
                "搜索互联网上的信息，支持通用搜索、新闻、技术文档等",
                Arrays.asList("search", "fetchDocumentContent"),
                List.of("搜索", "查询", "信息")
        ));

        // 代码审查技能（组合技能示例）
        register(new Skill(
                "code_review",
                "代码审查",
                "执行代码审查流程：检查代码质量、安全问题、性能优化建议",
                List.of("analyzeCode", "checkSecurity", "suggestOptimizations"),
                List.of("代码", "审查", "review", "quality")
        ));
    }

    /**
     * 注册技能
     */
    public void register(Skill skill) {
        skills.put(skill.id(), skill);
    }

    /**
     * 根据 ID 获取技能
     */
    public Skill getSkill(String skillId) {
        return skills.get(skillId);
    }

    /**
     * 获取所有技能列表
     */
    public List<Skill> getAllSkills() {
        return List.copyOf(skills.values());
    }

    /**
     * 根据关键词搜索技能
     */
    public List<Skill> searchSkills(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return skills.values().stream()
                .filter(skill -> 
                    skill.name().toLowerCase().contains(lowerKeyword) ||
                    skill.description().toLowerCase().contains(lowerKeyword) ||
                    skill.keywords().stream().anyMatch(k -> k.toLowerCase().contains(lowerKeyword))
                )
                .toList();
    }

    /**
     * 获取 WeatherTool 的 Function 名称（用于注册到 ChatClient）
     */
    public String getWeatherToolFunction() {
        return "getWeather";
    }

    /**
     * 获取 SearchTool 的 Function 名称
     */
    public String getSearchToolFunction() {
        return "search";
    }

    /**
     * 获取 CalculatorTool 的 Function 名称
     */
    public String getCalculatorToolFunction() {
        return "calculate";
    }

    /**
     * 技能定义记录
     */
    public static record Skill(
            String id,              // 技能唯一标识
            String name,            // 技能名称
            String description,     // 技能描述
            List<String> tools,     // 使用的工具列表
            List<String> keywords   // 关键词（用于匹配用户意图）
    ) {
        /**
         * 生成技能的系统提示词
         */
        public String generateSystemPrompt() {
            return """
                    你正在使用技能：%s
                    描述：%s
                    可用工具：%s
                    
                    当用户请求与该技能相关时，请调用相应的工具来完成任务。
                    如果工具调用失败，请向用户解释原因并提供替代方案。
                    """.formatted(name, description, String.join(", ", tools));
        }
    }
}
