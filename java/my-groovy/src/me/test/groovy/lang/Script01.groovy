package me.test.groovy.lang

import groovy.util.logging.Log
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder

/**
 *
 */
class Script01 {


    static class MyDSL {
        public void foo(int x, int y, Closure z) {
            z(x + y)
        }

        void task(Map<String, ?> m) {
            println "~~~~~~~~ m : " + m
        }

        void task(Map<String, ?> m, String n, Closure c) {
            c(m, n)
        }

        public void setBar(String a) {
            println "----- a = " + a
        }
    }

    static void main(String[] args) {


        CompilerConfiguration cc = new CompilerConfiguration();
        CompilerCustomizationBuilder.withConfig(cc) {
            ast(Log)
        }

        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Script01.getClassLoader(), new Binding(), cc);

        DelegatingScript script = (DelegatingScript) sh.parse("""
//foo(1,2){
//  println("===== sum = " + it);
//}
//bar="aaa"

log.info("aaaaaaaaa");

// 模仿 build.gradle 中的 task 语法
//task zzz (type:"Tar" ) {
//   println "~~~~~ " + it
//}
""")
        script.setDelegate(new MyDSL());
        script.run();
    }
}
