package com.github.btpka3.first.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 * @author dangqian.zll
 * @date 2023/8/15
 */
@Slf4j
public class WatchSetTccl implements ClassFileTransformer {

    protected static final String TARGET_CLASS = "java.lang.Thread";
    protected static final String TARGET_METHOD = "setContextClassLoader";


    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer
    ) throws IllegalClassFormatException {


        if (!Objects.equals(TARGET_CLASS, className)) {
            return classfileBuffer;
        }

        byte[] byteCode = classfileBuffer;
        log.warn("[Agent] Transforming class WatchSetTcclThread");
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(TARGET_CLASS);
            CtMethod m = cc.getDeclaredMethod(TARGET_METHOD);
            MethodInfo methodInfo = m.getMethodInfo();
            LocalVariableAttribute table = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);
            int numberOfLocalVariables = table.tableLength();
            {
                m.addLocalVariable("startTime", CtClass.longType);
                m.insertBefore("startTime = System.currentTimeMillis();");
            }
            Thread.currentThread().setContextClassLoader();
            {
                m.addLocalVariable("endTime", CtClass.longType);
                m.addLocalVariable("opTime", CtClass.longType);
                StringBuilder endBlock = new StringBuilder();
                endBlock.append("endTime = System.currentTimeMillis();");
                endBlock.append("opTime = (endTime-startTime)/1000;");
                endBlock.append("LOGGER.info(\"[Application] Withdrawal operation completed in:\" + opTime + \" seconds!\");");
                m.insertAfter(endBlock.toString());
            }

            byteCode = cc.toBytecode();
            cc.detach();
        } catch (Throwable e) {
            log.error("Exception", e);
        }
        return byteCode;


    }


//    private static void transformClass(String className, Instrumentation instrumentation) {
//        Class<?> targetCls = null;
//        ClassLoader targetClassLoader = null;
//        // see if we can get the class using forName
//        try {
//            targetCls = Class.forName(className);
//            targetClassLoader = targetCls.getClassLoader();
//            transform(targetCls, targetClassLoader, instrumentation);
//            return;
//        } catch (Exception ex) {
//            System.err.println("Class [{}] not found with Class.forName");
//        }
//        // otherwise iterate all loaded classes and find what we want
//        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
//            if (clazz.getName().equals(className)) {
//                targetCls = clazz;
//                targetClassLoader = targetCls.getClassLoader();
//                transform(targetCls, targetClassLoader, instrumentation);
//                return;
//            }
//        }
//        throw new RuntimeException(
//                "Failed to find class [" + className + "]");
//    }


    @SneakyThrows
    public byte[] transform1(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer
    ) {
        byte[] byteCode = classfileBuffer;
        if (className.equals("xxx") && loader.equals(null)) {

            log.info("[Agent] Transforming class MyAtm");
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(TARGET_CLASS);
                CtMethod m = cc.getDeclaredMethod(TARGET_METHOD);

                {
                    m.addLocalVariable("startTime", CtClass.longType);
                    m.insertBefore("startTime = System.currentTimeMillis();");
                }
                {
                    m.addLocalVariable("endTime", CtClass.longType);
                    m.addLocalVariable("opTime", CtClass.longType);
                    StringBuilder endBlock = new StringBuilder();
                    endBlock.append("endTime = System.currentTimeMillis();");
                    endBlock.append("opTime = (endTime-startTime)/1000;");
                    endBlock.append("LOGGER.info(\"[Application] Withdrawal operation completed in:\" + opTime + \" seconds!\");");
                    m.insertAfter(endBlock.toString());
                }

                byteCode = cc.toBytecode();
                cc.detach();
            } catch (Throwable e) {
                log.error("Exception", e);
            }
        }
        return byteCode;
    }

//    private static void transform(
//            Class<?> clazz,
//            ClassLoader classLoader,
//            Instrumentation instrumentation
//    ) {
//        AtmTransformer dt = new AtmTransformer(clazz.getName(), classLoader);
//        instrumentation.addTransformer(dt, true);
//        try {
//            instrumentation.retransformClasses(clazz);
//        } catch (Exception ex) {
//            throw new RuntimeException(
//                    "Transform failed for: [" + clazz.getName() + "]", ex);
//        }
//    }
}
