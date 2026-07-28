package me.btpka3.nativeexpr;

import java.util.Map;

/**
 * 一个"逻辑用例"：同一业务语义，在不同引擎里用各自的语法表达。
 * exprByEngine 的 key 为引擎标识：mvel / ql / aviator / js。
 * 纯 Java 基线由 PureJavaEngine 按 id 硬编码实现（作为正确性 oracle 与性能天花板）。
 */
public final class LogicalCase {
    public final String id;
    public final String category;
    public final String description;
    public final ResultType type;
    /** 是否用到"函数定义"能力（用户提出的关键诉求之一）。 */
    public final boolean usesFunctionDef;
    public final Map<String, String> exprByEngine;

    public LogicalCase(String id, String category, String description, ResultType type,
                       boolean usesFunctionDef, Map<String, String> exprByEngine) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.type = type;
        this.usesFunctionDef = usesFunctionDef;
        this.exprByEngine = exprByEngine;
    }
}
