package com.github.btpka3.my.maven.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * @author dangqian.zll
 * @date 2022/3/23
 */
public class NpeFinderAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain start: agentArgs=" + agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain start: agentArgs=" + agentArgs);
    }

//    public static void main(String[] args) {
//        //VirtualMachine vm = VirtualMachine.a
//    }

    private static void transformClass(String className, Instrumentation instrumentation) {
        Class<?> targetCls = null;
        ClassLoader targetClassLoader = null;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, instrumentation);
            return;
        } catch (Exception ex) {
            System.err.println("Class [{}] not found with Class.forName");
        }
        // otherwise iterate all loaded classes and find what we want
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                targetCls = clazz;
                targetClassLoader = targetCls.getClassLoader();
                transform(targetCls, targetClassLoader, instrumentation);
                return;
            }
        }
        throw new RuntimeException(
                "Failed to find class [" + className + "]");
    }

    private static void transform(
            Class<?> clazz,
            ClassLoader classLoader,
            Instrumentation instrumentation
    ) {
        AtmTransformer dt = new AtmTransformer(clazz.getName(), classLoader);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Transform failed for: [" + clazz.getName() + "]", ex);
        }
    }

}
