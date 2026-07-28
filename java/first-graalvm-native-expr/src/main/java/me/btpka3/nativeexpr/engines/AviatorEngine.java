package me.btpka3.nativeexpr.engines;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.EvalMode;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aviator 适配器。
 * 关键：使用 EvalMode.INTERPRETER（解释模式），避免默认 ASM 编译模式在运行期生成字节码类——
 * 那在 native image 下不可行。解释模式为 native 安全用法。
 * 注册两个自定义宿主函数：listContains / riskFactor（Aviator 默认不开放任意 bean 方法调用）。
 */
public class AviatorEngine implements Engine {

    private final AviatorEvaluatorInstance inst;

    public AviatorEngine() {
        this.inst = AviatorEvaluator.newInstance(EvalMode.INTERPRETER);
        this.inst.addFunction(new AbstractFunction() {
            @Override public String getName() { return "listContains"; }
            @Override
            public AviatorObject call(Map<String, Object> env, AviatorObject a1, AviatorObject a2) {
                List<?> list = (List<?>) a1.getValue(env);
                Object e = a2.getValue(env);
                return AviatorBoolean.valueOf(list != null && list.contains(e));
            }
        });
        this.inst.addFunction(new AbstractFunction() {
            @Override public String getName() { return "riskFactor"; }
            @Override
            public AviatorObject call(Map<String, Object> env, AviatorObject a1) {
                Fact f = (Fact) a1.getValue(env);
                return AviatorRuntimeJavaType.valueOf(f.riskFactor());
            }
        });
    }

    @Override
    public String key() { return "aviator"; }

    @Override
    public String displayName() { return "Aviator(interpreter)"; }

    static final class Prepared {
        final Expression expr;
        final Map<String, Object> env = new HashMap<>(2);
        Prepared(Expression expr) { this.expr = expr; }
    }

    @Override
    public Object compile(LogicalCase c) {
        String src = c.exprByEngine.get("aviator");
        Expression e = inst.compile(src, true);
        return new Prepared(e);
    }

    @Override
    public Object eval(Object prepared, Fact fact) {
        Prepared p = (Prepared) prepared;
        p.env.put("fact", fact);
        return p.expr.execute(p.env);
    }
}
