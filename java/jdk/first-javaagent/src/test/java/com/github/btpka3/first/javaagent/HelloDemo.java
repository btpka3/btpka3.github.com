package com.github.btpka3.first.javaagent;

import java.util.function.Consumer;

/**
 * @author dangqian.zll
 * @date 2024/10/29
 */
public class HelloDemo {

    public String hello(String name) throws ClassNotFoundException {
        return hello(name, false);
    }


    protected String hello(String name, boolean resolve)
            throws ClassNotFoundException {
        return "hello " + name + ", " + resolve;
    }

    public String demoModifiedHello(String name) throws ClassNotFoundException {
        MethodCallCtx ctx = new MethodCallCtx();
        ctx.setAt(System.currentTimeMillis());
        ctx.setStartNanoTime(System.nanoTime());
        try {
            return hello(name, false);
        } catch (ClassNotFoundException e) {
            ctx.setErr(e);
            throw e;
        } finally {
            ctx.setEndNanoTime(System.nanoTime());
            Consumer<MethodCallCtx> consumer = null;
            consumer.accept(ctx);
        }
    }

}
