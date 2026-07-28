# Espresso (Java on Truffle) 技术调研

> 调研时间：2026-07-27
> 调研背景：mtee3 实时风控引擎探索将 Groovy 动态脚本 / VBS 预处理函数转 GraalVM Native Image，Espresso 是 native image 内嵌动态字节码的关键拼图。

## 一、Espresso 是什么

Espresso 是 GraalVM 团队开源的 Java 虚拟机规范实现，核心思路是把 JVM 本身用 Truffle 框架重写为一个"解释器"。Java 字节码由 Truffle 解释执行，再经 Graal 编译器 JIT 优化。

- 首次发布：GraalVM 21.0.0（2021-01-19），标记为实验性
- 当前支持 guest JDK 版本：Java 8 / 11 / 17 / 21 / 25
- 已通过 Java Compatibility Kit（TCK），语义兼容性与 HotSpot 一致
- 支持平台：Linux x64/AArch64、macOS AArch64、Windows x64

## 二、核心能力

### 2.1 强隔离

Guest 代码与 host VM 在内存、线程、类空间上完全分离。Guest 和 host 是两套完全独立的对象图，guest 代码无论怎么反射都摸不到 host 堆上的对象，除非显式通过 interop 暴露。

### 2.2 嵌入 Native Image

可以把动态字节码加载能力带进 GraalVM Native Image（原本 native image 不支持运行时加载新类）。

### 2.3 多语言互操作

Guest Java 可以直接与同一 polyglot context 里的 Python、Ruby、JS、Wasm 等互调，无需序列化。

### 2.4 可指定 Guest JDK 版本

Host 是 21，guest 可以跑 Java 8 / 11 / 17 / 21 / 25，方便遗留代码迁移或测试兼容性。

## 三、Guest/Host 类空间隔离模型

### 3.1 完全隔离，不可直接共享

- Guest 有自己的 JDK（通过 `--java.JavaHome` 指定），跟 host 的 `java.lang.*` 是完全独立的两套 Class 对象
- Guest 里的 `String.class` 和 host 里的 `String.class` 不是同一个东西，不能互相 cast，`instanceof` 不跨边界
- Host 对象进入 guest 后变成 "foreign object"，方法调用走 interop 消息（`invokeMember`、`readArrayElement` 等）
- Guest 不能直接 import/instantiate host 类

### 3.2 桥接机制

| 机制 | 说明 |
|------|------|
| `Polyglot.exportObject` / `importObject` | 全局 polyglot bindings，host 放入对象，guest 取出（foreign object） |
| `PolyglotInterfaceMappings` | 为指定接口生成 guest 侧动态代理，guest 像操作本地接口一样调方法 |
| `PolyglotTypeConverters` | 注册转换器，host 类型进入 guest 时自动转成 guest 侧等价对象（拷贝语义） |
| `Polyglot.cast` | 对 foreign object 做"增强 cast"，按 interop kind 重新定型 |

### 3.3 对 mtee3 的影响

Host 侧的风控上下文对象（事件、特征值等）传入 guest 时，要么走 interface mapping 让 guest 脚本用接口方法访问，要么提前序列化成 guest 能直接消费的结构（Map/List/基本类型）。不能期望 guest 脚本直接 `import com.alipay.mtee.Event` 然后强转。

## 四、沙箱与安全控制

### 4.1 Espresso 无内置 guest 类/方法黑名单

GraalVM 的 SandboxPolicy（TRUSTED → CONSTRAINED → ISOLATED → UNTRUSTED）管控的是 host-guest 边界，不提供"guest 内部禁止使用 `java.lang.Thread`"这种粒度的配置。

### 4.2 推荐三层沙箱方案

1. **编译期**：Groovy/Java 脚本编译后用 ASM 扫描字节码，黑名单指令（`NEW java/lang/Thread`、`INVOKEVIRTUAL java/lang/Thread.start` 等）直接拒绝加载
2. **运行时**：裁剪一份最小 guest JDK（`--java.JavaHome` 指向 stripped JDK），去掉 Thread、ProcessBuilder、java.io、java.net、java.lang.reflect 等
3. **Context 级别**：SandboxPolicy 设 ISOLATED 或 UNTRUSTED，封死 host 侧逃逸路径

### 4.3 与 OSGi 的对比

| 维度 | OSGi | Espresso |
|------|------|----------|
| 隔离强度 | ClassLoader 级别，反射/Unsafe 可穿透 | VM 级别，独立堆/类空间/GC |
| 安全边界 | 防依赖混乱，不防恶意代码 | 可防恶意代码（禁 native/限 CPU/限内存） |
| Native Image 兼容 | 基本不可用 | 天生为此设计 |
| 性能开销 | 零额外开销 | Truffle 解释→JIT，有预热成本 |
| 卸载干净度 | ClassLoader 泄漏老大难 | 销毁 context = 整个 guest 堆回收 |
| 生态成熟度 | 20+ 年 | 2021 年起，仍在演进 |

结论：OSGi 是"同一间屋子里用隔断分办公桌"，Espresso 是"在同一栋楼里建独立公寓，门禁系统由你定"。

## 五、线程模型与 ThreadLocal

### 5.1 `java.MultiThreaded=false`（默认）

- Guest JVM 只有一个逻辑线程
- Guest 代码物理上跑在进入 polyglot context 的那个 host 线程上
- Guest 内部 `Thread.currentThread()` 返回 guest 自己的 Thread 对象，不是 host Thread
- Guest 不能 `new Thread().start()`

### 5.2 Guest 能否访问 host 的 ThreadLocal？

**不能通过 guest 自己的 ThreadLocal 机制访问。** Guest 的 `ThreadLocal` 和 host 的 `ThreadLocal` 是两个完全不同的类。Guest 的 `Thread.currentThread()` 返回 guest Thread，其 `threadLocals` map 跟 host Thread 的没有任何关联。

间接路径：如果 host 把 `ThreadLocal<T>` 对象通过 interop 传入 guest，guest 对它调 `.get()` 走 interop 的 `invokeMember`，实际执行 host 侧的 `ThreadLocal.get()`。由于单线程模式下 guest 物理上跑在当前 host 线程上，能读到该 host 线程之前 set 的值。但这不是"guest 访问了 host ThreadLocal"，而是委托 host 执行了一段代码。

### 5.3 Guest 能否自行设置 ThreadLocal？

**能。** Guest JVM 通过了 TCK，`java.lang.ThreadLocal` 完整可用。在单线程模式下退化为"per-context 全局变量"。

注意：如果 host 侧在不同时刻用不同 host 线程进入同一个 guest context（接力模式），guest 内部看到的仍然是同一个 guest Thread 对象，ThreadLocal 值不会因 host 线程切换而丢失。

### 5.4 对 mtee3 的影响

- 脚本内部用 ThreadLocal 做临时变量没问题，但语义等价于 static 变量
- Host 侧传上下文给 guest，正确做法是通过 interop bindings 或 interface mapping 注入
- 鹰眼 traceId 等基于 ThreadLocal 的链路追踪，需在进入 guest 前显式提取值以参数传入

## 六、预热与编译优化

### 6.1 编译管线

Guest 字节码先由 Truffle 解释器执行，热点方法被 Graal 编译器逐层 JIT 为机器码。

关键旋钮：

```bash
# latency 模式（偏向快速编译）
-Dtruffle.engine.Mode=latency

# 降低编译阈值
-Dtruffle.engine.FirstTierCompilationThreshold=100
-Dtruffle.engine.LastTierCompilationThreshold=5000

# 强制立即编译（调试用）
-Dtruffle.engine.CompileImmediately=true

# 创建时即 AOT 编译所有可达 root
-Dtruffle.engine.CompileAOTOnCreate=true
```

### 6.2 同进程跨 Context 共享编译代码（已从源码确认）

**Espresso 源码确认支持 `ContextPolicy.SHARED`**（`EspressoLanguage.java:104`）：

```java
@Registration(id = EspressoLanguage.ID,
              contextPolicy = TruffleLanguage.ContextPolicy.SHARED, ...)
```

多个 context 共享同一个 Engine 时，编译产物（AST + 机器码）跨 context 复用。`initializeMultipleContexts()` 设置 `isShared = true`，language 级别缓存 ParserKlass、LinkedKlass 等元数据。

```java
Engine sharedEngine = Engine.newBuilder()
    .option("engine.Mode", "latency")
    .build();

// 预热 context
try (Context warmup = Context.newBuilder("java").engine(sharedEngine).build()) {
    warmup.eval("java", warmupScript);
}

// 生产 context 直接复用已编译代码
try (Context prod = Context.newBuilder("java").engine(sharedEngine).build()) {
    prod.eval("java", actualRuleScript);
}
```

### 6.3 跨进程持久化：Auxiliary Engine Caching（仅 Oracle GraalVM）

```bash
# Native Image 构建时
native-image -H:+AuxiliaryEngineCache -H:ReservedAuxiliaryImageBytes=1073741824 -jar my-app.jar

# 运行时存盘
./my-app --experimental-options --engine.Cache --engine.CacheStore --engine.CacheCompile=hot

# 后续运行加载（几乎零预热）
./my-app --experimental-options --engine.Cache --engine.CacheLoad
```

缓存内容：encoded graph + 机器码 + profiled AST。加载"几乎瞬时"。

### 6.4 Context 预初始化（已从源码确认）

**Espresso 源码确认实现了完整的 pre-initialization + context patching 流程**：

- `EspressoLanguage.java:289-306`：`isPreInitialization()` 分支，build time 创建 inner context 做预初始化
- `EspressoLanguage.java:400-418`：`patchContext()` 返回 true，运行时 patch env
- `areOptionsCompatible()` 校验 JavaHome、BootClasspath、Verify 等选项一致性

```bash
native-image -Dpolyglot.image-build-time.PreinitializeContexts=java -jar my-app.jar
```

### 6.5 社区版替代方案

无 Oracle GraalVM 时：
- 同进程 shared engine（仍可用）
- `engine.Mode=latency` + 降阈值（几百次调用内完成编译）
- `engine.CompileAOTOnCreate=true`（context 创建时立即编译所有 AOT-eligible root）
- 进程级预热脚本（启动后灌合成流量，等编译队列排空再切真实流量）

## 七、Oracle GraalVM 商用许可

自 2023 年 6 月起，原 "Oracle GraalVM Enterprise Edition" 更名为 "Oracle GraalVM"，采用 GFTC（GraalVM Free Terms and Conditions）许可：

- **允许任何用户（包括商业和生产环境）免费使用**
- 无需点击同意（no click-through license）
- 允许 redistribution，但不得收费
- 托管/SaaS 场景视为内部使用，可以对外收费
- 付费项只有 Oracle 的技术支持（Premier/Extended/Sustaining）
- **Auxiliary Engine Caching 包含在 Oracle GraalVM 中，可免费商用**

## 八、多 Guest JVM 之间共享服务/数据

Espresso 没有内置的"guest-to-guest RPC"或"共享内存"原语。多个 guest context 之间的通信必须经过 host 中转或 Polyglot Bindings。

### 方案 A：Polyglot Bindings（全局符号表）

```java
// Guest A：暴露服务
Polyglot.exportObject("ruleService", new RuleServiceImpl());

// Guest B：消费服务
Object svc = Polyglot.importObject("ruleService");
RuleService api = Polyglot.cast(RuleService.class, svc);
api.execute(event);
```

- 所有共享同一 Engine 的 context 看到同一个 bindings scope
- 对象跨 context 传递时变成 foreign object，方法调用走 interop 消息
- 需要 `allowPolyglotAccess(true)` 或 `--polyglot` 选项
- 性能：每次跨 context 调用 = interop 消息分派 + foreign object 包装（微秒级）

### 方案 B：Host 做中介（推荐用于 API/SPI 场景）

Host 代码持有所有 context 引用，充当服务注册中心和消息路由器。可做鉴权、限流、序列化格式控制、错误隔离。

### 方案 C：共享数据结构（通过 host 注入）

Host 创建共享 Map/List，注入到每个 guest 的 bindings。所有 guest 看到同一份数据。并发访问需 host 侧保证线程安全。

### 方案 D：共享内存（DirectByteBuffer / off-heap）

Espresso 支持 `NewDirectByteBuffer`（JNI 层）。Host 分配 off-heap 内存，把地址传入每个 guest，guest 通过 DirectByteBuffer 直接读写。唯一能做到"零拷贝、纳秒级"的路径，但需要自己实现序列化协议和并发控制。

### 方案 E：Language-level 自动共享

`ContextPolicy.SHARED` 下，类元数据（ParserKlass、LinkedKlass）和编译后机器码跨 context 自动共享，无需额外设计。但对象实例不共享。

### 实际建议（mtee3 场景）

| 需求 | 推荐方案 | 理由 |
|------|----------|------|
| 规则调用公共服务（API/SPI） | 方案 B（host 中介）+ 接口定义 | 可控、可鉴权、错误隔离 |
| 高频读取共享特征值 | 方案 D（off-heap）或方案 C（host Map） | 延迟敏感 |
| 低频配置/元数据同步 | 方案 A（Polyglot Bindings） | 简单、够用 |
| 类/代码复用 | 自动（shared engine） | 无需额外设计 |

核心原则：guest 之间没有直接通道，一切跨 context 交互都必须经过 host 或 polyglot bindings 中转。设计时把 host 当作"微服务网关"来规划，guest 当作"无状态计算单元"。

## 九、mtee3 落地建议总结

1. **Native Image + Espresso** 是可行路径，Oracle GraalVM 已免费商用
2. **安全沙箱**：编译期 ASM 扫描 + 裁剪 guest JDK + SandboxPolicy ISOLATED
3. **预热策略**：shared Engine + latency 模式 + CompileAOTOnCreate；有 Oracle GraalVM 则加 Auxiliary Engine Cache
4. **接口边界**：host 侧定义服务接口，通过 InterfaceMappings 或 Polyglot Bindings 暴露给 guest
5. **上下文传递**：不依赖 ThreadLocal 继承，显式通过 bindings 注入事件/特征/traceId
6. **多规则集隔离**：每个规则集一个 guest context，共享 Engine 复用编译码，host 做路由

## 参考链接

- [Espresso Reference Manual](https://www.graalvm.org/latest/reference-manual/espresso/)
- [Espresso Interoperability](https://www.graalvm.org/latest/reference-manual/espresso/interoperability/)
- [Espresso Implementation Details](https://www.graalvm.org/latest/reference-manual/espresso/implementation/)
- [GraalVM Sandboxing Guide](https://www.graalvm.org/latest/security-guide/sandboxing/)
- [Truffle Options](https://www.graalvm.org/latest/graalvm-as-a-platform/language-implementation-framework/Options/)
- [Auxiliary Engine Caching](https://www.graalvm.org/jdk24/graalvm-as-a-platform/language-implementation-framework/AuxiliaryEngineCachingEnterprise/)
- [Truffle AOT Overview](https://www.graalvm.org/22.0/graalvm-as-a-platform/language-implementation-framework/AOTOverview/index.html)
- [GraalVM FAQ - Licensing](https://www.graalvm.org/faq/)
- [Espresso Source (oracle/graal)](https://github.com/oracle/graal/tree/master/espresso)
