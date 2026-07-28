package me.btpka3.nativeexpr.engines;

import me.btpka3.nativeexpr.Engine;
import me.btpka3.nativeexpr.Fact;
import me.btpka3.nativeexpr.LogicalCase;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

/**
 * Espresso 适配器（Truffle 客体语言，guest = Java 字节码）。
 *
 * 用户逻辑写成一个 Java 单类 {@code me.btpka3.nativeexpr.guest.EspCases}，
 * 每个用例对应一个 static 方法（方法名 == 用例 id），编译成普通字节码后 **不** 打进 native image，
 * 运行期经 Context option "java.Classpath" 由 Espresso 动态加载，Truffle 对其做运行期 JIT。
 *
 * 平台约束（实测）：
 *  - JVM 模式：Espresso 核心 JDK 库的 LLVM bitcode 官方仅提供 linux-amd64 / darwin-amd64；
 *    在 darwin-aarch64 上 JVM 模式构造即失败（缺 eden/libjvm 原生库），本引擎会以 UNSUPPORTED 记录。
 *  - native image 模式：Espresso 解释器被 AOT 成宿主机器码、guest JDK 为字节码运行期解释，
 *    与宿主架构无关，aarch64 可用（需单一 Espresso context）。
 *
 * guest classpath 由系统属性 espresso.guest 指定，默认 target/guest.jar（相对进程 CWD）。
 */
public class EspressoEngine implements Engine {

    private static final String GUEST_CLASS = "me.btpka3.nativeexpr.guest.EspCases";

    private final Context ctx;
    private final Value guestClass;

    public EspressoEngine() {
        String guestCp = System.getProperty("espresso.guest", "target/guest.jar");
        String polyglotJar = System.getProperty("espresso.polyglot", "target/guest-lib/espresso-polyglot.jar");
        this.ctx = Context.newBuilder("java")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(cn -> true)
                .allowNativeAccess(true)
                .allowExperimentalOptions(true)
                .option("java.Classpath", guestCp)
                // guest 经 com.oracle.truffle.espresso.polyglot.Interop 访问宿主对象；
                // Interop 的 native 方法只有由 guest boot classloader 加载时才会被 Espresso 内化，
                // 故须把 espresso-polyglot.jar 挂到 guest boot classpath（实验选项）。
                .option("java.BootClasspathAppend", polyglotJar)
                .build();
        // 加载 guest 类（运行期动态加载用户字节码）：
        // Espresso 不支持 eval("java", 类名)，需经 "java" 语言 bindings 的 getMember 加载 guest 类。
        this.guestClass = ctx.getBindings("java").getMember(GUEST_CLASS);
    }

    @Override
    public String key() { return "esp"; }

    @Override
    public String displayName() { return "Espresso(Truffle,Java)"; }

    @Override
    public Object compile(LogicalCase c) {
        // guest 方法名与用例 id 一致；"编译"即确定要调用的 static 方法名
        return c.id;
    }

    @Override
    public Object eval(Object prepared, Fact fact) {
        String method = (String) prepared;
        Value r = guestClass.invokeMember(method, fact);
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
