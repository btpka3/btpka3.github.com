package me.test.maven.greeting.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "greeting", threadSafe = false, defaultPhase=LifecyclePhase.COMPILE)
public class GreetingMojo extends AbstractMojo {

    // defaultValue = "${project.build.directory}"
    @Parameter(defaultValue = "Guest", readonly = true, required = false)
    private String name = null;

    /**
     * Says "Hi" to the user.
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hi " + name + " ~~~");
    }

}
