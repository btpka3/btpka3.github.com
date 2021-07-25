package me.test.jdk.java.lang;

import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2021/7/25
 */
public class MyChild1Class extends MyParentClass implements Supplier<Long> {

    @Override
    public Long get() {
        return System.currentTimeMillis();
    }
}
