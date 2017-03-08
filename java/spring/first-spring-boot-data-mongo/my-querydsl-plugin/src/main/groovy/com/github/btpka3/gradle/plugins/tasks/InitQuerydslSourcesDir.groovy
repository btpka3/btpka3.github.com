package com.github.btpka3.gradle.plugins.tasks

import com.github.btpka3.gradle.plugins.QuerydslPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 *
 */
class InitQuerydslSourcesDir extends DefaultTask {

    static final String DESCRIPTION = "Creates the Querydsl sources dir."

    InitQuerydslSourcesDir() {
        this.group = QuerydslPlugin.TASK_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {
        project.file(project.querydsl.querydslSourcesDir).mkdirs()
    }
}