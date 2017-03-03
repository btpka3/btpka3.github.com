package me.test.groovy.ast

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.util.HashCodeHelper

import static org.codehaus.groovy.ast.ClassHelper.make
import static org.codehaus.groovy.ast.tools.GenericsUtils.makeClassSafe

/**
 *
 *
 * 更多的实现参考可以去groovy-core 的 github 仓库中搜索 `AstBuilder`
 * https://github.com/groovy/groovy-core/search?utf8=%E2%9C%93&q=AstBuilder&type=Code
 *
 * @see org.codehaus.groovy.transform.EqualsAndHashCodeASTTransformation
 */
//@CompileStatic
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class MapGetterSetterAST extends AbstractASTTransformation {

    static final Class MY_CLASS = MapGetterSetter.class;
    static final ClassNode MY_TYPE = make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode HASHUTIL_TYPE = make(HashCodeHelper.class);
    private static final ClassNode OBJECT_TYPE = makeClassSafe(Object.class);


    @Override
    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        println "111111111111"
        println nodes

        AnnotationNode anno = (AnnotationNode) nodes[0]
        if (MY_TYPE != anno.classNode) {
            return
        }

        AnnotatedNode annoNode = (AnnotatedNode) nodes[1]
        if (!(annoNode instanceof ClassNode)) {
            return
        }


        ClassNode classNode = (ClassNode) annoNode

        // 追加一个 AstBuilder#buildFromCode 的示例
        classNode.addMethod(new MethodNode("hi_code",
                ACC_PUBLIC,
                ClassHelper.STRING_TYPE,
                [] as Parameter[],
                null,
                new AstBuilder().buildFromCode {
                    return "hi_code - " + new Date() + " @ " + this.getClass()
                }[0] as Statement
        ))

        // 追加一个 AstBuilder#buildFromSpec 的示例
        MethodNode hi_spec = new AstBuilder().buildFromSpec {
            method('hi_spec', ACC_PUBLIC, String) {
                parameters {
                    //parameter 'a': String.class;
                    //parameter 'b': String.class
                }
                exceptions {}
                block {
                    returnStatement {
                        constant "hi_spec - added"
                    }
                }
            }
        }[0]
        classNode.addMethod(hi_spec)

        // 追加一个 AstBuilder#buildFromString 的示例
        MethodNode hi_str = new AstBuilder().buildFromString(CompilePhase.CONVERSION, false, """
                java.lang.String hi_str(){
                    return "hi_str : " + new java.util.Date() 
                }
        """)[1].getMethods('hi_str')[0]
        classNode.addMethod(hi_str)

        classNode.fields.each { FieldNode fieldNode ->
            addGetter(classNode, fieldNode)
            addSetter(classNode, fieldNode)
        }
        println classNode
    }


    static void addGetter(ClassNode targetClassNode, FieldNode fieldNode) {
        String fieldName = fieldNode.name
        String firstUpper = "" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1)
        boolean isBool = fieldNode.type == ClassHelper.boolean_TYPE || fieldNode.type == ClassHelper.Boolean_TYPE
        String getterName = (isBool ? "is" : "get") + firstUpper

        MethodNode getter = new MethodNode(getterName,
                ACC_PUBLIC,
                fieldNode.type,
                [] as Parameter[],
                null,
                new AstBuilder().buildFromCode {
                    return this.map.get(fieldName)
                }[0] as Statement
        )
        targetClassNode.addMethod(getter)
    }

    static void addSetter(ClassNode targetClassNode, FieldNode fieldNode) {
        String fieldName = fieldNode.name
        String firstUpper = "" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1)
        String setterName = "set" + firstUpper

        MethodNode setter = new AstBuilder().buildFromSpec {
            method(setterName, ACC_PUBLIC, fieldNode.type.getTypeClass()) {
                parameters {
                    parameter "${fieldName}": fieldNode.type.getTypeClass()
                }
                exceptions {}
                block {
                    returnStatement {
                        methodCall {
                            variable "map"
                            constant "put"
                            argumentList {
                                constant fieldName
                                variable "${fieldName}"
                            }
                        }
                    }
                }
            }
        }[0]
        MethodNode getter = new MethodNode(setterName,
                ACC_PUBLIC,
                ClassHelper.VOID_TYPE,
                [new Parameter(fieldNode.type, fieldName)] as Parameter[],
                null,
                new AstBuilder().buildFromCode {
                    this.map.put(fieldName,)
                }[0] as Statement
        )
        targetClassNode.addMethod(setter)
    }

    static String firstUpper(String fieldName) {
        return
    }

    static String getterName(String fieldName, Class clazz) {


    }


}

//
//    public static void createEquals(ClassNode cNode, boolean includeFields, boolean callSuper, boolean useCanEqual, List<String> excludes, List<String> includes) {
//        if (useCanEqual) createCanEqual(cNode);
//        // make a public method if none exists otherwise try a private method with leading underscore
//        boolean hasExistingEquals = hasDeclaredMethod(cNode, "equals", 1);
//        if (hasExistingEquals && hasDeclaredMethod(cNode, "_equals", 1)) return;
//
//        final BlockStatement body = new BlockStatement();
//        VariableExpression other = varX("other");
//
//        // some short circuit cases for efficiency
//        body.addStatement(ifS(equalsNullX(other), returnS(constX(Boolean.FALSE, true))));
//        body.addStatement(ifS(sameX(varX("this"), other), returnS(constX(Boolean.TRUE, true))));
//
//        if (useCanEqual) {
//            body.addStatement(ifS(notX(isInstanceOfX(other, GenericsUtils.nonGeneric(cNode))), returnS(constX(Boolean.FALSE,true))));
//        } else {
//            body.addStatement(ifS(notX(hasClassX(other, GenericsUtils.nonGeneric(cNode))), returnS(constX(Boolean.FALSE,true))));
//        }
//
//        VariableExpression otherTyped = varX("otherTyped", GenericsUtils.nonGeneric(cNode));
//        CastExpression castExpression = new CastExpression(GenericsUtils.nonGeneric(cNode), other);
//        castExpression.setStrict(true);
//        body.addStatement(declS(otherTyped, castExpression));
//
//        if (useCanEqual) {
//            body.addStatement(ifS(notX(callX(otherTyped, "canEqual", varX("this"))), returnS(constX(Boolean.FALSE,true))));
//        }
//
//        List<PropertyNode> pList = getInstanceProperties(cNode);
//        for (PropertyNode pNode : pList) {
//            if (shouldSkip(pNode.getName(), excludes, includes)) continue;
//            boolean canBeSelf = StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(
//                    pNode.getOriginType(), cNode
//            );
//            if (!canBeSelf) {
//                body.addStatement(ifS(notX(hasEqualPropertyX(pNode, otherTyped)), returnS(constX(Boolean.FALSE, true))));
//            } else {
//                body.addStatement(
//                        ifS(notX(hasSamePropertyX(pNode, otherTyped)),
//                                ifElseS(differentSelfRecursivePropertyX(pNode, otherTyped),
//                                        returnS(constX(Boolean.FALSE, true)),
//                                        ifS(notX(bothSelfRecursivePropertyX(pNode, otherTyped)),
//                                                ifS(notX(hasEqualPropertyX(pNode, otherTyped)), returnS(constX(Boolean.FALSE, true))))
//                                )
//                        )
//                );
//            }
//        }
//        List<FieldNode> fList = new ArrayList<FieldNode>();
//        if (includeFields) {
//            fList.addAll(getInstanceNonPropertyFields(cNode));
//        }
//        for (FieldNode fNode : fList) {
//            if (shouldSkip(fNode.getName(), excludes, includes)) continue;
//            body.addStatement(
//                    ifS(notX(hasSameFieldX(fNode, otherTyped)),
//                            ifElseS(differentSelfRecursiveFieldX(fNode, otherTyped),
//                                    returnS(constX(Boolean.FALSE,true)),
//                                    ifS(notX(bothSelfRecursiveFieldX(fNode, otherTyped)),
//                                            ifS(notX(hasEqualFieldX(fNode, otherTyped)), returnS(constX(Boolean.FALSE,true)))))
//                    ));
//        }
//        if (callSuper) {
//            body.addStatement(ifS(
//                    notX(isTrueX(callSuperX("equals", other))),
//                    returnS(constX(Boolean.FALSE,true))
//            ));
//        }
//
//        // default
//        body.addStatement(returnS(constX(Boolean.TRUE,true)));
//
//        cNode.addMethod(new MethodNode(
//                hasExistingEquals ? "_equals" : "equals",
//                hasExistingEquals ? ACC_PRIVATE : ACC_PUBLIC,
//                ClassHelper.boolean_TYPE,
//                params(param(OBJECT_TYPE, other.getName())),
//                ClassNode.EMPTY_ARRAY,
//                body));
//    }
