package me.test.first.spring.boot.test;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.builder.AstBuilderTransformation;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2021/3/16
 * @see GroovyCodeVisitor
 * @see AstBuilderTransformation
 * @see ASTTransformation
 */
public class GroovyAstTest {

    @Test
    public void findVars01() {

        AstBuilder astBuilder = new AstBuilder();
        List<ASTNode> list = astBuilder.buildFromString("p('aaa') + a + bbb + {s-> ccc + ddd}.call(111)");

        FindVariableVisitor visitor = new FindVariableVisitor();
        for (ASTNode astNode : list) {
            astNode.visit(visitor);
        }

        System.out.println(list);
        System.out.println("varNames = " + visitor.getVarNames());
    }

    public static class MyASTTransformation implements ASTTransformation {

        @Override
        public void visit(ASTNode[] nodes, SourceUnit source) {

        }
    }

    /**
     * @see ASTTransformationCollectorCodeVisitor
     */
    public static class MyGroovyCodeVisitor extends ClassCodeVisitorSupport implements GroovyCodeVisitor {
        private final SourceUnit source;
        private final GroovyClassLoader transformLoader;

        public MyGroovyCodeVisitor(SourceUnit source, GroovyClassLoader transformLoader) {
            this.source = source;
            this.transformLoader = transformLoader;
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return source;
        }
    }


    public static class FindVariableVisitor extends CodeVisitorSupport {
        private static List<String> list = Arrays.asList("args", "context", "this", "super");
        private Set<String> varNames = new HashSet<>();

        @Override
        public void visitVariableExpression(VariableExpression expression) {

            if (!(list.contains(expression.getText()))) {
                if (expression.getAccessedVariable() instanceof DynamicVariable) {
                    varNames.add(expression.getText());
                }
            }
            super.visitVariableExpression(expression);
        }

        public Set<String> getVarNames() {
            return varNames;
        }
    }

}
