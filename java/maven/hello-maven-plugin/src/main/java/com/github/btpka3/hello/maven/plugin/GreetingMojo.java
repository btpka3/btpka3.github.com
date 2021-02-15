package com.github.btpka3.hello.maven.plugin;

import org.apache.maven.model.Model;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 */
@Mojo(name = "sayhi", requiresProject = true, requiresDirectInvocation = true, aggregator = true, threadSafe = true)
public class GreetingMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    protected MavenProject project;

    public MavenProject getProject() {
        return project;
    }

    public void execute() throws MojoExecutionException {
        getLog().info("Hello, world.");

        if (project.getOriginalModel().getVersion() == null) {
            throw new MojoExecutionException("Project version is inherited from parent.");
        }


        try {
            final MavenProject project = getProject();

            getLog().info("Local aggregation root: " + project.getBasedir());
            Map<String, Model> reactorModels = PomHelper.getReactorModels(project, getLog());
            getLog().info("reactorModels: " + reactorModels);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
