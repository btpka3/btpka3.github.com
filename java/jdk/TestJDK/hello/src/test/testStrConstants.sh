#!/bin/bash


#printf ${i}'%.0s' {1..1024}
#
#
#for i in {A..Z}
#do
#    export a=`printf ${i}'%.0s' {1..1024}`
#done
#
#
#
#mkdir -p me/test/test1
#mkdir -p me/test/test2
#
#a=`printf 'A%.0s' {1..1024}`

mkdir -p /tmp/testStrConstants
rm -fr /tmp/testStrConstants/*
cd /tmp/testStrConstants

mkdir -p me/test/test1
mkdir -p me/test/test2

cat > me/test/A.java <<EOF
package me.test;
public class A {
    public static String a = "${a}";
    public static void main(String[]args){
        System.out.println(a);
    }
}
EOF

cat > me/test/test1/Test1.java <<EOF
package me.test.test1;
import me.test.A;

public class Test1 {
    public static void main(String[]args){
        System.out.println(A.a);
    }
}
EOF

cat > me/test/test2/Test2.java <<EOF
package me.test.test2;
import me.test.*;

public class Test2 {
    public static void main(String[]args){
        System.out.println("${a}");
    }
}
EOF

javac `find . -type f -name "*.java"`

tree

# 结论：每个class文件中都有一部分存储常量，通过静态常量引用的方式，能明显减少不必要
ls -l `find . -type f -name "*.class"`
#-rw-r--r--  1 zll  wheel  1527 Nov  9 18:52 ./me/test/A.class
#-rw-r--r--  1 zll  wheel   463 Nov  9 18:52 ./me/test/test1/Test1.class
#-rw-r--r--  1 zll  wheel  1442 Nov  9 18:52 ./me/test/test2/Test2.class


#ls -l `find . -type f -name "*.java"`

