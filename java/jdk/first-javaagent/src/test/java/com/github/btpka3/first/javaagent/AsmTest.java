package com.github.btpka3.first.javaagent;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author dangqian.zll
 * @date 2024/10/28
 */
public class AsmTest {

    @SneakyThrows
    @Test
    public void parseClass() {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader(HelloDemo.class.getName());
        cr.accept(cp, 0);
    }

    /**
     * 演示如何生成一个新的class。
     * <code><pre>
     * package pkg;
     * public interface Comparable extends Mesurable {
     *   int LESS = -1;
     *   int EQUAL = 0;
     *   int GREATER = 1;
     *   int compareTo(Object o);
     * }
     * </pre></code>
     */
    @Test
    public void generateClass() {


        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[]{"pkg/Mesurable"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();
    }

    @Test
    public void transformingClass() {
        byte[] b1 = null;
        ClassWriter cw = new ClassWriter(0);
        // cv forwards all events to cw
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {};
        // ClassVisitor cv = new ChangeVersionAdapter();
        ClassReader cr = new ClassReader(b1);
        cr.accept(cv, 0);
        byte[] b2 = cw.toByteArray(); // b2 represents the same class as b1

    }

    public class ChangeVersionAdapter extends ClassVisitor {
        public ChangeVersionAdapter(ClassVisitor cv) {
            super(ASM4, cv);
        }
        @Override
        public void visit(int version, int access, String name,
                          String signature, String superName, String[] interfaces) {
            cv.visit(V1_5, access, name, signature, superName, interfaces);
        }
    }

    public class ClassPrinter extends ClassVisitor {
        public ClassPrinter() {
            super(ASM4);
        }

        public void visit(int version, int access, String name,
                          String signature, String superName, String[] interfaces) {
            System.out.println(name + " extends " + superName + " {");
        }

        public void visitSource(String source, String debug) {
        }

        public void visitOuterClass(String owner, String name, String desc) {
        }

        public AnnotationVisitor visitAnnotation(String desc,
                                                 boolean visible) {
            return null;
        }

        public void visitAttribute(Attribute attr) {
        }

        public void visitInnerClass(String name, String outerName,
                                    String innerName, int access) {
        }

        public FieldVisitor visitField(int access, String name, String desc,
                                       String signature, Object value) {
            System.out.println(" " + desc + " " + name);
            return null;
        }

        public MethodVisitor visitMethod(int access, String name,
                                         String desc, String signature, String[] exceptions) {
            System.out.println(" " + name + desc);
            return null;
        }

        public void visitEnd() {
            System.out.println("}");
        }
    }
}




