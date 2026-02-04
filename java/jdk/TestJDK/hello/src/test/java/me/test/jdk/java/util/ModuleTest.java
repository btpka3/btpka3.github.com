package me.test.jdk.java.util;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
public class ModuleTest {

    @Test
    public void test01() {
        {
            Module module = this.getClass().getModule();
            System.out.println("module = " + module);
        }
        {
            Module module = String.class.getModule();
            System.out.println("module = " + module);
            module.getClassLoader();
        }

        ClassLoader cl;
    }
}
