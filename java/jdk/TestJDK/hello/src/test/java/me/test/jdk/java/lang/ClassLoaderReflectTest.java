package me.test.jdk.java.lang;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author dangqian.zll
 * @date 2025/5/27
 */
public class ClassLoaderReflectTest {

    @SneakyThrows
    public static void main(String[] args) {

        ClassLoader c = ClassLoader.getPlatformClassLoader();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], c);
        urlClassLoader.getURLs();

        Class<?> classLoaderClass = ClassLoader.class;
        Field parentField = classLoaderClass.getDeclaredField("parent");
        parentField.setAccessible(true);
        Object parentCl = parentField.get(urlClassLoader);

        Assertions.assertSame(c, parentCl);
        System.out.println("Done.");
    }
}
