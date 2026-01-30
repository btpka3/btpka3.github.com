package me.test.jdk.java.util;

/**
 * @author dangqian.zll
 * @date 2024/5/29
 */
public class MySpiImpl2 implements MySpi {
    public MySpiImpl2() {
        System.out.println("MySpiImpl2 init : " + System.identityHashCode(this));
    }

    public String sayHello(String name) {
        return "MySpiImpl2 : hello " + name;
    }
}
