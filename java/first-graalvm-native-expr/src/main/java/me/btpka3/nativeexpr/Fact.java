package me.btpka3.nativeexpr;

import java.util.List;

/**
 * 模拟风控实时事件 / 风控实体：作为各引擎共享的宿主上下文对象。
 * 同时提供 public 字段 与 getter：
 *  - GraalJS(HostAccess.ALL) 走 public 字段
 *  - MVEL/QLExpress/Aviator 走 getter(bean 反射)
 * 这样 native image 下用 native-image agent 采样即可覆盖两类访问路径。
 */
public class Fact {

    public double amount;
    public int age;
    public String userId;
    public String city;
    public double deviceScore;
    public List<String> tags;
    public History history;

    public Fact(double amount, int age, String userId, String city,
                double deviceScore, List<String> tags, History history) {
        this.amount = amount;
        this.age = age;
        this.userId = userId;
        this.city = city;
        this.deviceScore = deviceScore;
        this.tags = tags;
        this.history = history;
    }

    public double getAmount() { return amount; }
    public int getAge() { return age; }
    public String getUserId() { return userId; }
    public String getCity() { return city; }
    public double getDeviceScore() { return deviceScore; }
    public List<String> getTags() { return tags; }
    public History getHistory() { return history; }

    /** 宿主方法调用：模拟 vbs 里调用 Java 侧算子 */
    public double riskFactor() {
        return amount / (age + 1) + deviceScore * 100.0;
    }

    public static class History {
        public int count;
        public double totalAmount;

        public History(int count, double totalAmount) {
            this.count = count;
            this.totalAmount = totalAmount;
        }

        public int getCount() { return count; }
        public double getTotalAmount() { return totalAmount; }
    }

    /** 固定的一组样本事件：覆盖不同分支走向，避免引擎/JIT 把结果常量折叠。 */
    public static Fact[] samples() {
        return new Fact[] {
            new Fact(1500.0, 22, "VIP_1001", "Beijing", 0.95,
                    new java.util.ArrayList<>(List.of("new_device", "risky")), new History(5, 8000.0)),
            new Fact(300.0, 40, "U_2002", "Shanghai", 0.30,
                    new java.util.ArrayList<>(List.of("trusted")), new History(1, 200.0)),
            new Fact(9999.0, 19, "VIP_3003", "Beijing", 0.60,
                    new java.util.ArrayList<>(List.of("risky", "night")), new History(9, 60000.0)),
            new Fact(50.0, 65, "U_4004", "Hangzhou", 0.10,
                    new java.util.ArrayList<>(List.of("trusted", "vip_shop")), new History(0, 0.0)),
        };
    }
}
