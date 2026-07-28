package me.btpka3.nativeexpr.engines;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.InstructionSet;
import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;

/**
 * QLExpress 适配器。
 * QLExpress 将脚本解析为指令集（InstructionSet）后解释执行，不在运行期生成 JVM 类，
 * 因此对 native image 相对友好；但属性/方法访问走反射，需要 native-image agent 采样补齐反射配置。
 */
public class QlExpressEngine implements Engine {

    private final ExpressRunner runner = new ExpressRunner(false, false);

    public QlExpressEngine() {
        try {
            // QLExpress 默认不开放任意 bean 方法调用，注册一个集合包含判断函数
            runner.addFunction("contains", new com.ql.util.express.Operator() {
                @Override
                public Object executeInner(Object[] args) {
                    return args[0] instanceof java.util.Collection<?> col && col.contains(args[1]);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("register contains() failed", e);
        }
    }

    @Override
    public String key() { return "ql"; }

    @Override
    public String displayName() { return "QLExpress"; }

    static final class Prepared {
        final InstructionSet is;
        final DefaultContext<String, Object> ctx = new DefaultContext<>();
        Prepared(InstructionSet is) { this.is = is; }
    }

    @Override
    public Object compile(LogicalCase c) throws Exception {
        String expr = c.exprByEngine.get("ql");
        InstructionSet is = runner.parseInstructionSet(expr);
        return new Prepared(is);
    }

    @Override
    public Object eval(Object prepared, Fact fact) throws Exception {
        Prepared p = (Prepared) prepared;
        p.ctx.put("fact", fact);
        return runner.execute(p.is, p.ctx, null, false, false);
    }
}
