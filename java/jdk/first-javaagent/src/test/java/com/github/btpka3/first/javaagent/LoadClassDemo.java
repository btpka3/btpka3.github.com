package com.github.btpka3.first.javaagent;

import java.util.function.Consumer;

/**
 * @author dangqian.zll
 * @date 2024/10/29
 * @see ClassLoader#loadClass(String)
 * @see ClassLoader#loadClass(String, boolean)
 */
public class LoadClassDemo {

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        MethodCallCtx ctx = new MethodCallCtx();
        ctx.setAt(System.currentTimeMillis());
        ctx.setStartNanoTime(System.nanoTime());
        try {
            return loadClass(name, false);
        } catch (ClassNotFoundException e) {
            ctx.setErr(e);
            throw e;
        } finally {
            ctx.setEndNanoTime(System.nanoTime());
            Consumer<MethodCallCtx> consumer = null;
            consumer.accept(ctx);
        }
    }

    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        return null;
    }
}
