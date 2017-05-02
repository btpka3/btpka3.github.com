package me.test.groovy.ast

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 *
 * 可以通过 ASTTransformationCustomizer 配置，可以避免将 自定义筛选用的 annotation 类放到单独的一个jar包中。
 * @See groovy.transform.EqualsAndHashCode
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@GroovyASTTransformationClass(["me.test.groovy.ast.MapGetterSetterAST"])
@interface MapGetterSetter {

}