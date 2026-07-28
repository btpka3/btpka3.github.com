package me.btpka3.nativeexpr.engines;

import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

/**
 * GraalJS 适配器（Truffle 客体语言）。
 * 统一把用例包成 IIFE：(function(fact){ return <expr>; })，
 * 这样"纯表达式"与"函数定义+调用"都能编译成一个可重复执行的 Value。
 * 热路径仅做 Value.execute(fact)，让 Truffle 对客体代码做 partial-evaluation JIT。
 *
 * HostAccess.ALL：允许 guest 访问宿主 public 字段与 public 方法
 * （Fact 的 public 字段 + getter + riskFactor()）。
 * native 下宿主成员访问需 native-image agent 采样补齐反射/字段配置。
 */
public class GraalJsEngine implements Engine {

    private final Context ctx;

    public GraalJsEngine() {
        this.ctx = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(cn -> false)
                .build();
    }

    @Override
    public String key() { return "js"; }

    @Override
    public String displayName() { return "GraalJS(Truffle)"; }

    @Override
    public Object compile(LogicalCase c) {
        String expr = c.exprByEngine.get("js");
        String wrapped = "(function(fact){ return " + expr + "; })";
        // 编译为可执行 Value；Context 关闭前一直有效
        return ctx.eval("js", wrapped);
    }

    @Override
    public Object eval(Object prepared, Fact fact) {
        Value fn = (Value) prepared;
        Value r = fn.execute(fact);
        if (r.isBoolean()) return r.asBoolean();
        if (r.isNumber()) return r.asDouble();
        if (r.isString()) return r.asString();
        return r.toString();
    }

    @Override
    public void close() {
        ctx.close(true);
    }
}
