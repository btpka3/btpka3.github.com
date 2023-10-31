package com.github.btpka3.hello.maven.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.enforcer.rules.utils.ArtifactUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author dangqian.zll
 * @date 2023/10/25
 */
public class ArtifactUtilsTest {

    @Test
    public void test01() {
        Artifact artifact = new DefaultArtifact("com.aaa", "bbb", "1.0.12", "compile", "maven-plugin", null, new DefaultArtifactHandler());
        List<String> excludes = Collections.singletonList("com.aaa:bbb");
        boolean result = ArtifactUtils.matchDependencyArtifact(artifact, excludes);
        assertTrue(result);
    }
}
