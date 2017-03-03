package me.test.groovy.ast

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 *
 * @See groovy.transform.EqualsAndHashCode
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@GroovyASTTransformationClass(["me.test.groovy.ast.MapGetterSetterAST"])
@interface MapGetterSetter {

}