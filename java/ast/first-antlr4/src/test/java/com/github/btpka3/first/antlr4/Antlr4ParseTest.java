package com.github.btpka3.first.antlr4;


import com.github.btpka3.first.antlr4.java9.Java9Lexer;
import com.github.btpka3.first.antlr4.java9.Java9Parser;
import org.antlr.v4.gui.TestRig;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

import java.io.InputStream;

public class Antlr4ParseTest {


    @Test
    public void testParseJava1() throws Exception {

        InputStream in = AstApp.class.getResourceAsStream("/HelloWorld.java");

        // Create a scanner that reads from the input stream passed to us
        Lexer lexer = new Java9Lexer(CharStreams.fromStream(in));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser that reads from the scanner
        Java9Parser parser = new Java9Parser(tokens);
//        parser.addErrorListener(new DiagnosticErrorListener());
//        parser.setErrorHandler(new BailErrorStrategy());
//        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

        // start parsing at the compilationUnit rule
        ParserRuleContext t = parser.compilationUnit();
//        parser.setBuildParseTree(false);

        System.out.println(t.toStringTree(parser));


        // TODO 解析完成后，可以自己遍历语法树，进行相应的分析. FIXME : 能否进行修改？
    }

    /**
     * 通过 GUI 的形式展示
     *
     * @throws Exception
     */
    @Test
    public static void testParseJavaGui() throws Exception {
        TestRig.main(new String[]{
                "com.github.btpka3.first.antlr4.java9.Java9",
                "compilationUnit",
                "-gui",
                "/data0/work/git-repo/github/btpka3/btpka3.github.com/java/ast/first-antlr4/src/main/resources/HelloWorld.java"
        });
    }
}
