学习/温习 Groovy


ast

```

# 一起编译的话，将不会执行
mkdir /tmp/c
groovyc -d /tmp/c src/me/test/groovy/ast/* 
java -cp $GROOVY_HOME/embeddable/groovy-all-2.4.7.jar:/tmp/c me.test.groovy.ast.MyForm

# 需要先将 AST 代码编译，再讲使用AST的代码编译，才会执行
# 参考: http://stackoverflow.com/questions/14267694/groovy-ast-transformation-does-not-get-applied-during-grails-compile-only-durin
rm -fr /tmp/c/*
groovyc -d /tmp/c src/me/test/groovy/ast/MapGetterSetter.groovy src/me/test/groovy/ast/MapGetterSetterAST.groovy
groovyc -cp /tmp/c  -d /tmp/c src/me/test/groovy/ast/MyForm.groovy src/me/test/groovy/ast/AbstractForm.groovy
java -cp $GROOVY_HOME/embeddable/groovy-all-2.4.7.jar:/tmp/c me.test.groovy.ast.MyForm

# 注意：
可以通过 Script 的方式执行
```


# javac hello-world

B.java
```java
package me;
public class B{
  
  public static String hi(){
    return "Hi";
  }
}
```

A.java
```java
package me;
public class A{
  public static void main(String[]args){
    System.out.println("hello world ~ " + B.hi());
  }
}
```

```bash
mkdir aaa bbb
javac -d bbb B.java
javac -cp bbb -d aaa A.java
java -cp aaa:bbb me.A
```