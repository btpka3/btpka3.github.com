package me.test

import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * ./gradlew -q greeting
 */
class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('greeting') {
            doLast {
                println 'greeting from the GreetingPlugin'
            }
        }
    }
}

