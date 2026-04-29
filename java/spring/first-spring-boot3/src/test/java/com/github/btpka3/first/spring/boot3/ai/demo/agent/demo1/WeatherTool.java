package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 天气查询工具
 * 
 * 演示如何创建 Spring AI 的 Function Calling 工具
 */
@Component
public class WeatherTool {

    // 模拟天气数据（实际应用中应调用天气 API）
    private static final Map<String, WeatherData> MOCK_WEATHER = new ConcurrentHashMap<>();

    static {
        MOCK_WEATHER.put("北京", new WeatherData("北京", "晴", 22, 12, 45));
        MOCK_WEATHER.put("上海", new WeatherData("上海", "多云", 20, 15, 65));
        MOCK_WEATHER.put("广州", new WeatherData("广州", "小雨", 28, 22, 80));
        MOCK_WEATHER.put("深圳", new WeatherData("深圳", "晴", 30, 24, 70));
        MOCK_WEATHER.put("杭州", new WeatherData("杭州", "阴", 21, 16, 60));
    }

    /**
     * 查询天气
     * 
     * @param city 城市名称
     * @return 天气信息
     */
    @Tool(description = "查询指定城市的当前天气信息，包括温度、湿度和天气状况")
    public String getWeather(
            @ToolParam(description = "要查询的城市名称，如：北京、上海、广州、深圳、杭州") 
            String city) {
        
        WeatherData data = MOCK_WEATHER.get(city);
        if (data == null) {
            return "抱歉，暂未找到 %s 的天气信息。支持的城市：北京、上海、广州、深圳、杭州".formatted(city);
        }

        return """
                🌤️ %s 天气
                ━━━━━━━━━━━━━━━
                天气状况：%s
                当前温度：%d°C
                最低温度：%d°C
                湿度：%d%%
                更新时间：%s
                """.formatted(
                data.city(),
                data.condition(),
                data.temperature(),
                data.minTemp(),
                data.humidity(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    /**
     * 获取支持的城市列表
     */
    @Tool(description = "获取支持天气查询的城市列表")
    public String getSupportedCities() {
        return "支持的城市列表：北京、上海、广州、深圳、杭州";
    }

    /**
     * 天气数据记录
     */
    private record WeatherData(
            String city,
            String condition,
            int temperature,
            int minTemp,
            int humidity
    ) {}
}
