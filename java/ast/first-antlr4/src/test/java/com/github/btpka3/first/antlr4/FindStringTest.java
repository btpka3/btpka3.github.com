package com.github.btpka3.first.antlr4;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FindStringTest {

    // 替换
    // 1. 检查并 import MessageSource, Filter
    // 1. 检查并 新增字段 @Autowired MessageSource messageSource
    // 1. 中文字符串 替换 方法调用。


    @Test
    public void visitClassMethod() throws IOException {
        InputStream in = AstApp.class.getResourceAsStream("Test.java");

        //System.out.println(IOUtils.toString(in,"UTF-8"));
        CompilationUnit cu = JavaParser.parse(in);

        System.out.println(cu.toString());

        System.out.println("com.github.btpka3.first.antlr4.FindStringTest.visitClassMethod(FindStringTest.java:25)");
        System.out.println("com.github.btpka3.first.antlr4.Test.getTime(Test.java:13)");
        System.out.println("com.github.btpka3.first.antlr4.Test.getTime(com/github/btpka3/first/antlr4/Test.java:13)");
        //System.out.println("com(com/github/btpka3/first/antlr4/Test.java:13)");

        // visit and change the methods names and parameters
        cu.accept(new MyExprVisitor(), null);

        System.out.println("===================================");
        System.out.println(cu);

    }

    public static void log(StringLiteralExpr n) {
        System.out.println("------------- " + n.getValue());
        System.out.println("@" + n.getBegin().get().line);
        n.findParent(ClassOrInterfaceDeclaration.class)
                .ifPresent(c -> System.out.println(c.getName()));
//        ClassOrInterfaceDeclaration declarationType = getDeclareLocation(n);
//        if(declarationType!=null){
//            System.out.println("@ " + n.findCompilationUnit().get().getPackageDeclaration().get().getName());
//        }
    }


    // FIXME: lambda 表达式的类是？类嵌套的情形。
    public static ClassOrInterfaceDeclaration getDeclareLocation(Expression n) {

        Node e = n;
        while (e != null) {
            if (e instanceof ClassOrInterfaceDeclaration) {
                return (ClassOrInterfaceDeclaration) e;
            }
            if (e instanceof Expression) {
                e = e.getParentNode().orElse(null);
            }
        }

        return null;
    }

    public static List<Expression> toList(Expression e) {

        List<Expression> list = new ArrayList<>();
        if (e instanceof BinaryExpr) {
            BinaryExpr n = (BinaryExpr) e;

            list.addAll(toList(n.getLeft()));
            list.addAll(toList(n.getRight()));
        } else {
            list.add(e);
        }
        return list;
    }


    public static MethodCallExpr toMethodCall(BinaryExpr n, String msgVal) {
        List<Expression> list = toList(n);

        NameExpr clazz = new NameExpr("this");
        FieldAccessExpr field = new FieldAccessExpr(clazz, "messageSource");
        MethodCallExpr call = new MethodCallExpr(field, "getMessage");
        call.addArgument(
                new StringLiteralExpr(msgVal)
        );

        list.forEach(e -> {
            if (!(e instanceof StringLiteralExpr)) {
                call.addArgument(e);
            }
        });
        return call;
    }

    public static String toMsgVal(BinaryExpr n) {
        List<Expression> list = toList(n);


        AtomicInteger idx = new AtomicInteger(0);
        StringBuilder buf = new StringBuilder();

        list.forEach(e -> {
            if (e instanceof StringLiteralExpr) {
                buf.append(((StringLiteralExpr) e).getValue());
            } else {
                buf.append("{");
                buf.append(idx.getAndAdd(1));
                buf.append("}");
            }
        });
        return buf.toString();

    }


    private static class MyExprVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(final BinaryExpr n, final Void arg) {

            if (isCnStringPlus(n)) {
                System.out.println("------------- " + n);
                String msgVal = toMsgVal(n);
                System.out.println("to msg value : " + msgVal);


                n.getParentNode().ifPresent(parentNode -> {
                    // 修改为方法调用
                    MethodCallExpr call = toMethodCall(n, msgVal);
                    parentNode.replace(n, call);
//                    List<Node> childNodes = parentNode.getChildNodes();
//
//                    int idx = childNodes.indexOf(n);
//                    childNodes.remove(idx);
//                    if (idx >= 0) {
//
//                        childNodes.add(idx, call);
//                    }
                });
            }
        }


        public void visit(final StringLiteralExpr n, final Void arg) {
            log(n);
        }

    }

    public static boolean isCnStringResult(Expression n) {

        if (n instanceof BinaryExpr && isCnStringPlus((BinaryExpr) n)) {
            return true;
        }

        if (n instanceof StringLiteralExpr && isChinese(((StringLiteralExpr) n).getValue())) {
            return true;
        }

        return false;

    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }


    public static boolean isCnStringPlus(BinaryExpr n) {
        if (BinaryExpr.Operator.PLUS != n.getOperator()) {
            return false;
        }

        if (isCnStringResult(n.getLeft())) {
            return true;
        }

        if (isCnStringResult(n.getRight())) {
            return true;
        }

        return false;
    }

}
