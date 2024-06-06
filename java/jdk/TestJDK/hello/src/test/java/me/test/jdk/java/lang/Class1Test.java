package me.test.jdk.java.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dangqian.zll
 * @date 2024/5/8
 */
public class Class1Test {

    /*
    # 测试步骤：
    # 1. copy 该java文件，并删除第一行的 package 声明
    mkdir -p /tmp/a
    cp Class1Test.java /tmp/a/
    awk 'NR>1' Class1Test.java > Class1Test.java.new
    mv Class1Test.java.new Class1Test.java

    # 2. 用 commons-lang3 编译。
    javac -cp ${HOME}/.m2/repository/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar Class1Test.java

    # 3. 用 commons-lang3 运行。
    java -cp ${HOME}/.m2/repository/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar:. Class1Test

    # 4. 不用 commons-lang3 运行。
    java -cp . Class1Test
    */

    public static void main(String[] args) {
        System.out.println("Hello world!");

        String clazz = "org.apache.commons.lang3.StringUtils";
        System.out.println("11111111111");
        if (isClassLoaded(clazz)) {
            System.out.println("22222222222");
            System.out.println("clazz `" + clazz + "` is loaded.");
            String str = "hello";
            boolean result = StringUtils.isBlank(str);
            System.out.println("StringUtils.isBlank(\"" + str + "\") =" + result);
        } else {
            System.out.println("33333333333");
            System.out.println("clazz `" + clazz + "` not loaded.");
        }

    }

    public static boolean isClassLoaded(String className) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = Class1Test.class.getClassLoader();
        }
        return loadClass(cl, className) != null;
    }

    public static boolean isClassLoaded(ClassLoader cl, String className) {
        return loadClass(cl, className) != null;
    }

    public static Class<?> loadClass(String className) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = Class1Test.class.getClassLoader();
        }
        return loadClass(cl, className);
    }

    public static Class<?> loadClass(ClassLoader cl, String className) {
        if (cl == null) {
            return null;
        }
        try {
            return cl.loadClass(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
