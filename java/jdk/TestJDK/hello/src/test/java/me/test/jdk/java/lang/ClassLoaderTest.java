package me.test.jdk.java.lang;

import io.github.classgraph.ClassGraph;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2021/7/25
 * @see <a href="http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html">Java Reflection - Dynamic Class Loading and Reloading</a>
 */
public class ClassLoaderTest {

    String className = "me.test.jdk.java.lang.MyChildClass";

    /**
     * 演示自定义class loader，并直接从class文件加载类。
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass(className);

        MyParentClass object1 = (MyParentClass) myObjectClass.newInstance();
        Supplier<Long> object2 = (Supplier<Long>) myObjectClass.newInstance();
        System.out.println("object1.getName() = " + object1.getName());
        System.out.println("object2.get() = " + object2.get());

        //create new class loader so classes can be reloaded.

        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass(className);

        object1 = (MyParentClass) myObjectClass.newInstance();
        object2 = (Supplier<Long>) myObjectClass.newInstance();

        System.out.println("object1.getName() = " + object1.getName());
        System.out.println("object2.get() = " + object2.get());

    }

    public class MyClassLoader extends ClassLoader {

        public MyClassLoader(ClassLoader parent) {
            super(parent);
        }

        public Class loadClass(String name) throws ClassNotFoundException {
            if (!className.equals(name))
                return super.loadClass(name);

            try {
                URL myUrl = ClassLoaderTest.class.getClassLoader().getResource("MyChildClass.class");
                URLConnection connection = myUrl.openConnection();
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = input.read();

                while (data != -1) {
                    buffer.write(data);
                    data = input.read();
                }

                input.close();

                byte[] classData = buffer.toByteArray();
                System.out.println("defineClass: " + name);
                return defineClass(className, classData, 0, classData.length);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    /**
     * 验证JDK自带的classloader 以及层级关系
     */
    @Test
    public void testJdkClassLoader() {
        System.out.println("java.version="+System.getProperty("java.version"));
        System.out.println("java.specification.version="+System.getProperty("java.specification.version"));
        System.out.println("java.runtime.version="+System.getProperty("java.runtime.version"));
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();
        // jdk.internal.loader.ClassLoaders$PlatformClassLoader
        System.out.println("platformClassLoader = " + platformClassLoader.getClass());
        System.out.println("platformClassLoader.parent = " + platformClassLoader.getParent());
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // jdk.internal.loader.ClassLoaders$AppClassLoader
        System.out.println("systemClassLoader = " + systemClassLoader.getClass());
        System.out.println("String.class.getClassLoader() = " + String.class.getClassLoader());
    }

    /**
     * URLClassLoader -> CLassPath
     */
    @Test
    public void testURLClassLoader() {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0]);
        urlClassLoader.getURLs();
    }

    /**
     * 验证通过 ClassGraph 遍历获取 classLoader 获取classPath.
     *
     * @see <a href="https://github.com/classgraph/classgraph">ClassGraph</a>
     */
    @Test
    public void classLoader2ClassPath() {
        String classPath = System.getProperty("java.class.path");
        System.out.println("=========== System.getProperty(\"java.class.path\") : " + classPath);
        System.out.println("===========");
        System.out.println();
        printClassPath(classPath);
        System.out.println();

        classPath = new ClassGraph()
                //.addClassLoader(xxxClassLoader)
                .getClasspath();
        System.out.println("=========== classPath : " + classPath);
        System.out.println("===========");
        System.out.println();
        printClassPath(classPath);
    }

    public static void printClassPath(String classPath) {
        String[] arr = classPath.split("[;:,]");
        System.out.println(arr.length);
        for (String s : arr) {
            if (StringUtils.isNotBlank(s)) {
                System.out.println(s);
            }
        }

    }

}
