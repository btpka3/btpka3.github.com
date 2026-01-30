package me.test.jdk.java.lang;

/**
 * @author dangqian.zll
 * @date 2025/4/6
 */
public class TcclTest {

    public void x() {
        Thread.currentThread().getContextClassLoader().toString();
    }
}
