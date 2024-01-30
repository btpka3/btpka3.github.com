package me.test;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * ./gradlew -q hi
 * @see <a href="https://docs.gradle.org/current/userguide/custom_plugins.html">Developing Custom Gradle Plugins</a>
 */
public class HiPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.task("hi", t -> t.doLast(task -> System.out.println("Hello from hi task")));
    }
}