package me.btpka3.nativeexpr.guest;

import com.oracle.truffle.espresso.polyglot.Interop;

/**
 * Espresso guest 类：模拟 mtee3「用户用 Java 单类写事件预处理 / vbs 函数」的形态。
 * 一个类、每个用例对应一个 static 方法（方法名 == 用例 id）。
 *
 * 宿主互操作（实测可用模式）：
 *  - guest 方法以 {@code Object fact} 接收宿主对象（不直接引用宿主应用类，避免 guest/host 类身份冲突）；
 *  - 经 {@link com.oracle.truffle.espresso.polyglot.Interop} 读宿主字段(readMember)/调宿主方法(invokeMember)；
 *  - Interop 类须由 guest boot classloader 加载（引擎用 java.BootClasspathAppend 挂 espresso-polyglot.jar），
 *    否则其 native 方法不会被 Espresso 内化，报 UnsatisfiedLinkError。
 *
 * 本类编译成普通字节码，但 **不** 打进 native image，运行期由 Espresso 经 java.Classpath 动态加载，
 * Truffle 对其做运行期 JIT（即使在 native image 内）。
 */
public class EspCases {

    private static double amount(Object fact) throws Exception {
        return Interop.asDouble(Interop.readMember(fact, "amount"));
    }

    private static int age(Object fact) throws Exception {
        return Interop.asInt(Interop.readMember(fact, "age"));
    }

    private static double deviceScore(Object fact) throws Exception {
        return Interop.asDouble(Interop.readMember(fact, "deviceScore"));
    }

    // arith_cmp：算术比较
    public static Object arith_cmp(Object fact) throws Exception {
        return amount(fact) > 1000;
    }

    // bool_and：布尔与
    public static Object bool_and(Object fact) throws Exception {
        return amount(fact) > 1000 && age(fact) < 25;
    }

    // bool_mix：布尔混合
    public static Object bool_mix(Object fact) throws Exception {
        return (amount(fact) > 1000 && age(fact) < 25) || deviceScore(fact) >= 0.9;
    }

    // string_op：字符串操作（宿主 String 经 asString 转 guest String 后调用其方法）
    public static Object string_op(Object fact) throws Exception {
        String userId = Interop.asString(Interop.readMember(fact, "userId"));
        String city = Interop.asString(Interop.readMember(fact, "city"));
        return userId.startsWith("VIP") && city.equals("Beijing");
    }

    // weighted_score：加权计算
    public static Object weighted_score(Object fact) throws Exception {
        return amount(fact) * 0.3 + deviceScore(fact) * 700;
    }

    // nested：嵌套属性（history 为宿主对象，继续 readMember）
    public static Object nested(Object fact) throws Exception {
        Object history = Interop.readMember(fact, "history");
        int count = Interop.asInt(Interop.readMember(history, "count"));
        double totalAmount = Interop.asDouble(Interop.readMember(history, "totalAmount"));
        return count > 3 && totalAmount > 5000;
    }

    // coll_contains：集合包含（宿主 List.contains）
    public static Object coll_contains(Object fact) throws Exception {
        Object tags = Interop.readMember(fact, "tags");
        return Interop.asBoolean(Interop.invokeMember(tags, "contains", "risky"));
    }

    // ternary：三元表达式
    public static Object ternary(Object fact) throws Exception {
        return amount(fact) > 1000 ? "HIGH" : "LOW";
    }

    // func_def：用户自定义函数 + 调用（函数定义能力，纯 guest 计算）
    static double score(double a, double b) {
        return a * 0.3 + b * 700;
    }

    public static Object func_def(Object fact) throws Exception {
        return score(amount(fact), deviceScore(fact));
    }

    // method_call：调用宿主方法 riskFactor()
    public static Object method_call(Object fact) throws Exception {
        return Interop.asDouble(Interop.invokeMember(fact, "riskFactor"));
    }
}
