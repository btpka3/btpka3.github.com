package me.test.first.spring.boot.web.service;


import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;


// https://stackoverflow.com/a/28032097/533317
public class AssertUtils<T extends RuntimeException> {


    private Constructor<T> exceptionConstructor;

    public AssertUtils(Class<T> exceptionClass) {
        try {
            this.exceptionConstructor = exceptionClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("需要有个参数为 String 的构造函数");
        }
    }


    private RuntimeException newException(String msg) {
        try {
            return this.exceptionConstructor.newInstance(msg);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("实例化异常失败", e);
        }
    }

    public void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw newException(nullSafeGet(messageSupplier));
        }
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }
}
