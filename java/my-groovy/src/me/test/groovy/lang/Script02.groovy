package me.test.groovy.lang

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import static org.codehaus.groovy.ast.ClassHelper.make

/**
 *
 */


class AstUtils {
    public static boolean isMethodOnThis(MethodCallExpression call, String name) {
        boolean hasName = call.getMethod() instanceof ConstantExpression && call.getMethod().getText().equals(name);
        return hasName && targetIsThis(call);
    }

    public static boolean targetIsThis(MethodCallExpression call) {
        Expression target = call.getObjectExpression();
        return target instanceof VariableExpression && target.getText().equals("this");
    }

    static void visitScriptCode(SourceUnit source, GroovyCodeVisitor transformer) {
        source.getAST().getStatementBlock().visit(transformer);
        Iterator i$ = source.getAST().getMethods().iterator();

        while (i$.hasNext()) {
            Object method = i$.next();
            MethodNode methodNode = (MethodNode) method;
            methodNode.getCode().visit(transformer);
        }

    }
}

@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class MyTaskAST extends AbstractASTTransformation {
    static final ClassNode MY_TYPE = make(MyTask);

    static class MyTaskTransformer extends CodeVisitorSupport {

        private boolean isInstanceMethod(MethodCallExpression call, String name) {
            boolean isTaskMethod = AstUtils.isMethodOnThis(call, name);
            if (!isTaskMethod) {
                return false;
            }

            return call.getArguments() instanceof ArgumentListExpression;
        }

        private boolean isDynamicVar(Expression expression) {
            if (!(expression instanceof VariableExpression)) {
                return false;
            }
            VariableExpression variableExpression = (VariableExpression) expression;
            return variableExpression.getAccessedVariable() instanceof DynamicVariable;
        }

        private boolean isTaskIdentifier(Expression expression) {
            return expression instanceof ConstantExpression || expression instanceof GStringExpression;
        }


        private void transformVariableExpression(MethodCallExpression call, int index) {
            ArgumentListExpression args = (ArgumentListExpression) call.getArguments();
            VariableExpression arg = (VariableExpression) args.getExpression(index);
            if (!isDynamicVar(arg)) {
                return;
            }

            // Matches: task args?, <identifier>, args? or task(args?, <identifier>, args?)
            // Map to: task(args?, '<identifier>', args?)
            String taskName = arg.getText();
            call.setMethod(new ConstantExpression("task"));
            args.getExpressions().set(index, new ConstantExpression(taskName));
        }

        @Override
        void visitMethodCallExpression(MethodCallExpression call) {

            println "call : " + call

            if (!isInstanceMethod(call, "task")) {
                return;
            }

            ArgumentListExpression args = (ArgumentListExpression) call.getArguments();

            Expression arg = args.getExpression(0);
            // arg instanceof MethodCallExpression
            maybeTransformNestedMethodCall((MethodCallExpression) arg, call);


            super.visitMethodCallExpression(call);
        }


        private boolean maybeTransformNestedMethodCall(MethodCallExpression nestedMethod, MethodCallExpression target) {
            if (!(isTaskIdentifier(nestedMethod.getMethod()) && AstUtils.targetIsThis(nestedMethod))) {
                return false;
            }

            // Matches: task <identifier> <arg-list> | task <string> <arg-list>
            // Map to: task("<identifier>", <arg-list>) | task(<string>, <arg-list>)

            Expression taskName = nestedMethod.getMethod();
            Expression mapArg = null;
            List<Expression> extraArgs = Collections.emptyList();

            if (nestedMethod.getArguments() instanceof TupleExpression) {
                TupleExpression nestedArgs = (TupleExpression) nestedMethod.getArguments();
                if (nestedArgs.getExpressions().size() == 2 && nestedArgs.getExpression(0) instanceof MapExpression && nestedArgs.getExpression(1) instanceof ClosureExpression) {
                    // Matches: task <identifier>(<options-map>) <closure>
                    mapArg = nestedArgs.getExpression(0);
                    extraArgs = nestedArgs.getExpressions().subList(1, nestedArgs.getExpressions().size());
                } else if (nestedArgs.getExpressions().size() == 1 && nestedArgs.getExpression(0) instanceof ClosureExpression) {
                    // Matches: task <identifier> <closure>
                    extraArgs = nestedArgs.getExpressions();
                } else if (nestedArgs.getExpressions().size() == 1 && nestedArgs.getExpression(0) instanceof NamedArgumentListExpression) {
                    // Matches: task <identifier>(<options-map>)
                    mapArg = nestedArgs.getExpression(0);
                } else if (nestedArgs.getExpressions().size() != 0) {
                    return false;
                }
            }

            target.setMethod(new ConstantExpression("task"));
            ArgumentListExpression args = (ArgumentListExpression) target.getArguments();
            args.getExpressions().clear();
            if (mapArg != null) {
                args.addExpression(mapArg);
            }
            args.addExpression(taskName);
            for (Expression extraArg : extraArgs) {
                args.addExpression(extraArg);
            }
            return true;
        }
    }


    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {


        AnnotationNode anno = (AnnotationNode) nodes[0]
        if (MY_TYPE != anno.classNode) {
            return
        }

        println("nodes.length = " + nodes.length)
        AnnotatedNode annoNode = (AnnotatedNode) nodes[1]
        if (!(annoNode instanceof ClassNode)) {
            return
        }

        AstUtils.visitScriptCode(source, new MyTaskTransformer())

//        ClassNode classNode = (ClassNode) annoNode
//        classNode.getMethods().each { MethodNode mn ->
//            println "mn   : " + mn
//        }
    }
}

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["me.test.groovy.lang.MyTaskAST"])
@interface MyTask {
}

abstract class Script02 extends Script {

    void task(Map<String, ?> m, String n, Closure c) {
        println "=======task: n = $n, m = $m"
        c(n, m)
    }


    Object invokeMethod(String name, Object args) {
        println("invokeMethod : name : ${name}, args : ${args}")
        return null;
    }

    static void main(String[] args) {

        //def acz = new ASTTransformationCustomizer(MyTask)

        CompilerConfiguration cc = new CompilerConfiguration();
        CompilerCustomizationBuilder.withConfig(cc) {
            ast(MyTask)
        }

        cc.setScriptBaseClass(Script02.class.getName());
        GroovyShell sh = new GroovyShell(Script02.getClassLoader(), new Binding(), cc);

        Script02 script = (Script02) sh.parse("""

// 不用 AST 做不到，请参考 gradle 的 org.gradle.groovy.scripts.internal.TaskDefinitionScriptTransformer
// 模仿 build.gradle 中的 task 语法
task zzz (type:"Tar" ) {String name , Map<String,?> conf ->
   println "~~~ task name = "+ name + ", conf = " + conf
}
""")
        script.run();
    }
}
