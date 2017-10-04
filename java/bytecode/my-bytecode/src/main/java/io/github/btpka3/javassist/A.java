package io.github.btpka3.javassist;

import javassist.*;

import java.io.*;

public class A {
    public static void main(String[] args)
            throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.setSuperclass(pool.get("test.Point"));
        cc.writeFile();
    }
}
