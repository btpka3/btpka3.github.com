package me.test.jdk.jdk.internal.reflect;


/**
 * @author dangqian.zll
 * @date 2025/7/8
 */
public class ReflectionTest {

    public static class Aaa {
        public static void aaa() {
            System.out.println("aaa");
            Bbb.bbb();
        }
    }

    public static class Bbb {
        public static void bbb() {
            // Class clazz = jdk.internal.reflect.Reflection.getCallerClass();
            System.out.println("bbb");
        }
    }
}
