package com.github.btpka3.hello.maven.plugin;

import java.util.List;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 */
public class XxxConf {


    /**
     * 要定义的 properties 列表
     */
    List<String> propertiesList;


    /**
     * 要 import 的 pom的列表。
     */
    List<String> importPomList;

    /**
     * 要管理的
     */
    List<Artifact> managedArtifactList;


    /**
     * 要排除的包的列表。
     */
    List<String> excludeArtifactList;


    public static class Artifact {
        String groupId;
        String artifactId;
        String version;
        String classifier;
        String scope;
        String packaging;
    }

    public static class ExcludeArtifact {
        String groupId;
        String artifactId;

        String versionRange;
        String message;
        Artifact theNewArtifact;
    }
}


