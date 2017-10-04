package io.github.btpka3.asm;

import org.apache.commons.io.*;
import org.objectweb.asm.*;

import java.io.*;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorldClassGenerator {


    public static void genConstructor(ClassWriter cw) throws IOException {
        MethodVisitor mv = cw.visitMethod(
                ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null
        );

        mv.visitEnd();
    }

    public static void genMain(ClassWriter cw) throws IOException {
        MethodVisitor mv = cw.visitMethod(
                ACC_PUBLIC + ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null
        );

        mv.visitFieldInsn(
                GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");

        mv.visitEnd();
    }

    public static void main(String[] args) throws IOException {


        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(
                V1_8,
                ACC_PUBLIC + ACC_SUPER,
                "io/github/btpka3/asm/Hi",
                null,
                "java/lang/Object",
                null
        );

        // 构造函数
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        // main 函数
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Hello World");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();


        // String dirPath = "/data0/work/git-repo/github/btpka3/btpka3.github.com/java/bytecode/my-bytecode/.tmp";
        String dirPath = ".tmp";
        String filePath = dirPath + "/Hello.class";

        IOUtils.write(cw.toByteArray(), new FileOutputStream(filePath));
        System.out.println(111);
    }


}
