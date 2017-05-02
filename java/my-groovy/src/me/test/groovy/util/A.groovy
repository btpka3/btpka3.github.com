package me.test.groovy.util

import groovy.transform.TypeChecked
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer

/**
 *
 */
class A {
    static void main(String[] args) {
        def config = new CompilerConfiguration()
//        config.addCompilationCustomizers(
//                new ASTTransformationCustomizer(TypeChecked)
//        )
        config.addCompilationCustomizers(
                new ASTTransformationCustomizer(
                        TypeChecked,
                        extensions: ['me/test/groovy/util/robotextension.groovy'])
        )
        def shell = new GroovyShell(config)
        def robot = new Robot()
        shell.setVariable('robot', robot)
        shell.evaluate("robot.move 1")
    }
}

class Robot {
    Robot move(int qt) { this }
}
