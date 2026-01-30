package com.github.btpka3.first.spring.boot3.ai;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/17
 * @see <a href="https://modelcontextprotocol.io/docs/develop/build-server#java">Build an MCP server</a>
 */
public class DemoMcpServer {


    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

    @Service
    public static class WeatherService {

        @Tool(description = "Get weather forecast for a specific latitude/longitude")
        public String getWeatherForecastByLocation(
                double latitude,   // Latitude coordinate
                double longitude   // Longitude coordinate
        ) {
            // Returns detailed forecast including:
            // - Temperature and unit
            // - Wind speed and direction
            // - Detailed forecast description
            return null;
        }

        @Tool(description = "Get weather alerts for a US state")
        public String getAlerts(
                @ToolParam(description = "Two-letter US state code (e.g. CA, NY)") String state
        ) {
            // Returns active alerts including:
            // - Event type
            // - Affected area
            // - Severity
            // - Description
            // - Safety instructions
            return null;
        }
    }
}
