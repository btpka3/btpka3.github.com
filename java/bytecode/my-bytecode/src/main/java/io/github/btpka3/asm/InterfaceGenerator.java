package io.github.btpka3.asm;

import org.apache.commons.io.*;
import org.objectweb.asm.*;

import java.io.*;

import static org.objectweb.asm.Opcodes.*;

public class InterfaceGenerator {

    public static void main(String[] args) throws IOException {

        ClassWriter cw = new ClassWriter(0);

        // 通过visit方法确定类的头部信息
        cw.visit(
                Opcodes.V1_8,
                ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "me/test/Comparable",
                null,
                "java/lang/Object",
                new String[]{"me/test/Mesurable"}
        );

        // 定义一个常量属性
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();


        // 定义方法
        cw.visitMethod(
                ACC_PUBLIC + ACC_ABSTRACT,
                "compareTo",
                "(Ljava/lang/Object;)I",
                null,
                null
        ).visitEnd();


        // 使cw类已经完成
        cw.visitEnd();
        // 将cw转换成字节数组写到文件里面去


        String dirPath = "/data0/work/git-repo/github/btpka3/btpka3.github.com/java/bytecode/my-bytecode/.tmp";
        String filePath = dirPath + "/Hello.class";

        IOUtils.write(cw.toByteArray(), new FileOutputStream(filePath));
        System.out.println(111);
    }
}
