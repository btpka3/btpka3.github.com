package com.github.btpka3.gradle.plugins.tasks

import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.compile.GroovyCompile
import org.gradle.api.tasks.compile.JavaCompile

class QuerydslCompile extends JavaCompile {

    QuerydslCompile() {
        source(
                project.sourceSets.main.java,
                project.sourceSets.main.groovy
        )
        //setSource()

        if (project.plugins.hasPlugin(WarPlugin.class)) {
            project.configurations {
                querydsl.extendsFrom compile, providedRuntime, providedCompile
            }
        } else {
            project.configurations {
                querydsl.extendsFrom compile
            }
        }

        project.afterEvaluate {
            setClasspath(project.configurations.querydsl)
            File file = project.file(project.querydsl.querydslSourcesDir)
            setDestinationDir(file)
        }
    }
}