package com.github.btpka3.first.javaagent;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.bind.annotation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * @author dangqian.zll
 * @date 2024/10/28
 * @see <a href="https://bytebuddy.net/">byte-buddy</a>
 */
public class ByteBuddyTest {


    public void modifyClassLoaderLoadClass() {
        Instrumentation instrumentation = null;
        new AgentBuilder.Default()
                .type(named("java.lang.ClassLoader"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.method(named("loadClass").and(takesArguments(String.class)))
                                .intercept(Advice.to(MethodCallInterceptor.class))
                )
                .installOn(instrumentation);
    }

    static class MethodCallInterceptor {
        @Advice.OnMethodEnter
        public static long enter() {
            return System.nanoTime();
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class)
        public static void exit(@Advice.Enter long start, @Advice.Origin String origin) {
            long duration = System.nanoTime() - start;
            // 这里可以记录到日志或者数据库等
            System.out.println("Called " + origin + ", took " + duration / 1000000.0 + " ms");
        }
    }


    public void modifyHello() {
        Instrumentation instrumentation = null;
        new AgentBuilder.Default()
                .type(named(HelloDemo.class.getName()))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder
                                //.require()
                                .method(named("hello").and(takesArguments(String.class)))
                                .intercept(Advice.to(HelloInterceptor.class))
                )
                .installOn(instrumentation);
    }

    static class HelloInterceptor {
        @RuntimeType
        String foo(
                @Argument(0) String str,
                @AllArguments Object[] args,
                @This HelloDemo thisObject,
                @Super HelloDemo superObject,
                @Origin Method targetMethod,
                @SuperCall Callable<String> o4,
                @DefaultCall Object o6,
                @Pipe Object o7
        ) {
            // https://bytebuddy.net/#/tutorial

            try {
                return o4.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    @SneakyThrows
    public void helloWorld() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        System.out.println("====dynamicType:" + dynamicType);
        Assertions.assertEquals("Hello World!", dynamicType.newInstance().toString());
    }
}
