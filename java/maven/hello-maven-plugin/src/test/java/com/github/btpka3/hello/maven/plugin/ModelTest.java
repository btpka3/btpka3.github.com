package com.github.btpka3.hello.maven.plugin;

import org.apache.commons.io.IOUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.project.*;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2023/11/1
 */
public class ModelTest {

    @Test
    public void writePom01() throws IOException {
        Model pomModel = new Model();
        pomModel.setModelVersion("4.0.0");

        pomModel.setGroupId("demo.group01");
        pomModel.setArtifactId("demo-artifact01");
        pomModel.setVersion("1.0.0-SNAPSHOT");
        pomModel.setPackaging("pom");


        MavenXpp3Writer mavenWriter = new MavenXpp3Writer();
        StringWriter writer = new StringWriter(10 * 1024);
        mavenWriter.write(writer, pomModel);
        System.out.println("============");
        System.out.println(writer.toString());
    }

    @Test
    public void readPom01() throws XmlPullParserException, IOException {
        String xml = IOUtils.toString(ModelTest.class.getResourceAsStream("/bom.tpl.xml"));

        Model model = null;
        {
            MavenXpp3Reader mavenReader = new MavenXpp3Reader();
            model = mavenReader.read(new StringReader(xml));
            model.setPomFile(null);


            List<Dependency> dependencies = model.getDependencyManagement().getDependencies();
            System.out.println("============ model   : " + dependencies.size());

            // swap order： 可以改变最后输出的顺序
            Dependency dep0 = dependencies.remove(0);
            Dependency dep1 = dependencies.remove(0);
            dependencies.add(dep1);
            dependencies.add(dep0);
        }
        {
            MavenProject project = new MavenProject(model);

            Dependency dependency = new Dependency();
            dependency.setGroupId("demo.group01");
            dependency.setArtifactId("demo-artifact01");
            dependency.setVersion("1.0.0-SNAPSHOT");
            project.getDependencyManagement().addDependency(dependency);
            System.out.println("============ project : " + project.getDependencyManagement().getDependencies().size());

        }

        {

            // 输出的 pom.xml 不包含注释
            MavenXpp3Writer mavenWriter = new MavenXpp3Writer();
            StringWriter writer = new StringWriter(10 * 1024);
            mavenWriter.write(writer, model);
            System.out.println("============ model   : " + model.getDependencyManagement().getDependencies().size());
            System.out.println(writer.toString());
        }
    }


    /**
     * 示例代码，不可运行。
     *
     * @param bomTemplate
     * @return
     */
    protected MavenProject genMavenProjectFromTemplate(String bomTemplate) {

        //@Parameter(defaultValue = "${session}", readonly = true, required = true)
        MavenSession session = null;

        //@Component
        ProjectBuilder projectBuilder = null;

        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
        buildingRequest.setRepositorySession(session.getRepositorySession());
        buildingRequest.setProject(null);
        buildingRequest.setProcessPlugins(false);
        buildingRequest.setResolveDependencies(false);

        try {
            ProjectBuildingResult result = projectBuilder.build(new File(bomTemplate), buildingRequest);
            return result.getProject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
