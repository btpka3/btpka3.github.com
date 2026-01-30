package me.test.jdk.java.util;

/**
 * @author dangqian.zll
 * @date 2024/5/29
 */
public class MySpiImpl3 implements MySpi {
    public MySpiImpl3() {
        System.out.println("MySpiImpl3 init : " + System.identityHashCode(this));
    }

    public String sayHello(String name) {
        return "MySpiImpl3 : hello " + name;
    }
}
