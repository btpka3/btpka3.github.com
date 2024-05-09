package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 只验证 OpenTelemetry 的 Context 机制
 *
 * @author dangqian.zll
 * @date 2024/5/7
 */
public class ContextTest {

    private String key = "k";
    ContextKey contextKey = ContextKey.named(key);

    @Test
    public void test101() {

        ContextKey<String> key1 = ContextKey.named("a");
        ContextKey<String> key2 = ContextKey.named("a");

        Context context1 = Context.current();

        Context context2 = context1.with(key1, "a01");

        Assertions.assertNotSame(context1, context2);
        Assertions.assertNull(context1.get(key1));
        Assertions.assertEquals("a01", context2.get(key1));
        Assertions.assertNull(context2.get(key2));
    }

    @Test
    public void test01() throws InterruptedException {

        System.out.println("===================== MyTraceService#exec");

        // 注意：必须重新赋值
        Context context = Context.current()
                .with(contextKey, "test01.1");

        System.out.println("test01.1 :" + threadName() + ": " + key + "=" + context.get(contextKey));
        try (Scope scope = context.makeCurrent()) {
            System.out.println("test01.2 :" + threadName() + ": " + key + "=" + context.get(contextKey));

            // 演示同步执行
            b01();

            // 演示如何切换线程上下文
            Context context2 = context.with(contextKey, "test01.2");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try (Scope scope = context2.makeCurrent()) {
                        b02();
                    }
                }
            });
            thread.start();

            System.out.println("test01.3 :" + threadName() + ": " + key + "=" + context.get(contextKey));

            // 演示同步执行
            b01();
        }
        System.out.println("test01.4 :" + threadName() + ": " + threadName() + ": " + key + "=" + context.get(contextKey));

        // 等待输出完成
        Thread.sleep(3 * 1000);
    }

    protected void b01() {
        Context context = Context.current();
        System.out.println("b01.1 :" + threadName() + ": " + key + "=" + context.get(contextKey));

        try (Scope scope = context.makeCurrent()) {
            context = context.with(contextKey, "a02");
            System.out.println("b01.1 :" + threadName() + ": " + key + "=" + context.get(contextKey));
        }
    }

    protected void b02() {
        Context context = Context.current();
        System.out.println("b02.1 :" + threadName() + ": " + key + "=" + context.get(contextKey));

        try (Scope scope = context.makeCurrent()) {
            context = context.with(contextKey, "a02");
            System.out.println("b02.1 :" + threadName() + ": " + key + "=" + context.get(contextKey));
        }
    }

    public String threadName() {
        return Thread.currentThread().getName();
    }
}
