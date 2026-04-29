package com.github.btpka3.first.spring.boot3.ai.demo.agent.demo1;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算器工具
 * 
 * 演示复杂计算和参数验证的工具实现
 */
@Component
public class CalculatorTool {

    private final List<CalculationRecord> history = new ArrayList<>();

    /**
     * 执行基础数学运算
     */
    @Tool(description = "执行数学运算，支持加减乘除、幂运算、开方等。返回精确计算结果。")
    public String calculate(
            @ToolParam(description = "第一个操作数") double operand1,
            @ToolParam(description = "运算符：add(加), subtract(减), multiply(乘), divide(除), power(幂), sqrt(开方)") String operation,
            @ToolParam(description = "第二个操作数（sqrt 运算时可选）", required = false) double operand2) {
        
        try {
            BigDecimal num1 = BigDecimal.valueOf(operand1);
            BigDecimal result;
            String opSymbol;

            switch (operation.toLowerCase()) {
                case "add", "+" -> {
                    result = num1.add(BigDecimal.valueOf(operand2));
                    opSymbol = "+";
                }
                case "subtract", "-" -> {
                    result = num1.subtract(BigDecimal.valueOf(operand2));
                    opSymbol = "-";
                }
                case "multiply", "*" -> {
                    result = num1.multiply(BigDecimal.valueOf(operand2));
                    opSymbol = "×";
                }
                case "divide", "/" -> {
                    if (operand2 == 0) {
                        return "❌ 错误：除数不能为零";
                    }
                    result = num1.divide(BigDecimal.valueOf(operand2), 10, RoundingMode.HALF_UP);
                    opSymbol = "÷";
                }
                case "power", "^" -> {
                    result = BigDecimal.valueOf(Math.pow(operand1, operand2));
                    opSymbol = "^";
                }
                case "sqrt", "√" -> {
                    if (operand1 < 0) {
                        return "❌ 错误：负数不能开平方";
                    }
                    result = BigDecimal.valueOf(Math.sqrt(operand1));
                    opSymbol = "√";
                    operand2 = 0; // Mark as single-operand operation
                }
                default -> {
                    return "❌ 错误：不支持的运算符 '%s'。支持的运算符：add(+), subtract(-), multiply(*), divide(/), power(^), sqrt(√)".formatted(operation);
                }
            }

            // 去除末尾多余的零
            result = result.stripTrailingZeros();

            // 记录历史
            String expression = operand2 == 0 && operation.equals("sqrt")
                    ? "%s(%s)".formatted(opSymbol, operand1)
                    : "%s %s %s".formatted(operand1, opSymbol, operand2);
            
            history.add(new CalculationRecord(expression, result.toPlainString()));

            return "✅ 计算结果\n" +
                   "━━━━━━━━━━━━━━━\n" +
                   "表达式：%s\n" +
                   "结果：%s".formatted(expression, result.toPlainString());

        } catch (Exception e) {
            return "❌ 计算错误：%s".formatted(e.getMessage());
        }
    }

    /**
     * 获取计算历史
     */
    @Tool(description = "获取最近的计算历史记录")
    public String getCalculationHistory(
            @ToolParam(description = "返回的记录数量，默认 5", required = false) int limit) {
        
        if (limit <= 0) limit = 5;
        if (history.isEmpty()) {
            return "暂无计算历史记录";
        }

        StringBuilder sb = new StringBuilder("📊 计算历史\n━━━━━━━━━━━━━━━\n");
        int start = Math.max(0, history.size() - limit);
        for (int i = start; i < history.size(); i++) {
            var record = history.get(i);
            sb.append("%d. %s = %s\n".formatted(i + 1, record.expression(), record.result()));
        }
        
        return sb.toString();
    }

    /**
     * 清空计算历史
     */
    @Tool(description = "清空所有计算历史记录")
    public String clearHistory() {
        history.clear();
        return "✅ 计算历史已清空";
    }

    private record CalculationRecord(String expression, String result) {}
}
