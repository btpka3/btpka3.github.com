package me.btpka3.nativeexpr.engines;

import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;
import org.mvel2.MVEL;
import org.mvel2.optimizers.OptimizerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * MVEL 适配器。
 * 关键：强制使用 "reflective" 优化器（而非默认的 ASM 动态字节码优化器），
 * 否则 MVEL 会在运行期用 ASM 生成访问器类 —— 这在 native image 封闭世界下不可行。
 * 采用"预编译表达式 + 反射执行"，是 MVEL 的 native 安全用法。
 */
public class MvelEngine implements Engine {

    public MvelEngine() {
        // 全局关闭 ASM 动态优化器，改走反射（native 安全）
        OptimizerFactory.setDefaultOptimizer("reflective");
    }

    @Override
    public String key() { return "mvel"; }

    @Override
    public String displayName() { return "MVEL(reflective)"; }

    static final class Prepared {
        final Serializable compiled;
        Prepared(Serializable compiled) { this.compiled = compiled; }
    }

    @Override
    public Object compile(LogicalCase c) {
        String expr = c.exprByEngine.get("mvel");
        Serializable compiled = MVEL.compileExpression(expr);
        return new Prepared(compiled);
    }

    @Override
    public Object eval(Object prepared, Fact fact) {
        Prepared p = (Prepared) prepared;
        // 每次新建上下文 map：MVEL 内联 def 在复用同一 map 时会重复注册函数报 duplicate
        Map<String, Object> vars = new HashMap<>(2);
        vars.put("fact", fact);
        return MVEL.executeExpression(p.compiled, vars);
    }
}
