package com.github.btpka3.first.antlr4;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.junit.Test;

import java.io.InputStream;
import java.util.EnumSet;

import static com.github.javaparser.ast.type.PrimitiveType.intType;

public class JavaParserTest {

    /**
     * Visiting class methods
     */
    @Test
    public void visitClassMethod() {
        InputStream in = AstApp.class.getResourceAsStream("/HelloWorld.java");
        CompilationUnit cu = JavaParser.parse(in);

        // visit and print the methods names
        cu.accept(new MethodVisitor(), null);

        System.out.println(cu.toString());
    }


    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }


    /**
     * change class methods with a visitor
     */
    @Test
    public void visitClassMethodWithVisitor() {
        InputStream in = AstApp.class.getResourceAsStream("/HelloWorld.java");
        CompilationUnit cu = JavaParser.parse(in);

        // visit and change the methods names and parameters
        cu.accept(new MethodChangerVisitor(), null);

        System.out.println(cu.toString());
    }


    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodChangerVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // change the name of the method to upper case
            n.setName(n.getNameAsString().toUpperCase());

            // add a new parameter to the method
            n.addParameter("int", "value");
        }
    }

    /**
     * change class methods without a visitor
     */
    @Test
    public void visitClassMethodWithoutVisitor() {
        InputStream in = AstApp.class.getResourceAsStream("/HelloWorld.java");
        CompilationUnit cu = JavaParser.parse(in);

        // Changing methods from a class without a visitor
        changeMethods(cu);

        System.out.println(cu.toString());
    }

    private static void changeMethods(CompilationUnit cu) {
        // Go through all the types in the file
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        for (TypeDeclaration<?> type : types) {
            // Go through all fields, methods, etc. in this type
            NodeList<BodyDeclaration<?>> members = type.getMembers();
            for (BodyDeclaration<?> member : members) {
                if (member instanceof MethodDeclaration) {
                    MethodDeclaration method = (MethodDeclaration) member;
                    changeMethod(method);
                }
            }
        }
    }

    private static void changeMethod(MethodDeclaration n) {
        // change the name of the method to upper case
        n.setName(n.getNameAsString().toUpperCase());

        // create the new parameter
        n.addParameter(intType(), "value");
    }


    @Test
    public void creatingFromScratch() {
        CompilationUnit cu = new CompilationUnit();
        // set the package
        cu.setPackageDeclaration(new PackageDeclaration(JavaParser.parseName("java.parser.test")));

        // or a shortcut
        cu.setPackageDeclaration("java.parser.test");

        // create the type declaration
        ClassOrInterfaceDeclaration type = cu.addClass("GeneratedClass");


        // 添加字段1
        FieldDeclaration field1 = new FieldDeclaration();
        AnnotationExpr field1Ann = new NormalAnnotationExpr(new Name("Autowired"), new NodeList<>());
        field1.addAnnotation(field1Ann);

        field1.addAnnotation(new SingleMemberAnnotationExpr(
                new Name("Qualifier"),
                new StringLiteralExpr("xxxBean")
        ));

        field1.addAnnotation(new NormalAnnotationExpr(new Name("Qualifier222"), new NodeList<>(
                new MemberValuePair("value", new StringLiteralExpr("value222")),
                new MemberValuePair("name", new StringLiteralExpr("name222"))
        )));
        field1.setModifiers(EnumSet.of(Modifier.PUBLIC));
        field1.setComment(new JavadocComment("this is javadoc comment"));

        VariableDeclarator field1VarDecl = new VariableDeclarator(
                JavaParser.parseClassOrInterfaceType("String"),
                "name",
                new StringLiteralExpr("zhang3")
        );
        field1.addVariable( field1VarDecl);
        type.addMember(field1);

        // create a method
        EnumSet<Modifier> modifiers = EnumSet.of(Modifier.PUBLIC);
        MethodDeclaration method = new MethodDeclaration(modifiers, new VoidType(), "main");
        modifiers.add(Modifier.STATIC);
        method.setModifiers(modifiers);
        type.addMember(method);

        // or a shortcut
        MethodDeclaration main2 = type.addMethod("main2", Modifier.PUBLIC, Modifier.STATIC);

        // add a parameter to the method
        Parameter param = new Parameter(JavaParser.parseClassOrInterfaceType("String"), "args");
        param.setVarArgs(true);
        method.addParameter(param);

        // or a shortcut
        main2.addAndGetParameter(String.class, "args").setVarArgs(true);

        // add a body to the method
        BlockStmt block = new BlockStmt();
        method.setBody(block);

        // add a statement do the method body
        NameExpr clazz = new NameExpr("System");
        FieldAccessExpr field = new FieldAccessExpr(clazz, "out");
        MethodCallExpr call = new MethodCallExpr(field, "println");
        call.addArgument(new StringLiteralExpr("Hello World!"));
        block.addStatement(call);



        // 插入字段2
        FieldDeclaration field2 = new FieldDeclaration();

        field2.setModifiers(EnumSet.of(Modifier.PRIVATE));

        VariableDeclarator field2VarDecl = new VariableDeclarator(
                PrimitiveType.intType(),
                "age",
                new IntegerLiteralExpr("11")
        );
        field2.addVariable( field2VarDecl);
        type.getMembers().add(1,field2);


        System.out.println(cu);
    }


}
