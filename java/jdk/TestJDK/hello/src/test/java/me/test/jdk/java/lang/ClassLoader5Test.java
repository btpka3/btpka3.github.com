package me.test.jdk.java.lang;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 测试 ThreadLocal + 不同 classLoader。
 * 增加 jvm 参数 `-verbose:class` 确认 类加载情况
 *
 * @author dangqian.zll
 * @date 2025/7/17
 */
public class ClassLoader5Test {

    /**
     * ERROR: 直接使用当前类的 classloader 运行 会报错。
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {

        // mvn dependency:get  -Dartifact=org.apache.commons:commons-lang3:3.9
        File jarFile = new File(System.getProperty("user.home")
                + "/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar");

        for (int i = 0; i < 2; i++) {
            System.out.println("============== i = " + i);
            URLClassLoader cl = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}, null, null);
            Class clazz = Class.forName("org.apache.commons.lang3.tuple.MutablePair", true, cl);
            Object o = clazz.getConstructor().newInstance();
            ClassLoader5TcHolder.HOLDER.set(o);
            new ClassLoader5Runnable().run();
        }
    }

    @Test
    public void test02() throws Exception {

        // mvn dependency:get  -Dartifact=org.apache.commons:commons-lang3:3.9
        File jarFile = new File(System.getProperty("user.home")
                + "/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar");

        // URL classLoader5RunnableUrl = ClassLoader5Test.class.getResource("ClassLoader5Runnable.class");
        URL testClassesUrl = ClassLoader5Test.class.getResource("/");
        Assertions.assertNotNull(testClassesUrl);
        System.out.println("===== testClassesUrl= " + testClassesUrl);

        URL[] urls = new URL[] {jarFile.toURI().toURL(), testClassesUrl};

        for (int i = 0; i < 2; i++) {
            System.out.println("============== i = " + i);
            URLClassLoader cl = new URLClassLoader(urls, null, null) {
                @Override
                protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                    // 模拟 从父classloader 加载
                    if ("me.test.jdk.java.lang.ClassLoader5TcHolder".equals(name)) {
                        return ClassLoader5TcHolder.class;
                    }
                    return super.loadClass(name, resolve);
                }
            };
            Class clazz = Class.forName("org.apache.commons.lang3.tuple.MutablePair", true, cl);
            Object o = clazz.getConstructor().newInstance();
            ClassLoader5TcHolder.HOLDER.set(o);

            Class runnableClazz = Class.forName("me.test.jdk.java.lang.ClassLoader5Runnable", true, cl);
            ((Runnable) runnableClazz.getConstructor().newInstance()).run();
        }
    }
}
