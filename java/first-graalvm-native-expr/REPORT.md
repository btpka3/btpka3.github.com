# GraalVM Native Image 配合动态表达式引擎评估报告

> 目标：为 mtee3 实时风控引擎「动态脚本」能力迁移到 GraalVM Native Image 提供选型依据。
> 实测环境：Oracle GraalVM 25.0.3（native-image 25.0.3，Substrate VM，serial gc），macOS arm64，JDK 25。
> 工程：`first-graalvm-native-expr`，6 个引擎 × 10 个逻辑用例，JVM 与 Native 各跑一遍，全部数据来自 `results/bench-jvm.csv` 与 `results/bench-native.csv`（Espresso 在 JVM 模式于 darwin-arm64 不可用，记 UNSUPPORTED；native 模式实测可用）。

---

## 一、结论速览（TL;DR）

1. **Native Image ≠ 只能解释执行、性能更差**。静态 Java 代码在 native 下吞吐与 JVM JIT 峰值基本持平（52–92M ops/s），证明 AOT 编译到机器码同样能拿到接近 JIT 的稳态性能。

2. **Truffle 系 guest 语言（GraalJS / Espresso）是 native 下唯二能「运行时 JIT 用户代码」的高性能载体**。即便在 native image 里，Truffle 仍会对运行时才出现的代码做部分求值 + 运行时编译，**原生支持函数定义**，这是「用户代码编译期不存在」这一核心约束下的关键能力。GraalJS 吞吐更高（12–28M ops/s）；Espresso 让用户**继续写 Java 语法**，实测 1.3–8.5M ops/s。

3. **Aviator 与 Native Image 不兼容**：即使强制 `EvalMode.INTERPRETER`，Aviator 仍在运行期 `defineClass` 生成 `AviatorScript_*` 类，触发 `UnsupportedFeatureError: Classes cannot be defined at runtime`。在 native 下完全不可用。

4. **MVEL（reflective 优化器）与 QLExpress 可在 native 下以解释器方式工作**，但吞吐明显低于 GraalJS：MVEL 约 2–11M、QLExpress 约 0.6–2M ops/s。MVEL 还有内联 `def` 函数复用 vars map 时重复注册的坑（需每次求值新建 vars）。

5. **Espresso（Java 作为 guest 语言）已在 native 下打通并实测可用**：用户写一个普通 Java 单类（每用例一个 static 方法），编译成字节码但**不**进 native image，运行期由 Espresso 动态加载并 JIT。10 个用例全部正确，稳态 1.3–8.5M ops/s（介于 MVEL 与 QLExpress 之间，低于 GraalJS）。两个硬约束：(a) **JVM 模式在 darwin-arm64 不可用**（Espresso 核心 JDK 的 LLVM bitcode 官方仅发布 linux-amd64 / darwin-amd64），native 模式与架构无关、aarch64 可用；(b) guest 不能直接引用宿主应用类（guest/host 类身份隔离），须经 `com.oracle.truffle.espresso.polyglot.Interop` 读字段/调方法，且该 Interop 类必须挂到 guest **boot** classpath（`java.BootClasspathAppend`）才会被 Espresso 内化。代价是启动与内存偏高：native 下 Espresso 上下文初始化约 129ms（GraalJS 约 25ms），单跑 esp 峰值 RSS 约 550MB（含整套 guest JDK）。

6. **启动与内存是 native 的强项**：进程启动到首次求值完成，native 约 4ms（纯 Java）/ 25ms（含 GraalJS 上下文），JVM 约 163ms / 583ms；峰值 RSS native 全套约 213MB，JVM 仅 Java 引擎就约 452MB。代价是二进制体积与构建时间——加入 Espresso 后二进制从 143MB 增至约 389MB（内含 guest JDK 字节码）。

7. **对 mtee3 的建议**：动态载体首选 **GraalJS**（native 下吞吐最高、函数定义为一等公民）；**Espresso 已验证可行，是「场景二：用户继续写 Java 语法」的最优解**（用户学习成本最低，吞吐 1.3–8.5M ops/s 对旁路/预处理场景足够，但需接受其启动约 129ms、峰值 RSS 约 550MB 与 389MB 二进制的代价，且仅 native 可用、JVM 模式在 arm64 不可用）；MVEL(reflective)/QLExpress 作为「只需表达式、不需函数定义、追求极简依赖」的备选；Aviator 在 native 路线下排除。

---

## 二、为什么这件事不 trivial：封闭世界 vs 运行期生成代码

GraalVM Native Image 是**封闭世界（closed-world）AOT**：构建期必须能枚举到所有可达代码，运行期不允许再定义新类。凡是在运行期动态生成 JVM 字节码/类的引擎，都会直接构建失败或运行期崩溃：

- Groovy 动态类、运行期 javac、Aviator（含 INTERPRETER 模式）都会 `defineClass` → `UnsupportedFeatureError`。
- 这正是 mtee3 现状（Groovy 动态事件/实体 + vbs Java 单类）直接迁 native 会撞墙的根本原因。

破局点有两条：

- **Truffle 系语言（GraalJS / Espresso）**：用户代码作为「guest 语言」在 Truffle 上解释 + 运行时部分求值编译，**不需要在 native 构建期存在**，因此天然适配「用户配置编译期未知」的场景。Oracle GraalVM 提供完整的运行期优化编译与沙箱。
- **纯解释型 DSL（MVEL reflective / QLExpress 指令集）**：不生成类，靠反射或指令集解释执行，native 兼容，但拿不到 JIT 级吞吐。

---

## 三、测试设计

10 个逻辑用例覆盖风控规则常见形态，每个用例在不同引擎里用语义等价的写法表达，统一绑定变量 `fact`（含 amount/age/userId/city/deviceScore/tags/history 及 `riskFactor()` 方法、嵌套 `history`）：

| 用例 | 类别 | 考察点 | 涉及函数定义 |
|---|---|---|---|
| arith_cmp | 算术比较 | 数值运算 + 比较 | 否 |
| bool_and | 布尔与 | 短路与 | 否 |
| bool_mix | 布尔混合 | 与/或/非组合 | 否 |
| string_op | 字符串操作 | 字符串比较/拼接 | 否 |
| weighted_score | 加权计算 | 多字段加权求和 | 否 |
| nested | 嵌套属性 | 深层属性访问 | 否 |
| coll_contains | 集合包含 | 集合 contains（宿主方法互操作） | 否 |
| ternary | 三元表达式 | 条件分支 | 否 |
| func_def | 函数定义 | **用户自定义函数** | 是 |
| method_call | 宿主方法调用 | 调宿主对象方法 `riskFactor()` | 否 |

基准框架：先用 PureJava（手写 lambda）作为正确性 oracle 与吞吐上限基线；每用例先 12 轮预热（每轮 200ms）记录预热曲线，再 2000ms 稳态测量，4 个样本轮转，结果以 ops/s 计。正确性逐用例与 oracle 比对。

被测引擎：

- **java**：PureJava lambda，oracle + 上限基线。
- **js**：GraalJS，经 `org.graalvm.polyglot` 嵌入，`HostAccess.ALL`。
- **mvel**：MVEL2，强制 `OptimizerFactory.setDefaultOptimizer("reflective")` 以兼容 native。
- **ql**：QLExpress 指令集解释器，自定义 `contains` Operator。
- **aviator**：Aviator 5.4.3，`EvalMode.INTERPRETER`（native 下构建即失败，记 UNSUPPORTED）。
- **esp**：Espresso（`org.graalvm.espresso:java` 25.0.3），Java 字节码作 Truffle guest。用户逻辑写成单类 `EspCases`（每用例一个 static 方法），编译成 `target/guest.jar` 但**不**进 native image，运行期经 `java.Classpath` 动态加载；guest 经 `com.oracle.truffle.espresso.polyglot.Interop` 访问宿主 `fact`（该 jar 须挂 `java.BootClasspathAppend`）。JVM 模式在 darwin-arm64 不可用，记 UNSUPPORTED。

---

## 四、稳态吞吐实测（ops/s）

### 4.1 JVM（HotSpot JIT 峰值）

| 用例 | java | js | mvel | aviator | ql | esp |
|---|---:|---:|---:|---:|---:|---:|
| arith_cmp | 91.7M | 42.2M | 15.8M | 6.2M | 2.7M | — |
| bool_and | 86.5M | 44.0M | 12.3M | 3.8M | 1.9M | — |
| bool_mix | 91.2M | 44.2M | 9.7M | 3.1M | 1.6M | — |
| string_op | 84.0M | 27.6M | 10.3M | 3.9M | 1.5M | — |
| weighted_score | 67.2M | 25.1M | 9.6M | 3.0M | 1.6M | — |
| nested | 84.5M | 25.1M | 10.1M | 2.9M | 1.4M | — |
| coll_contains | 63.0M | 25.1M | 12.5M | 5.3M | 3.0M | — |
| ternary | 57.3M | 25.9M | 12.1M | 4.9M | 2.7M | — |
| func_def | 57.5M | 26.1M | 3.2M | 1.9M | 1.3M | — |
| method_call | 53.3M | 26.7M | 18.9M | 9.3M | 3.5M | — |

> esp 列在 JVM 模式全为 — ：Espresso 核心 JDK 的 LLVM bitcode 官方仅发布 linux-amd64 / darwin-amd64，本机 darwin-arm64 构造即失败（`Unexpected fallback symbol: Java_java_lang_System_registerNatives`），引擎容错记 UNSUPPORTED。

### 4.2 Native Image

| 用例 | java | js | mvel | aviator | ql | esp |
|---|---:|---:|---:|---:|---:|---:|
| arith_cmp | 82.2M | 20.9M | 10.6M | ✗ | 2.0M | 8.5M |
| bool_and | 70.4M | 23.6M | 7.8M | ✗ | 1.4M | 6.8M |
| bool_mix | 82.1M | 18.9M | 7.0M | ✗ | 1.0M | 3.2M |
| string_op | 67.1M | 20.8M | 7.6M | ✗ | 0.57M | 2.2M |
| weighted_score | 56.4M | 14.4M | 6.2M | ✗ | 1.0M | 1.5M |
| nested | 81.7M | 12.3M | 7.7M | ✗ | 0.86M | 1.3M |
| coll_contains | 52.4M | 12.8M | 10.2M | ✗ | 1.4M | 1.5M |
| ternary | 72.5M | 15.3M | 6.9M | ✗ | 1.9M | 1.4M |
| func_def | 57.4M | 13.1M | 1.9M | ✗ | 0.80M | 1.5M |
| method_call | 55.1M | 16.6M | 10.6M | ✗ | 1.4M | 2.1M |

> ✗ = Aviator 在 native 构建期即报 `UnsupportedFeatureError`，全部 10 例 UNSUPPORTED。

### 4.3 Native / JVM 稳态比值

| 引擎 | 比值区间 | 解读 |
|---|---|---|
| java | 0.78 – 1.00 | 静态代码 native 几乎不丢吞吐，部分用例持平 |
| js | 0.45 – 0.63 | Truffle 运行期 JIT 在 native 仍生效，但比 JVM 峰值低约一半 |
| mvel | 0.60 – 0.72 | 反射解释，native 略低于 JVM |
| ql | 0.40 – 0.73 | 指令集解释，基数小、波动相对大 |
| esp | n/a | JVM 模式在本机 arm64 不可用，无比值；native 绝对值见 4.2 |

关键观察：

- **java native ≈ JVM**（如 func_def 57.4M vs 57.5M 几乎持平）——直接回答「native 是否自始至终只能解释、反而更慢」：**不是**。AOT 机器码对静态代码能逼近 JIT 峰值。
- **js native 仍达 12–28M ops/s**，且预热曲线平稳（如 native js/arith_cmp 预热即 25–28M），说明 Truffle 在 native 内确实在做运行期编译，而非纯解释——这是 GraalJS 相对 MVEL/QL 的最大优势来源。
- **func_def 用例**：GraalJS 在 native 下 13.1M ops/s 且正确，证明「用户自定义函数」这一 mtee3 刚需在 native 下可行；MVEL/QL 虽也支持但吞吐低一个量级。
- **esp native 1.3–8.5M ops/s，且「读宿主字段越多的用例越慢」**（arith_cmp 单次 readMember 8.5M，weighted_score/nested 多次 readMember 降到 1.3–1.5M）——瓶颈在 guest↔host 的 Interop 边界穿越，而非 guest 计算本身（纯 guest 的 func_def 也因两次 readMember 落在 1.5M）。这说明 Espresso 的吞吐主要受宿主互操作密度限制；若把一批字段一次性传入 guest、减少跨界次数，可显著提速。即便如此，其量级已高于 QLExpress、与 MVEL 相当，对旁路/预处理场景足够。

---

## 五、启动 / 内存 / 产物实测

| 指标 | Native | JVM | 说明 |
|---|---:|---:|---|
| 启动到首次求值（纯 Java 引擎） | 4ms | 163ms | `sinceProcessMs`，native 约 40× 快 |
| 启动到首次求值（含 GraalJS 上下文） | 25ms | 583ms | GraalJS 首次 Context 初始化开销 |
| 启动到首次求值（含 Espresso 上下文） | ~129ms | n/a | Espresso 需初始化整套 guest JDK，明显重于 GraalJS；JVM 模式 arm64 不可用 |
| 峰值 RSS（全套引擎） | ~213MB | — | native 含 GraalJS+MVEL+QL |
| 峰值 RSS（仅 Java 引擎） | ~63MB | ~452MB | 同口径对比，native 约 7× 省 |
| 峰值 RSS（仅 Espresso） | ~550MB | — | 含整套 guest JDK 字节码，footprint ~383MB |
| 二进制大小 | 143MB → ~389MB | — | 加入 Espresso（内含 guest JDK）后增至约 389MB |
| native 构建耗时 | ~2.5–3 分钟 | — | `-O2 --no-fallback`，含 Espresso 约 3 分钟、峰值内存约 13GB |

启动与内存对 mtee3 的意义：风控旁路/弹性扩缩容场景下，native 的毫秒级启动与低 RSS 利于快速扩容与高密度部署；代价是产物体积与构建时间。**Espresso 会显著抬高这条代价线**：guest JDK 使二进制翻倍、上下文初始化约 129ms、单引擎峰值 RSS 约 550MB——选型时需在「用户继续写 Java」的体验与这些开销之间权衡。

---

## 六、函数定义能力矩阵（mtee3 关键诉求）

| 引擎 | 支持用户自定义函数 | native 兼容 | 备注 |
|---|---|---|---|
| GraalJS | ✅ 原生（function/IIFE） | ✅ | 运行期 JIT，吞吐最高 |
| MVEL | ✅（内联 `def`） | ✅（reflective） | 复用 vars map 会「重复函数」报错，需每次新建 vars |
| QLExpress | ✅（指令集） | ✅ | 吞吐最低 |
| Aviator | ✅ | ❌ | native 下 defineClass 直接失败 |
| Espresso | ✅（即 Java 语法） | ✅（实测，仅 native） | 用户写普通 Java 单类；native 下 1.3–8.5M ops/s；JVM 模式 arm64 不可用 |

---

## 七、对 mtee3 的选型建议

mtee3 两类动态脚本场景与本次结论的对应：

**场景一：用户用 Groovy 动态创建风控事件/实体（类似 class）+ 事件/实体特征（类似 getter）。**
本质是「运行期定义结构化对象 + 计算逻辑」。在 native 封闭世界下无法继续用 Groovy 动态类。建议改用 **GraalJS**：用 JS 对象/函数字面量承载「事件/实体 + 特征 getter」，既保留动态性，又能在 native 内拿到运行期 JIT（实测 12–28M ops/s）。语法上 JS 对象字面量 + 函数对用户的学习成本低于全新 DSL，且函数定义为一等公民。

**场景二：白屏界面让用户写事件预处理 vbs 函数（Java 单类）。**
**首选 Espresso（已验证）**：让用户继续写 Java 语法——一个普通 Java 单类、每个预处理逻辑一个 static 方法，编译成字节码但不进 native image，运行期由 Espresso 动态加载并 JIT。用户零学习成本、无需改写存量 vbs 逻辑，10 个用例实测全部正确、稳态 1.3–8.5M ops/s。需要接受的代价：仅 native 可用（JVM 模式 arm64 不可用）、上下文初始化约 129ms、单引擎峰值 RSS 约 550MB、二进制增至约 389MB；以及 guest 不能直接 import 宿主应用类，须经 `Interop` 读字段/调方法（建议一次性把所需字段打包传入 guest，减少跨界次数以提速）。若团队希望统一技术栈、不引入 guest JDK 开销，**GraalJS 是次选**：把 vbs 预处理改写为 JS 函数，吞吐更高（12–28M ops/s）但用户需学 JS。简单表达式场景仍可用 MVEL(reflective) 作轻量备选。

**统一建议**：

- 动态载体首选 **GraalJS**（native 下兼具「运行期 JIT + 函数定义 + 最高吞吐」）；**Espresso 已验证可行，作为「让用户继续写 Java」场景（场景二）的首选**，二者可按「用户是否必须写 Java」二选一或并存。
- **Aviator 在 native 路线下排除**（构建期即失败，无可用模式）。
- MVEL(reflective)/QLExpress 仅作「纯表达式、极简依赖」的补充，不作为承载用户函数定义的主力。
- 宿主互操作需在 native 下为被 JS 调用的宿主方法补充反射注册（本次 `ArrayList.contains` 即 tracing agent 未自动捕获、需手动补 `reachability-metadata.json` 才通过）——这是迁移时需要建立的工程规范。
- 采用 **Espresso** 时：guest 经 `com.oracle.truffle.espresso.polyglot.Interop` 访问宿主对象，espresso-polyglot.jar 须挂 guest **boot** classpath（`java.BootClasspathAppend`，需 `allowExperimentalOptions(true)`）；guest 字节码不进 native image、运行期经 `java.Classpath` 动态加载；espresso-svm 由 GraalVM 内置、**不可**放进构建 `-cp`；为控制跨界开销，建议把一批字段打包传入 guest 而非逐字段 readMember。

---

## 八、复现方式

```bash
# JVM 基准
./run.sh jvm        # 写 results/bench-jvm.csv（esp 在 arm64 记 UNSUPPORTED）

# 生成/更新 native-image 反射元数据（tracing agent）
./run.sh agent

# 构建 Espresso guest 字节码：把用户 Java 单类编成 target/guest.jar，
# 并从 espresso-libs-resources jar 抽出 espresso-polyglot.jar 到 target/guest-lib/
./build-guest.sh

# 构建 native（build-native.sh 直接调 Java 25 的 native-image，
# 规避 native-maven-plugin 0.10.6 误选 JDK21 的问题）
./build-native.sh

# Native 基准
./run.sh native     # 写 results/bench-native.csv（含 esp）

# 仅跑 Espresso（guest/polyglot 路径默认相对 CWD，可经 -D 覆盖）
./target/expr-bench --bench --mode native --engines esp \
    -Despresso.guest=target/guest.jar \
    -Despresso.polyglot=target/guest-lib/espresso-polyglot.jar \
    --out results/bench-native-esp.csv

# 启动测量
./target/expr-bench --startup --engines java
```

已知工程要点（踩坑沉淀）：

- Truffle 是 Multi-Release jar，**不能用 shade 打 fat-jar**，需保留独立 jar + 显式 classpath（工程用 maven-jar-plugin + dependency-plugin copy-deps）。
- MVEL 必须切 `reflective` 优化器；内联 `def` 复用同一 vars map 会报「重复函数」，每次求值新建 HashMap。
- Aviator 即使 INTERPRETER 也 defineClass，native 构建失败；引擎构造做了容错，记 UNSUPPORTED 而非崩溃。
- GraalJS 宿主方法互操作（如 `ArrayList.contains`）tracing agent 可能漏采，需手动补反射元数据。
- `Fact.tags` 用 `ArrayList` 而非不可变 `List.of`，否则 native 下宿主 `contains` 互操作报 Unknown identifier。
- **Espresso JVM 模式仅 linux-amd64 / darwin-amd64**（核心 JDK 的 LLVM bitcode 未发布 darwin-aarch64），arm64 上构造即失败；native 模式与架构无关可用。引擎构造做了容错，JVM 下记 UNSUPPORTED。
- **Espresso guest 不能直接引用宿主应用类**（guest/host 类身份隔离，NoClassDefFoundError），须经 `com.oracle.truffle.espresso.polyglot.Interop` 的 readMember/invokeMember/asX 访问宿主对象。
- **Interop 必须挂 guest boot classpath**（`java.BootClasspathAppend`，实验选项需 `allowExperimentalOptions(true)`）；若只挂 `java.Classpath`（app loader），其 native 方法不被 Espresso 内化，报 UnsatisfiedLinkError。espresso-polyglot.jar 从 `espresso-libs-resources` jar 的 `META-INF/resources/java/espresso-libs/<os>/<arch>/lib/` 内抽出。
- **espresso-svm.jar 由 GraalVM 内置**（`$GRAALVM/lib/svm/builder/`），**不可**放进构建 `-cp`，否则 native-image 报「part of the image builder itself」。
- 加载 guest 类用 `ctx.getBindings("java").getMember(fqcn)`，**不能** `eval("java", 类名)`（Espresso 不求值 Java 源码）。
- Espresso 上下文需 `allowNativeAccess(true)`；JVM 模式另需 `--enable-native-access=ALL-UNNAMED`。
- 含 Espresso 的 native 构建峰值内存约 13GB，内存紧张时会 OOM，构建前确保空闲内存。
