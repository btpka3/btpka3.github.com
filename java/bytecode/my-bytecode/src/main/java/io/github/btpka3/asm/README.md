


# 参考

-   [The class File Format](https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.1)



# 准备

## Hi.java

```java
package io.github.btpka3.asm;

public class Hi {

    private static String name = "zhang" + new String("3");

    public static void main(String[] args) {
        String newName = name + "4";
        System.out.println("Hello " + newName + "  " + Math.max(101, 202));
    }
}
```

## hexdump

```text
0000000 ca fe ba be 00 00 00 34 00 48 0a 00 13 00 25 07
        ########### magic number，指明该文件是java文件
                    ##### minor version 
                          ##### major version. 52 
                                ##### constant_pool_count. 71 条记录 (0x48 为72)
                                      ## 常量池条目1的类型 - CONSTANT_Methodref
                                         ##             CONSTANT_Methodref_info.tag  (big-endian) 0x0013=19 
                                            #####       CONSTANT_Methodref_info.class_index 1829
                                                  ##### CONSTANT_Methodref_info.name_and_type_index
0000010 00 26 0a 00 02 00 25 09 00 12 00 27 0a 00 02 00
0000020 28 08 00 29 0a 00 02 00 2a 09 00 2b 00 2c 08 00
0000030 2d 08 00 2e 0a 00 2f 00 30 0a 00 02 00 31 0a 00
0000040 32 00 33 08 00 34 07 00 35 08 00 36 0a 00 0f 00
0000050 37 07 00 38 07 00 39 01 00 04 6e 61 6d 65 01 00
0000060 12 4c 6a 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69
0000070 6e 67 3b 01 00 06 3c 69 6e 69 74 3e 01 00 03 28
0000080 29 56 01 00 04 43 6f 64 65 01 00 0f 4c 69 6e 65
0000090 4e 75 6d 62 65 72 54 61 62 6c 65 01 00 12 4c 6f
00000a0 63 61 6c 56 61 72 69 61 62 6c 65 54 61 62 6c 65
00000b0 01 00 04 74 68 69 73 01 00 19 4c 69 6f 2f 67 69
00000c0 74 68 75 62 2f 62 74 70 6b 61 33 2f 61 73 6d 2f
00000d0 48 69 3b 01 00 04 6d 61 69 6e 01 00 16 28 5b 4c
00000e0 6a 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69 6e 67
00000f0 3b 29 56 01 00 04 61 72 67 73 01 00 13 5b 4c 6a
0000100 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 3b
0000110 01 00 07 6e 65 77 4e 61 6d 65 01 00 08 3c 63 6c
0000120 69 6e 69 74 3e 01 00 0a 53 6f 75 72 63 65 46 69
0000130 6c 65 01 00 07 48 69 2e 6a 61 76 61 0c 00 16 00
0000140 17 01 00 17 6a 61 76 61 2f 6c 61 6e 67 2f 53 74
0000150 72 69 6e 67 42 75 69 6c 64 65 72 0c 00 14 00 15
0000160 0c 00 3a 00 3b 01 00 01 34 0c 00 3c 00 3d 07 00
0000170 3e 0c 00 3f 00 40 01 00 06 48 65 6c 6c 6f 20 01
0000180 00 02 20 20 07 00 41 0c 00 42 00 43 0c 00 3a 00
0000190 44 07 00 45 0c 00 46 00 47 01 00 05 7a 68 61 6e
00001a0 67 01 00 10 6a 61 76 61 2f 6c 61 6e 67 2f 53 74
00001b0 72 69 6e 67 01 00 01 33 0c 00 16 00 47 01 00 17
00001c0 69 6f 2f 67 69 74 68 75 62 2f 62 74 70 6b 61 33
00001d0 2f 61 73 6d 2f 48 69 01 00 10 6a 61 76 61 2f 6c
00001e0 61 6e 67 2f 4f 62 6a 65 63 74 01 00 06 61 70 70
00001f0 65 6e 64 01 00 2d 28 4c 6a 61 76 61 2f 6c 61 6e
0000200 67 2f 53 74 72 69 6e 67 3b 29 4c 6a 61 76 61 2f
0000210 6c 61 6e 67 2f 53 74 72 69 6e 67 42 75 69 6c 64
0000220 65 72 3b 01 00 08 74 6f 53 74 72 69 6e 67 01 00
0000230 14 28 29 4c 6a 61 76 61 2f 6c 61 6e 67 2f 53 74
0000240 72 69 6e 67 3b 01 00 10 6a 61 76 61 2f 6c 61 6e
0000250 67 2f 53 79 73 74 65 6d 01 00 03 6f 75 74 01 00
0000260 15 4c 6a 61 76 61 2f 69 6f 2f 50 72 69 6e 74 53
0000270 74 72 65 61 6d 3b 01 00 0e 6a 61 76 61 2f 6c 61
0000280 6e 67 2f 4d 61 74 68 01 00 03 6d 61 78 01 00 05
0000290 28 49 49 29 49 01 00 1c 28 49 29 4c 6a 61 76 61
00002a0 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 42 75 69 6c
00002b0 64 65 72 3b 01 00 13 6a 61 76 61 2f 69 6f 2f 50
00002c0 72 69 6e 74 53 74 72 65 61 6d 01 00 07 70 72 69
00002d0 6e 74 6c 6e 01 00 15 28 4c 6a 61 76 61 2f 6c 61
00002e0 6e 67 2f 53 74 72 69 6e 67 3b 29 56 00 21 00 12
00002f0 00 13 00 00 00 01 00 0a 00 14 00 15 00 00 00 03
0000300 00 01 00 16 00 17 00 01 00 18 00 00 00 2f 00 01
0000310 00 01 00 00 00 05 2a b7 00 01 b1 00 00 00 02 00
0000320 19 00 00 00 06 00 01 00 00 00 03 00 1a 00 00 00
0000330 0c 00 01 00 00 00 05 00 1b 00 1c 00 00 00 09 00
0000340 1d 00 1e 00 01 00 18 00 00 00 7c 00 04 00 02 00
0000350 00 00 40 bb 00 02 59 b7 00 03 b2 00 04 b6 00 05
0000360 12 06 b6 00 05 b6 00 07 4c b2 00 08 bb 00 02 59
0000370 b7 00 03 12 09 b6 00 05 2b b6 00 05 12 0a b6 00
0000380 05 10 65 11 00 ca b8 00 0b b6 00 0c b6 00 07 b6
0000390 00 0d b1 00 00 00 02 00 19 00 00 00 0e 00 03 00
00003a0 00 00 08 00 16 00 09 00 3f 00 0a 00 1a 00 00 00
00003b0 16 00 02 00 00 00 40 00 1f 00 20 00 00 00 16 00
00003c0 2a 00 21 00 15 00 01 00 08 00 22 00 17 00 01 00
00003d0 18 00 00 00 37 00 04 00 00 00 00 00 1f bb 00 02
00003e0 59 b7 00 03 12 0e b6 00 05 bb 00 0f 59 12 10 b7
00003f0 00 11 b6 00 05 b6 00 07 b3 00 04 b1 00 00 00 01
0000400 00 19 00 00 00 06 00 01 00 00 00 05 00 01 00 23
0000410 00 00 00 02 00 24                              
0000416
```


## ASMifier

```java
package io.github.btpka3.asm;

import org.objectweb.asm.*;

public class HiDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "io/github/btpka3/asm/Hi", null, "java/lang/Object", null);

        
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_STATIC, "name", "Ljava/lang/String;", null, null);
            fv.visitEnd();
        }
        
        
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        
        
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitFieldInsn(GETSTATIC, "io/github/btpka3/asm/Hi", "name", "Ljava/lang/String;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("4");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("Hello ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("  ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitIntInsn(BIPUSH, 101);
            mv.visitIntInsn(SIPUSH, 202);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "max", "(II)I", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("zhang");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitTypeInsn(NEW, "java/lang/String");
            mv.visitInsn(DUP);
            mv.visitLdcInsn("3");
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitFieldInsn(PUTSTATIC, "io/github/btpka3/asm/Hi", "name", "Ljava/lang/String;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
```

## javap

```txt
// javap --verbose
Classfile /data0/work/git-repo/github/btpka3/btpka3.github.com/java/bytecode/my-bytecode/out/production/classes/io/github/btpka3/asm/Hi.class
  Last modified Oct 7, 2017; size 1046 bytes
  MD5 checksum 47ab09d25de43cc62e85754ed0ccde48
  Compiled from "Hi.java"
public class io.github.btpka3.asm.Hi
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #18                         // io/github/btpka3/asm/Hi
  super_class: #19                        // java/lang/Object
  interfaces: 0, fields: 1, methods: 3, attributes: 1
Constant pool:
   #1 = Methodref          #19.#37        // java/lang/Object."<init>":()V
   #2 = Class              #38            // java/lang/StringBuilder
   #3 = Methodref          #2.#37         // java/lang/StringBuilder."<init>":()V
   #4 = Fieldref           #18.#39        // io/github/btpka3/asm/Hi.name:Ljava/lang/String;
   #5 = Methodref          #2.#40         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   #6 = String             #41            // 4
   #7 = Methodref          #2.#42         // java/lang/StringBuilder.toString:()Ljava/lang/String;
   #8 = Fieldref           #43.#44        // java/lang/System.out:Ljava/io/PrintStream;
   #9 = String             #45            // Hello
  #10 = String             #46            //
  #11 = Methodref          #47.#48        // java/lang/Math.max:(II)I
  #12 = Methodref          #2.#49         // java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
  #13 = Methodref          #50.#51        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #14 = String             #52            // zhang
  #15 = Class              #53            // java/lang/String
  #16 = String             #54            // 3
  #17 = Methodref          #15.#55        // java/lang/String."<init>":(Ljava/lang/String;)V
  #18 = Class              #56            // io/github/btpka3/asm/Hi
  #19 = Class              #57            // java/lang/Object
  #20 = Utf8               name
  #21 = Utf8               Ljava/lang/String;
  #22 = Utf8               <init>
  #23 = Utf8               ()V
  #24 = Utf8               Code
  #25 = Utf8               LineNumberTable
  #26 = Utf8               LocalVariableTable
  #27 = Utf8               this
  #28 = Utf8               Lio/github/btpka3/asm/Hi;
  #29 = Utf8               main
  #30 = Utf8               ([Ljava/lang/String;)V
  #31 = Utf8               args
  #32 = Utf8               [Ljava/lang/String;
  #33 = Utf8               newName
  #34 = Utf8               <clinit>
  #35 = Utf8               SourceFile
  #36 = Utf8               Hi.java
  #37 = NameAndType        #22:#23        // "<init>":()V
  #38 = Utf8               java/lang/StringBuilder
  #39 = NameAndType        #20:#21        // name:Ljava/lang/String;
  #40 = NameAndType        #58:#59        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #41 = Utf8               4
  #42 = NameAndType        #60:#61        // toString:()Ljava/lang/String;
  #43 = Class              #62            // java/lang/System
  #44 = NameAndType        #63:#64        // out:Ljava/io/PrintStream;
  #45 = Utf8               Hello
  #46 = Utf8
  #47 = Class              #65            // java/lang/Math
  #48 = NameAndType        #66:#67        // max:(II)I
  #49 = NameAndType        #58:#68        // append:(I)Ljava/lang/StringBuilder;
  #50 = Class              #69            // java/io/PrintStream
  #51 = NameAndType        #70:#71        // println:(Ljava/lang/String;)V
  #52 = Utf8               zhang
  #53 = Utf8               java/lang/String
  #54 = Utf8               3
  #55 = NameAndType        #22:#71        // "<init>":(Ljava/lang/String;)V
  #56 = Utf8               io/github/btpka3/asm/Hi
  #57 = Utf8               java/lang/Object
  #58 = Utf8               append
  #59 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #60 = Utf8               toString
  #61 = Utf8               ()Ljava/lang/String;
  #62 = Utf8               java/lang/System
  #63 = Utf8               out
  #64 = Utf8               Ljava/io/PrintStream;
  #65 = Utf8               java/lang/Math
  #66 = Utf8               max
  #67 = Utf8               (II)I
  #68 = Utf8               (I)Ljava/lang/StringBuilder;
  #69 = Utf8               java/io/PrintStream
  #70 = Utf8               println
  #71 = Utf8               (Ljava/lang/String;)V
{
  public io.github.btpka3.asm.Hi();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lio/github/btpka3/asm/Hi;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=2, args_size=1
         0: new           #2                  // class java/lang/StringBuilder
         3: dup
         4: invokespecial #3                  // Method java/lang/StringBuilder."<init>":()V
         7: getstatic     #4                  // Field name:Ljava/lang/String;
        10: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        13: ldc           #6                  // String 4
        15: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        18: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        21: astore_1
        22: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
        25: new           #2                  // class java/lang/StringBuilder
        28: dup
        29: invokespecial #3                  // Method java/lang/StringBuilder."<init>":()V
        32: ldc           #9                  // String Hello
        34: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        37: aload_1
        38: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        41: ldc           #10                 // String
        43: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        46: bipush        101
        48: sipush        202
        51: invokestatic  #11                 // Method java/lang/Math.max:(II)I
        54: invokevirtual #12                 // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        57: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        60: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        63: return
      LineNumberTable:
        line 8: 0
        line 9: 22
        line 10: 63
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      64     0  args   [Ljava/lang/String;
           22      42     1 newName   Ljava/lang/String;

  static {};
    descriptor: ()V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=4, locals=0, args_size=0
         0: new           #2                  // class java/lang/StringBuilder
         3: dup
         4: invokespecial #3                  // Method java/lang/StringBuilder."<init>":()V
         7: ldc           #14                 // String zhang
         9: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        12: new           #15                 // class java/lang/String
        15: dup
        16: ldc           #16                 // String 3
        18: invokespecial #17                 // Method java/lang/String."<init>":(Ljava/lang/String;)V
        21: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        24: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        27: putstatic     #4                  // Field name:Ljava/lang/String;
        30: return
      LineNumberTable:
        line 5: 0
}
SourceFile: "Hi.java"
```


