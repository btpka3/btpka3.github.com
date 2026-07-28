package me.btpka3.nativeexpr.engines;

import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;

import java.util.function.Function;

/**
 * 纯 Java 手写基线：性能天花板 + 正确性 oracle。
 * 直接编译进 native image，无任何运行期动态性。
 */
public class PureJavaEngine implements Engine {

    @Override
    public String key() { return "java"; }

    @Override
    public String displayName() { return "PureJava(baseline)"; }

    @Override
    public Object compile(LogicalCase c) {
        Function<Fact, Object> fn = switch (c.id) {
            case "arith_cmp"      -> (Fact f) -> f.amount > 1000;
            case "bool_and"       -> (Fact f) -> f.amount > 1000 && f.age < 25;
            case "bool_mix"       -> (Fact f) -> (f.amount > 1000 && f.age < 25) || f.deviceScore >= 0.9;
            case "string_op"      -> (Fact f) -> f.userId.startsWith("VIP") && f.city.equals("Beijing");
            case "weighted_score" -> (Fact f) -> f.amount * 0.3 + f.deviceScore * 700;
            case "nested"         -> (Fact f) -> f.history.count > 3 && f.history.totalAmount > 5000;
            case "coll_contains"  -> (Fact f) -> f.tags.contains("risky");
            case "ternary"        -> (Fact f) -> f.amount > 1000 ? "HIGH" : "LOW";
            case "func_def"       -> (Fact f) -> score(f.amount, f.deviceScore);
            case "method_call"    -> (Fact f) -> f.riskFactor();
            default -> throw new IllegalArgumentException("unknown case: " + c.id);
        };
        return fn;
    }

    private static double score(double a, double b) {
        return a * 0.3 + b * 700;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object eval(Object prepared, Fact fact) {
        return ((Function<Fact, Object>) prepared).apply(fact);
    }
}
