package me.test.jdk.java.util;

/**
 * @author dangqian.zll
 * @date 2024/5/29
 */
public class MySpiImpl1 implements MySpi {

    public MySpiImpl1() {
        System.out.println("MySpiImpl1 init : " + System.identityHashCode(this));
    }

    public String sayHello(String name) {
        return "MySpiImpl1 : hello " + name;
    }
}
