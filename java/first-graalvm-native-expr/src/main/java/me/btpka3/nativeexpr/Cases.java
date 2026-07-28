package me.btpka3.nativeexpr;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用例集：覆盖风控常见的表达式/脚本形态。
 * 变量统一绑定为 "fact"（Fact 实例）。
 */
public final class Cases {

    private Cases() {}

    private static Map<String, String> m(String mvel, String ql, String aviator, String js) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("mvel", mvel);
        map.put("ql", ql);
        map.put("aviator", aviator);
        map.put("js", js);
        return map;
    }

    public static List<LogicalCase> all() {
        List<LogicalCase> cs = new ArrayList<>();

        cs.add(new LogicalCase("arith_cmp", "算术比较", "amount > 1000", ResultType.BOOL, false,
                m("fact.amount > 1000",
                  "fact.amount > 1000",
                  "fact.amount > 1000",
                  "fact.amount > 1000")));

        cs.add(new LogicalCase("bool_and", "布尔与", "amount>1000 && age<25", ResultType.BOOL, false,
                m("fact.amount > 1000 && fact.age < 25",
                  "fact.amount > 1000 && fact.age < 25",
                  "fact.amount > 1000 && fact.age < 25",
                  "fact.amount > 1000 && fact.age < 25")));

        cs.add(new LogicalCase("bool_mix", "布尔混合", "(amount>1000 && age<25) || deviceScore>=0.9",
                ResultType.BOOL, false,
                m("(fact.amount > 1000 && fact.age < 25) || fact.deviceScore >= 0.9",
                  "(fact.amount > 1000 && fact.age < 25) || fact.deviceScore >= 0.9",
                  "(fact.amount > 1000 && fact.age < 25) || fact.deviceScore >= 0.9",
                  "(fact.amount > 1000 && fact.age < 25) || fact.deviceScore >= 0.9")));

        cs.add(new LogicalCase("string_op", "字符串操作", "userId.startsWith(VIP) && city==Beijing",
                ResultType.BOOL, false,
                m("fact.userId.startsWith(\"VIP\") && fact.city.equals(\"Beijing\")",
                  "fact.userId.startsWith(\"VIP\") && fact.city.equals(\"Beijing\")",
                  "string.startsWith(fact.userId, 'VIP') && fact.city == 'Beijing'",
                  "(''+fact.userId).startsWith('VIP') && (''+fact.city) === 'Beijing'")));

        cs.add(new LogicalCase("weighted_score", "加权计算", "amount*0.3 + deviceScore*700",
                ResultType.DOUBLE, false,
                m("fact.amount * 0.3 + fact.deviceScore * 700",
                  "fact.amount * 0.3 + fact.deviceScore * 700",
                  "fact.amount * 0.3 + fact.deviceScore * 700",
                  "fact.amount * 0.3 + fact.deviceScore * 700")));

        cs.add(new LogicalCase("nested", "嵌套属性", "history.count>3 && history.totalAmount>5000",
                ResultType.BOOL, false,
                m("fact.history.count > 3 && fact.history.totalAmount > 5000",
                  "fact.history.count > 3 && fact.history.totalAmount > 5000",
                  "fact.history.count > 3 && fact.history.totalAmount > 5000",
                  "fact.history.count > 3 && fact.history.totalAmount > 5000")));

        cs.add(new LogicalCase("coll_contains", "集合包含", "tags contains 'risky'", ResultType.BOOL, false,
                m("fact.tags contains \"risky\"",
                  "contains(fact.tags, \"risky\")",
                  "listContains(fact.tags, 'risky')",
                  "fact.tags.contains('risky')")));

        cs.add(new LogicalCase("ternary", "三元表达式", "amount>1000 ? HIGH : LOW", ResultType.STRING, false,
                m("fact.amount > 1000 ? \"HIGH\" : \"LOW\"",
                  "fact.amount > 1000 ? \"HIGH\" : \"LOW\"",
                  "fact.amount > 1000 ? 'HIGH' : 'LOW'",
                  "fact.amount > 1000 ? 'HIGH' : 'LOW'")));

        cs.add(new LogicalCase("func_def", "函数定义", "定义 score(a,b)=a*0.3+b*700 并调用",
                ResultType.DOUBLE, true,
                m("def score_mvel(a, b) { a * 0.3 + b * 700 }; score_mvel(fact.amount, fact.deviceScore)",
                  "function score(double a, double b){ return a * 0.3 + b * 700; }; score(fact.amount, fact.deviceScore);",
                  "let score = lambda(a, b) -> a * 0.3 + b * 700 end; score(fact.amount, fact.deviceScore)",
                  "(function(){ function score(a, b){ return a * 0.3 + b * 700; } return score(fact.amount, fact.deviceScore); })()")));

        cs.add(new LogicalCase("method_call", "宿主方法调用", "fact.riskFactor()", ResultType.DOUBLE, false,
                m("fact.riskFactor()",
                  "fact.riskFactor()",
                  "riskFactor(fact)",
                  "fact.riskFactor()")));

        return cs;
    }
}
