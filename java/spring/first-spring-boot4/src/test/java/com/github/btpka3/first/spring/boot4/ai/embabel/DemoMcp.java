package com.github.btpka3.first.spring.boot4.ai.embabel;

import com.embabel.agent.api.annotation.LlmTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */
@Component
public class DemoMcp {


    @Tool(description = "Calculate the customer's loyalty discount percentage")
    public BigDecimal getLoyaltyDiscount() {
        return BigDecimal.valueOf(1L);
    }

    @LlmTool(description = "add two numbers")
    public double add(double a, double b) {
        return a + b;
    }
}
