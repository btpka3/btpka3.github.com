

## 参考
- [antlr4](https://github.com/antlr/antlr4)
- [antlr4 grammars](https://github.com/antlr/grammars-v4)
- [javaparser](http://javaparser.org/)
- [lombok.ast](https://github.com/rzwitserloot/lombok.ast)
- [groovy ast](http://docs.groovy-lang.org/2.4.7/html/api/index.html?org/codehaus/groovy/ast/package-summary.html)
- [typescript ast](https://github.com/Microsoft/TypeScript/wiki/Using-the-Compiler-API#creating-and-printing-a-typescript-ast)
- [javascript : babel-parser](https://github.com/babel/babel/blob/master/packages/babel-parser/ast/spec.md)
- [astexplorer.net](https://astexplorer.net/)

## study

1. 下载 antlr4

    ```sh
    cd /usr/local/lib
    sudo curl -O https://www.antlr.org/download/antlr-4.7.1-complete.jar
    export CLASSPATH=".:/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH"
    alias antlr4='java -jar /usr/local/lib/antlr-4.7.1-complete.jar'
    alias grun='java org.antlr.v4.gui.TestRig'
    ```

1. 编写语法文件 `vi Expr.g4` :

    ```g4
    grammar Expr;		
    prog:	(expr NEWLINE)* ;
    expr:	expr ('*'|'/') expr
        |	expr ('+'|'-') expr
        |	INT
        |	'(' expr ')'
        ;
    NEWLINE : [\r\n]+ ;
    INT     : [0-9]+ ;
    ```
1. 根据语法文件生成 Java 文件，并编译：

    ```bash
    antlr4 Expr.g4
    javac Expr*.java
    ```

1. 测试

    ```bash
    echo 100+2*34 > /tmp/test.txt 
    grun Expr prog -gui /tmp/test.txt
    grun Expr prog -tree /tmp/test.txt
    ```

1. 使用既有的语法文件

    ```bash
    # 下载语法文件
    cd $PROJECT_ROOT
    wget -P src/main/resources/ https://raw.githubusercontent.com/antlr/grammars-v4/master/java9/Java9.g4
    
    # 生成
    cd $PROJECT_ROOT
    antlr4 \
        -o src/main/java/com/github/btpka3/first/antlr4/java9 \
        -Xexact-output-dir \
        -package com.github.btpka3.first.antlr4.java9 \
        src/main/resources/Java9.g4 
    ```
 