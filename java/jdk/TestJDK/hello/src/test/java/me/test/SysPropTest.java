package me.test;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/15
 */
public class SysPropTest {

    public static void main(String[] args) {
        // 如果java命令行重复指定了多个 jvm属性，则以最后的值为准。
        // 比如: "-Daaa=a1 -Daaa=a2" 运行态的值是 "a2"
        System.out.println("=============" + System.getProperty("aaa"));
    }
}
