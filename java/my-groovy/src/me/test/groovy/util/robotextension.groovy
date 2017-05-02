import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.*


// TODO cp this file to $PROJECT_ROOT/out/production/my-groovy/me/test/groovy/util/

// 注意: 这里的代码可以调用 GroovyTypeCheckingExtensionSupport 上的方法
// 比如: newMethod、storeType 等

// Called after the type checker finished initialization
setup {
    // this is called before anything else
}

// Called after the type checker completed type checking
finish {
    // this is after completion
    // of all type checking
}

// Called when the type checker finds an unresolved variable
unresolvedVariable { VariableExpression var ->
    if ('robot' == var.name) {
        storeType(var, classNodeFor(me.test.groovy.util.Robot))
        handled = true
    }
}

// Called when the type checker cannot find a property on the receiver
unresolvedProperty { PropertyExpression pexp ->
//    if ('longueur' == pexp.propertyAsString &&
//            getType(pexp.objectExpression) == classNodeFor(String)) {
//        storeType(pexp, classNodeFor(int))
//        handled = true
//    }
}

// Called when the type checker cannot find an attribute on the receiver
unresolvedAttribute { AttributeExpression aex ->
//    if (getType(aex.objectExpression)==classNodeFor(String)) {
//        storeType(aex,classNodeFor(String))
//        handled = true
//    }
}

// Called before the type checker starts type checking a method call
beforeMethodCall { MethodCall call ->
//    if (isMethodCallExpression(call)
//            && call.methodAsString=='toUpperCase') {
//        addStaticTypeError('Not allowed',call)
//        handled = true
//    }
}

// Called once the type checker has finished type checking a method call

afterMethodCall { MethodCall call ->
//    if (getTargetMethod(call).name=='toUpperCase') {
//        addStaticTypeError('Not allowed',call)
//        handled = true
//    }
}

// Called by the type checker when it finds a method appropriate for a method call
onMethodSelection { Expression expr, MethodNode node ->
//    if (node.declaringClass.name == 'java.lang.String') {
//        // calling a method on 'String'
//        // let’s perform additional checks!
//        if (++count>2) {
//            addStaticTypeError("You can use only 2 calls on String in your source code",expr)
//        }
//    }
}

// Called by the type checker when it fails to find an appropriate method for a method call
methodNotFound { ClassNode receiver, String name, ArgumentListExpression argList, ClassNode[] argTypes, MethodCall call ->
//    // receiver is the inferred type of the receiver
//    // name is the name of the called method
//    // argList is the list of arguments the method was called with
//    // argTypes is the array of inferred types for each argument
//    // call is the method call for which we couldn’t find a target method
//    if (receiver==classNodeFor(String)
//            && name=='longueur'
//            && argList.size()==0) {
//        handled = true
//        return newMethod('longueur', classNodeFor(String))
//    }
}

// Called by the type checker before type checking a method body

beforeVisitMethod { MethodNode methodNode ->
    // tell the type checker we will handle the body by ourselves
    handled = methodNode.name.startsWith('skip')
}

// Called by the type checker after type checking a method body
afterVisitMethod { MethodNode methodNode ->
    scopeExit {
        if (methods > 2) {
            addStaticTypeError("Method ${methodNode.name} contains more than 2 method calls", methodNode)
        }
    }
}

// Called by the type checker before type checking a class
beforeVisitClass { ClassNode classNode ->
//    def name = classNode.nameWithoutPackage
//    if (!(name[0] in 'A'..'Z')) {
//        addStaticTypeError("Class '${name}' doesn't start with an uppercase letter",classNode)
//    }
}

// Called by the type checker after having finished the visit of a type checked class
afterVisitClass { ClassNode classNode ->
//    def name = classNode.nameWithoutPackage
//    if (!(name[0] in 'A'..'Z')) {
//        addStaticTypeError("Class '${name}' doesn't start with an uppercase letter",classNode)
//    }
}

// Called when the type checker thinks that an assignment is incorrect, meaning that the right hand side of an assignment is incompatible with the left hand side
incompatibleAssignment { lhsType, rhsType, expr ->
//    if (isBinaryExpression(expr) && isAssignment(expr.operation.type)) {
//        if (lhsType==classNodeFor(int) && rhsType==classNodeFor(Closure)) {
//            handled = true
//        }
//    }
}

// Called when the type checker cannot choose between several candidate methods
ambiguousMethods { List<MethodNode> methods, Expression origin ->
//    // choose the method which has an Integer as parameter type
//    methods.find { it.parameters.any { it.type == classNodeFor(Integer) } }
}
