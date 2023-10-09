package com.github.btpka3.hello.maven.plugin;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2023/9/28
 */
public class GreetingMojoTest extends AbstractMojoTestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        // required for mojo lookups to work
        super.setUp();


    }

    /**
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        // required
        super.tearDown();
    }

    protected void addGuiceModules(List<Module> modules) {
        modules.add((Binder binder) -> {
            binder.bind(Mojo.class).to(GreetingMojo.class);
        });
    }

    public void testMojoGoal() throws Exception {

        String baseDir = getBasedir();
        System.out.println("====== baseDir=" + baseDir+", "+Thread.currentThread().getContextClassLoader());
        String file = "src/test/resources/unit/project-to-test/pom.xml";

        //File testPom = new File(baseDir, file);
        File testPom = getTestFile(file);
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        GreetingMojo mojo = (GreetingMojo) lookupMojo("sayhi", testPom);
        //getContainer().addComponent();
//        getContainer().getContainerRealm();
        //getContainer().

//        GreetingMojo mojo = new GreetingMojo();
//        mojo = (GreetingMojo) configureMojo(mojo, extractPluginConfiguration("hello-maven-plugin", testPom));


        assertNotNull(mojo);
        mojo.execute();
    }
}
