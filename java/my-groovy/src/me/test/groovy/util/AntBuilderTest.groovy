package me.test.groovy.util

import org.apache.tools.ant.taskdefs.ExecTask

/**
 *
 */
//@CompileStatic
//@TypeChecked
class AntBuilderTest {

    static void main(String[] args) {
        println "1111"
        AntBuilder ant = new AntBuilder()
        ant.echo(message: "-------------------") // FIXME: 这里的自动完成是？
        ant.echo([:], "999") {

        }
        ExecTask e = ant.exec(executable: "echo", outputproperty: "a.out") {
            arg(value: "aaa")
            arg(value: "bbb")
        }
        println "===" + e.project.getProperty("a.out") + "~~~"
    }
}
