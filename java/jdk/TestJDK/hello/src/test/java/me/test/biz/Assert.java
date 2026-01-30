package me.test.biz;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class Assert<T extends RuntimeException> {


    private static final Map<Class<RuntimeException>, Assert<RuntimeException>> instances
            = new LinkedHashMap<>();


    public static Assert<RuntimeException> getInstance() {
        return getInstance(RuntimeException.class);
    }

    public static <E extends RuntimeException> Assert<E> getInstance(Class<E> clazz) {
        if (!instances.containsKey(clazz)) {

            synchronized (Assert.class) {
                if (!instances.containsKey(clazz)) {
                    Assert<E> a = new Assert<>(clazz);
                    instances.put((Class<RuntimeException>) clazz, (Assert<RuntimeException>) a);
                }
            }
        }
        return (Assert<E>) instances.get(clazz);

    }

    private Class<T> exceptionClazz;
    private Constructor<T> exceptionConstructor;


    public Assert(Class<T> clazz) {
        this.exceptionClazz = clazz;

        try {
            exceptionConstructor = clazz.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("RuntimeException clazz 必须有一个仅接收 String 参数的构造函数");
        }
    }

    private T newException(String msg) {
        try {
            return exceptionConstructor.newInstance(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void isTrue(boolean expr, String msg) {
        if (expr) {
            return;
        }
        throw newException(msg);
    }

    public static void main(String[] args) {


        try {
            Assert a = Assert.getInstance(IllegalArgumentException.class);
            a.isTrue(false, "aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Assert a = Assert.getInstance();
            a.isTrue(false, "bbb");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
