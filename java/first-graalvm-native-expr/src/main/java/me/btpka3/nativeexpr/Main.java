package me.btpka3.nativeexpr;

import me.btpka3.nativeexpr.bench.BenchResult;
import me.btpka3.nativeexpr.bench.Harness;
import me.btpka3.nativeexpr.engines.AviatorEngine;
import me.btpka3.nativeexpr.engines.EspressoEngine;
import me.btpka3.nativeexpr.engines.GraalJsEngine;
import me.btpka3.nativeexpr.engines.MvelEngine;
import me.btpka3.nativeexpr.engines.PureJavaEngine;
import me.btpka3.nativeexpr.engines.QlExpressEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 入口。模式：
 *   --bench     完整基准（默认）：编译+正确性+预热曲线+稳态吞吐，输出 CSV
 *   --agent     快速遍历所有引擎/用例各跑若干次，供 native-image agent 采样反射配置
 *   --startup   冷启动测量：打印 进程启动->首次求值完成 的耗时
 * 通用参数：--mode <label> --out <csv> --warmup-rounds N --warmup-ms N --steady-ms N --engines a,b,c
 */
public final class Main {

    public static void main(String[] args) throws Exception {
        Args a = Args.parse(args);
        switch (a.mode) {
            case "agent" -> runAgent(a);
            case "startup" -> runStartup(a);
            default -> runBench(a);
        }
    }

    record EngineEntry(Engine engine, String key, String constructError) {
        boolean ok() { return engine != null; }
    }

    /** 容错构造：某引擎在 native 下可能连构造都失败（如 Aviator 运行期 defineClass），不应拖垮整个进程。 */
    private static List<EngineEntry> engineEntries(Args a) {
        record Spec(String key, java.util.function.Supplier<Engine> sup) {}
        List<Spec> specs = List.of(
                new Spec("java", PureJavaEngine::new),
                new Spec("mvel", MvelEngine::new),
                new Spec("ql", QlExpressEngine::new),
                new Spec("aviator", AviatorEngine::new),
                new Spec("js", GraalJsEngine::new),
                new Spec("esp", EspressoEngine::new));
        List<EngineEntry> out = new ArrayList<>();
        for (Spec s : specs) {
            if (a.engines != null && !a.engines.contains(s.key())) continue;
            try {
                out.add(new EngineEntry(s.sup().get(), s.key(), null));
            } catch (Throwable t) {
                String msg = rootMsg(t);
                out.add(new EngineEntry(null, s.key, msg));
                System.out.println("[construct] " + s.key + " FAILED: " + msg);
            }
        }
        return out;
    }

    private static String rootMsg(Throwable t) {
        Throwable r = t;
        while (r.getCause() != null) r = r.getCause();
        String m = r.getMessage();
        if (m != null) { m = m.replace('\n', ' ').replace(',', ';'); if (m.length() > 140) m = m.substring(0, 140); }
        return r.getClass().getSimpleName() + (m == null ? "" : ":" + m);
    }

    private static List<Engine> engines(Args a) {
        List<Engine> ok = new ArrayList<>();
        for (EngineEntry e : engineEntries(a)) if (e.ok()) ok.add(e.engine());
        return ok;
    }

    private static void runBench(Args a) throws IOException {
        Harness.Config cfg = new Harness.Config();
        cfg.mode = a.label;
        cfg.warmupRounds = a.warmupRounds;
        cfg.warmupRoundMs = a.warmupMs;
        cfg.steadyMs = a.steadyMs;
        Harness h = new Harness(cfg);

        List<LogicalCase> cases = Cases.all();
        List<BenchResult> results = new ArrayList<>();
        for (EngineEntry entry : engineEntries(a)) {
            if (!entry.ok()) {
                for (LogicalCase c : cases) {
                    BenchResult r = new BenchResult();
                    r.mode = a.label;
                    r.engine = entry.key();
                    r.caseId = c.id;
                    r.category = c.category;
                    r.usesFunctionDef = c.usesFunctionDef;
                    r.supported = false;
                    r.error = "construct:" + entry.constructError();
                    results.add(r);
                    System.out.println("[" + a.label + "] " + entry.key() + " / " + c.id + " -> UNSUPPORTED (engine construct failed)");
                }
                continue;
            }
            Engine e = entry.engine();
            for (LogicalCase c : cases) {
                BenchResult r;
                try {
                    r = h.measure(e, c);
                } catch (Throwable t) {
                    r = new BenchResult();
                    r.mode = a.label;
                    r.engine = e.key();
                    r.caseId = c.id;
                    r.category = c.category;
                    r.usesFunctionDef = c.usesFunctionDef;
                    r.supported = false;
                    r.error = t.getClass().getSimpleName();
                }
                results.add(r);
                System.out.println("[" + a.label + "] " + e.key() + " / " + c.id
                        + " -> " + (r.supported ? (r.correct ? "OK " : "BAD") : "UNSUPPORTED")
                        + (r.supported ? (" " + String.format(java.util.Locale.ROOT, "%.0f ops/s", r.steadyOpsPerSec)) : (" (" + r.error + ")")));
            }
            try { e.close(); } catch (Throwable ignore) {}
        }

        Harness.printTable(results);
        writeCsv(a.out, results);
        System.out.println("CSV written: " + a.out);
    }

    /** 供 native-image tracing agent 采样：尽量覆盖所有引擎/用例的反射与宿主访问路径。 */
    private static void runAgent(Args a) {
        List<LogicalCase> cases = Cases.all();
        Fact[] samples = Fact.samples();
        for (Engine e : engines(a)) {
            for (LogicalCase c : cases) {
                try {
                    Object p = e.compile(c);
                    for (int k = 0; k < 50; k++) {
                        e.eval(p, samples[k & 3]);
                    }
                } catch (Throwable t) {
                    System.out.println("[agent] skip " + e.key() + "/" + c.id + " : " + t.getClass().getSimpleName());
                }
            }
            try { e.close(); } catch (Throwable ignore) {}
        }
        System.out.println("[agent] done");
    }

    /** 冷启动：JVM 初始化 -> 创建全部引擎 -> 每个用例求值一次 完成。 */
    private static void runStartup(Args a) {
        long jvmStart = java.lang.management.ManagementFactory.getRuntimeMXBean().getStartTime();
        long t0 = System.nanoTime();
        List<LogicalCase> cases = Cases.all();
        Fact f = Fact.samples()[0];
        int evaluated = 0;
        for (Engine e : engines(a)) {
            for (LogicalCase c : cases) {
                try {
                    Object p = e.compile(c);
                    e.eval(p, f);
                    evaluated++;
                } catch (Throwable ignore) {}
            }
            try { e.close(); } catch (Throwable ignore) {}
        }
        long inMainMs = (System.nanoTime() - t0) / 1_000_000;
        long sinceProcMs = System.currentTimeMillis() - jvmStart;
        System.out.println("STARTUP mode=" + a.label
                + " evaluated=" + evaluated
                + " inMainMs=" + inMainMs
                + " sinceProcessMs=" + sinceProcMs);
    }

    private static void writeCsv(String path, List<BenchResult> results) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(BenchResult.csvHeader()).append('\n');
        for (BenchResult r : results) sb.append(r.csvLine()).append('\n');
        Files.writeString(Path.of(path), sb.toString());
    }

    static final class Args {
        String mode = "bench";
        String label = "jvm";
        String out = "bench.csv";
        int warmupRounds = 12;
        long warmupMs = 200;
        long steadyMs = 2000;
        List<String> engines = null;

        static Args parse(String[] args) {
            Args a = new Args();
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--bench" -> a.mode = "bench";
                    case "--agent" -> a.mode = "agent";
                    case "--startup" -> a.mode = "startup";
                    case "--mode", "--label" -> a.label = args[++i];
                    case "--out" -> a.out = args[++i];
                    case "--warmup-rounds" -> a.warmupRounds = Integer.parseInt(args[++i]);
                    case "--warmup-ms" -> a.warmupMs = Long.parseLong(args[++i]);
                    case "--steady-ms" -> a.steadyMs = Long.parseLong(args[++i]);
                    case "--engines" -> a.engines = List.of(args[++i].split(","));
                    default -> {}
                }
            }
            return a;
        }
    }
}
