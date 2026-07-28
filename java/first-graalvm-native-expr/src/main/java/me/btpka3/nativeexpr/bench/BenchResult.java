package me.btpka3.nativeexpr.bench;

import java.util.ArrayList;
import java.util.List;

/** 单个 (mode, engine, case) 的测量结果。 */
public final class BenchResult {
    public String mode;
    public String engine;
    public String caseId;
    public String category;
    public boolean usesFunctionDef;
    public boolean supported = true;
    public boolean correct = false;
    public String error = "";
    public long compileNs = -1;
    public long steadyOps = -1;
    public double steadyOpsPerSec = -1;
    public double steadyNsPerOp = -1;
    /** 预热曲线：每轮 ops/sec（用于观察 Truffle/JIT 升温）。 */
    public List<Double> warmupCurve = new ArrayList<>();

    public String csvLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(mode).append(',')
          .append(engine).append(',')
          .append(caseId).append(',')
          .append(esc(category)).append(',')
          .append(usesFunctionDef).append(',')
          .append(supported).append(',')
          .append(correct).append(',')
          .append(compileNs).append(',')
          .append(steadyOps).append(',')
          .append(fmt(steadyOpsPerSec)).append(',')
          .append(fmt(steadyNsPerOp)).append(',')
          .append(esc(error)).append(',')
          .append(curve());
        return sb.toString();
    }

    private String curve() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < warmupCurve.size(); i++) {
            if (i > 0) sb.append(';');
            sb.append(fmt(warmupCurve.get(i)));
        }
        return sb.toString();
    }

    private static String fmt(double d) {
        if (d < 0) return "-1";
        return String.format(java.util.Locale.ROOT, "%.1f", d);
    }

    private static String esc(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    public static String csvHeader() {
        return "mode,engine,caseId,category,usesFunctionDef,supported,correct,"
                + "compileNs,steadyOps,steadyOpsPerSec,steadyNsPerOp,error,warmupCurve(ops/sec per round)";
    }
}
