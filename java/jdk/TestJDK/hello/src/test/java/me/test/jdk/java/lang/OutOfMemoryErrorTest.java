package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/4
 */
public class OutOfMemoryErrorTest {

    @Test
    public void x() {
        System.out.println("=======1111");
        try {
            throw new OutOfMemoryError("xxx");
        } catch (Throwable e) {
            System.out.println("=======2222");
        } finally {
            System.out.println("=======3333");
        }

    }
}
