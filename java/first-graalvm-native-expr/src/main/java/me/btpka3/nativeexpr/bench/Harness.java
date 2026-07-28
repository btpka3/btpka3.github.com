package me.btpka3.nativeexpr.bench;

import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;
import me.btpka3.nativeexpr.ResultType;
import me.btpka3.nativeexpr.engines.PureJavaEngine;

import java.util.List;

/**
 * 自计时基准 harness：同一份代码在 JVM(HotSpot JIT) 与 native image 下运行，
 * 度量编译耗时、正确性、预热曲线、稳态吞吐。
 */
public final class Harness {

    /** 防死代码消除的累加器。 */
    static volatile long sink;

    public static final class Config {
        public String mode;
        public int warmupRounds = 12;
        public long warmupRoundMs = 200;
        public long steadyMs = 2000;
        public Fact[] samples = Fact.samples();
    }

    private final Config cfg;
    private final Engine oracle = new PureJavaEngine();

    public Harness(Config cfg) {
        this.cfg = cfg;
    }

    public BenchResult measure(Engine engine, LogicalCase c) {
        BenchResult r = new BenchResult();
        r.mode = cfg.mode;
        r.engine = engine.key();
        r.caseId = c.id;
        r.category = c.category;
        r.usesFunctionDef = c.usesFunctionDef;

        // 1) 编译
        Object prepared;
        long t0 = System.nanoTime();
        try {
            prepared = engine.compile(c);
        } catch (Throwable e) {
            r.supported = false;
            r.error = shortErr(e);
            return r;
        }
        r.compileNs = System.nanoTime() - t0;

        // 2) 正确性（与 PureJava oracle 比对）
        try {
            r.correct = verify(engine, prepared, c);
        } catch (Throwable e) {
            r.supported = false;
            r.error = "verify:" + shortErr(e);
            return r;
        }
        if (!r.correct) {
            r.error = "result mismatch vs oracle";
            // 仍继续测吞吐，但标记不正确
        }

        // 3) 预热曲线
        for (int i = 0; i < cfg.warmupRounds; i++) {
            double ops = runFor(engine, prepared, cfg.warmupRoundMs);
            r.warmupCurve.add(ops);
        }

        // 4) 稳态吞吐
        long steadyOps = runForOps(engine, prepared, cfg.steadyMs);
        r.steadyOps = steadyOps;
        r.steadyOpsPerSec = steadyOps / (cfg.steadyMs / 1000.0);
        r.steadyNsPerOp = steadyOps > 0 ? (cfg.steadyMs * 1_000_000.0 / steadyOps) : -1;
        return r;
    }

    private boolean verify(Engine engine, Object prepared, LogicalCase c) throws Exception {
        Object oraclePrepared = oracle.compile(c);
        for (Fact f : cfg.samples) {
            Object expected = oracle.eval(oraclePrepared, f);
            Object actual = engine.eval(prepared, f);
            if (!same(c.type, expected, actual)) {
                return false;
            }
        }
        return true;
    }

    private static boolean same(ResultType t, Object exp, Object act) {
        if (exp == null || act == null) return exp == act;
        switch (t) {
            case BOOL:
                return asBool(exp) == asBool(act);
            case DOUBLE:
                return Math.abs(asDouble(exp) - asDouble(act)) < 1e-6;
            case STRING:
                return String.valueOf(exp).equals(String.valueOf(act));
            default:
                return exp.equals(act);
        }
    }

    private static boolean asBool(Object o) {
        if (o instanceof Boolean b) return b;
        return Boolean.parseBoolean(String.valueOf(o));
    }

    private static double asDouble(Object o) {
        if (o instanceof Number n) return n.doubleValue();
        return Double.parseDouble(String.valueOf(o));
    }

    /** 时间盒运行，返回 ops/sec。 */
    private double runFor(Engine engine, Object prepared, long ms) {
        long ops = runForOps(engine, prepared, ms);
        return ops / (ms / 1000.0);
    }

    private long runForOps(Engine engine, Object prepared, long ms) {
        Fact[] s = cfg.samples;
        long deadline = System.nanoTime() + ms * 1_000_000L;
        long count = 0;
        long acc = 0;
        int i = 0;
        try {
            while (System.nanoTime() < deadline) {
                Object res = engine.eval(prepared, s[i]);
                acc += blackhole(res);
                i = (i + 1) & 3; // 4 个样本轮转
                count++;
            }
        } catch (Throwable e) {
            // 运行期异常：返回已完成的量，错误在外层已尽量捕获
        }
        sink += acc;
        return count;
    }

    private static long blackhole(Object res) {
        if (res instanceof Boolean b) return b ? 1 : 0;
        if (res instanceof Number n) return Double.doubleToLongBits(n.doubleValue());
        if (res instanceof String str) return str.length();
        return res == null ? 0 : res.hashCode();
    }

    private static String shortErr(Throwable e) {
        String m = e.getMessage();
        String cls = e.getClass().getSimpleName();
        if (m == null) return cls;
        m = m.replace('\n', ' ').replace(',', ';');
        if (m.length() > 120) m = m.substring(0, 120);
        return cls + ":" + m;
    }

    public static void printTable(List<BenchResult> results) {
        System.out.printf("%-8s %-22s %-15s %-6s %-8s %-14s %-12s%n",
                "mode", "engine", "case", "fnDef", "correct", "ops/sec", "ns/op");
        for (BenchResult r : results) {
            if (!r.supported) {
                System.out.printf("%-8s %-22s %-15s %-6s %-8s %-14s %-12s  [%s]%n",
                        r.mode, r.engine, r.caseId, r.usesFunctionDef, "-", "UNSUPPORTED", "-", r.error);
            } else {
                System.out.printf("%-8s %-22s %-15s %-6s %-8s %-14s %-12s%n",
                        r.mode, r.engine, r.caseId, r.usesFunctionDef, r.correct,
                        String.format(java.util.Locale.ROOT, "%.0f", r.steadyOpsPerSec),
                        String.format(java.util.Locale.ROOT, "%.1f", r.steadyNsPerOp));
            }
        }
    }
}
