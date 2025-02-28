package com.github.btpka3.first.javaagent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import lombok.SneakyThrows;

/**
 * @author dangqian.zll
 * @date 2024/10/28
 */
public class JavassistTest {

    @SneakyThrows
    public void changeSuperClass() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.setSuperclass(pool.get("test.Point"));
        cc.writeFile();
    }

    @SneakyThrows
    public void newClass() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Point");
    }

    @SneakyThrows
    public void insertBefore() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("Point");
        CtMethod m = cc.getDeclaredMethod("move");
        m.insertBefore("{ System.out.println($1); System.out.println($2); }");
        cc.writeFile();
    }

    @SneakyThrows
    public void replaceMethod() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("Point");
        CtMethod cm = cc.getDeclaredMethod("move");
        cm.instrument(
                new ExprEditor() {
                    public void edit(MethodCall m)
                            throws CannotCompileException {
                        if (m.getClassName().equals("Point")
                                && m.getMethodName().equals("move"))
                            m.replace("{ $1 = 0; $_ = $proceed($$); }");
                    }
                });
    }


}
