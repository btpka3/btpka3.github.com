package me.test.jdk.java.lang;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ClassLoader2Test {

    String className = "me.test.jdk.java.lang.MyChildClass";

    /**
     * 演示自定义class loader，并直接从class文件加载类。
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        // 3.14.0
        System.out.println("111: "
                + StringUtils.class.getProtectionDomain().getCodeSource().getLocation());

        // mvn dependency:get  -Dartifact=org.apache.commons:commons-lang3:3.9
        File jarFile = new File(System.getProperty("user.home")
                + "/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar");

        {
            URLClassLoader cl = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});
            Class clazz = Class.forName("org.apache.commons.lang3.StringUtils", true, cl);
            // 3.14.0, 因为先从父classLoader 找，cl的父classLoader默认是当前类的classLoader
            System.out.println(
                    "222: " + clazz.getProtectionDomain().getCodeSource().getLocation());
        }
        {
            URLClassLoader cl = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}, null, null);
            Class clazz = Class.forName("org.apache.commons.lang3.StringUtils", true, cl);
            // 3.9
            System.out.println(
                    "333: " + clazz.getProtectionDomain().getCodeSource().getLocation());
        }
        // 3.14.0
        System.out.println("444: "
                + StringUtils.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
