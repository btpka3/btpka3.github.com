package me.test.jdk.java.lang;

import io.github.classgraph.ClassGraph;
import java.net.URLClassLoader;
import java.util.Objects;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 向 {@link ClassLoader#getPlatformClassLoader()} 中增加 jar
 *
 * @author dangqian.zll
 * @date 2024/10/30
 * @see ClassLoader#getPlatformClassLoader()
 */
public class ClassLoader3Test {

    @SneakyThrows
    @Test
    public void test01() {
        ClassLoader c = ClassLoader.getPlatformClassLoader();
        Assertions.assertEquals("jdk.internal.loader.ClassLoaders$PlatformClassLoader", c.getClass().getName());
        if (Objects.equals("jdk.internal.loader.ClassLoaders$PlatformClassLoader", c.getClass().getName())) {

        }
        Assertions.assertInstanceOf(URLClassLoader.class, ClassLoader.getPlatformClassLoader());
        Assertions.assertInstanceOf(URLClassLoader.class, ClassLoader.getSystemClassLoader());
        String jarPath = "/tmp/DEMO_NOT_EXISTED.jar";
        String classPathSysProp1 = System.getProperty("java.class.path");
        String classPathValue1 = new ClassGraph()
                .getClasspath();
        Assertions.assertFalse(classPathSysProp1.contains(jarPath));
        Assertions.assertFalse(classPathValue1.contains(jarPath));

        {
//            MyClassLoader myClassLoader = new MyClassLoader(new URL[]{}, ClassLoader.getPlatformClassLoader());
//
//            URL jarUrl = new File(jarPath).toURI().toURL();
//            myClassLoader.addURL(jarUrl);
        }
        ClassLoader cl = ClassLoader.getPlatformClassLoader();

        String classPathSysProp2 = System.getProperty("java.class.path");
        String classPathValue2 = new ClassGraph()
                .getClasspath();

        Assertions.assertEquals(classPathSysProp1, classPathSysProp2);
        Assertions.assertTrue(classPathValue2.contains(jarPath));


        ClassLoaderTest.printClassPath(classPathValue2);
    }

//    public static class MyClassLoader extends URLClassLoader {
//
//        public MyClassLoader(URL[] urls, ClassLoader parent) {
//            super(urls, parent);
//        }
//
//        public MyClassLoader(URL[] urls) {
//            super(urls);
//        }
//
//        public MyClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
//            super(urls, parent, factory);
//        }
//
//        public MyClassLoader(String name, URL[] urls, ClassLoader parent) {
//            super(name, urls, parent);
//        }
//
//        public MyClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
//            super(name, urls, parent, factory);
//        }
//
//        @Override
//        public void addURL(URL url) {
//            ((URLClassLoader)getParent()).addURL(url);
//        }
//    }
}
