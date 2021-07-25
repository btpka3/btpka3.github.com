package me.test.jdk.java.lang;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2021/7/25
 * @see <a href="http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html">Java Reflection - Dynamic Class Loading and Reloading</a>
 */
public class ClassLoaderTest {

    String className = "me.test.jdk.java.lang.MyChildClass";

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
}
