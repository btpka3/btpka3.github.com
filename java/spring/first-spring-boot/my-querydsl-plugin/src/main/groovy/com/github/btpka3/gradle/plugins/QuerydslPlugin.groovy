package com.github.btpka3.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 */
class QuerydslPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.task('hi') {
            //doLast {
                println "Hello from the GreetingPlugin"
            //}
        }

        project.tasks.compileGroovy.dependsOn project.tasks.hi
    }

}
