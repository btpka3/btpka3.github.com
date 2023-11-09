package com.github.btpka3.hello.maven.plugin;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 * @see <a href="https://github.com/ferstl/depgraph-maven-plugin/blob/master/src/main/java/com/github/ferstl/depgraph/ForArtifactDependencyGraphMojo.java">ForArtifactDependencyGraphMojo.java</a>
 */

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.Result;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.*;
import org.apache.maven.repository.internal.ArtifactDescriptorUtils;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @see <a href="https://github.com/jboss/bom-builder-maven-plugin">已经停止维护：jboss/bom-builder-maven-plugin</a>
 */
@Mojo(name = "gen-bom", defaultPhase = LifecyclePhase.NONE, requiresProject = false, requiresDependencyCollection = ResolutionScope.NONE, requiresDependencyResolution = ResolutionScope.NONE, threadSafe = true)
public class GenBomMojo extends AbstractMojo {


    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Parameter(property = "bom.template", required = true)
    private String bomTemplate;

    @Component
    private ProjectBuilder projectBuilder;
    @Component
    ProjectDependenciesResolver dependenciesResolver;

    @Component
    DependencyGraphBuilder dependencyGraphBuilder;

    XxxConf xxxConf = new XxxConf();

    public GenBomMojo() {
        xxxConf.excludeArtifactList = Arrays.asList(
//                "io.netty:netty-all",
//                "jakarta.annotation:jakarta.annotation-api:[1.0,2.0)",
//                "jakarta.xml.bind:jakarta.xml.bind-api:[2.0,3.0)",
//                "jakarta.activation:jakarta.activation-api:(1.0,2.0)",
                "*:*:[9999.0-empty-to-avoid-conflict-with-guava]");
    }


    public void execute() throws MojoExecutionException {
        try {

            excludeConf.dependencyNodes = getResolvedDependencyNodes();

            excludeConf.usedDependencies = genUsedDependencies(excludeConf.dependencyNodes);
            if (getLog().isDebugEnabled()) {
                getLog().debug("========== excludeConf.usedDependencies : \n" + toUsedDependencyStr(excludeConf.usedDependencies));
            }
            excludeConf.excludeDependencies = new HashMap<>();

            for (DependencyNode dependencyNode : excludeConf.dependencyNodes) {
                Set<Exclusion> exclusions = findExcludeDependencies(dependencyNode);
                if (!exclusions.isEmpty()) {
                    excludeConf.excludeDependencies.put(dependencyNode, exclusions);
                }
            }
            if (getLog().isDebugEnabled()) {
                getLog().debug("========== excludeConf.excludeDependencies : \n" + toExcludeDependenciesStr(excludeConf.excludeDependencies));
            }
//
//
//            outputFinalPom();
//            MavenProject finalProject = genMavenProjectFromTemplateWithDependencies(bomTemplate, excludeConf.usedDependencies);
//            lastCheck(finalProject);

            getLog().info("====== excludeConf.dependencyNodes : " + excludeConf.dependencyNodes.size() + " : " + excludeConf.dependencyNodes);
            getLog().info("====== Done.");
        } catch (Exception e) {
            throw new MojoExecutionException("err", e);
        }
    }


//    public MavenProject genProject() {
//        MavenProject project = new MavenProject();
//        {
//            Dependency dependency = new Dependency();
//            dependency.setGroupId("org.apache.zookeeper");
//            dependency.setArtifactId("zookeeper");
//            dependency.setVersion("3.8.1");
//            project.getDependencies().add(dependency);
//        }
//        {
//            Dependency dependency = new Dependency();
//            dependency.setGroupId("ch.qos.logback");
//            dependency.setArtifactId("logback-classic");
//            dependency.setVersion("1.4.11");
//            project.getDependencies().add(dependency);
//        }
//        return project;
//    }

//    protected void showDepsLikeTreeMojo() throws DependencyGraphBuilderException {
//        ArtifactFilter artifactFilter = new ScopeArtifactFilter("compile");
//
//        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
//        buildingRequest.setProject(this.genProject());
//        org.apache.maven.shared.dependency.graph.DependencyNode rootNode = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, artifactFilter);
//
//        StringWriter writer = new StringWriter();
//        DependencyNodeVisitor visitor = new org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor(writer,
//                org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor.STANDARD_TOKENS);
//        visitor = new BuildingDependencyNodeVisitor(visitor);
//        rootNode.accept(visitor);
//        String str = writer.toString();
//        getLog().info("====== showDepsLikeTreeMojo: tree : \n" + str);
//
//    }

//    /**
//     * 该方式的缺点: 依赖只会打印1次。
//     *
//     * @param project
//     * @throws DependencyResolutionException
//     */
//    public void showDeps(MavenProject project) throws DependencyResolutionException {
//        DefaultDependencyResolutionRequest request = new DefaultDependencyResolutionRequest();
//        request.setMavenProject(project);
//
////        request.setRepositorySession(getVerboseRepositorySession(project));
//        request.setRepositorySession(this.session.getRepositorySession());
//        // only download the poms, not the artifacts
//        request.setResolutionFilter((node, parents) -> false);
//
//
//        DependencyResolutionResult result = dependenciesResolver.resolve(request);
//        DependencyNode root = result.getDependencyGraph();
//
//        getLog().info("====== root : " + root);
//        getLog().info("====== root.children : " + root.getChildren());
//
//        String str = getDependencyTreeStr(root);
//        getLog().info("====== tree : \n" + str);
//
//
//        //ArtifactFilter transitiveDependencyFilter = createTransitiveDependencyFilter(project);
//    }

    ExcludeConf excludeConf = new ExcludeConf();

    @Component
    ModelBuilder modelBuilder;

    @Component
    private org.eclipse.aether.RepositorySystem repoSystem;


    protected boolean isRawDependency(List<Dependency> rawDependencyList, Dependency resolvedDependency) {
        return rawDependencyList.stream().anyMatch(rawDep -> Objects.equals(rawDep.getGroupId(), resolvedDependency.getGroupId()) && Objects.equals(rawDep.getArtifactId(), resolvedDependency.getArtifactId()) && Objects.equals(rawDep.getClassifier(), resolvedDependency.getClassifier()) && Objects.equals(rawDep.getScope(), resolvedDependency.getScope())

        );
    }


//    public void execute2() throws MojoExecutionException {
//        try {
//            MavenProject project = genMavenProjectFromTemplate(bomTemplate);
//            project.setPackaging("jar");
//            {
//                Dependency dependency = new Dependency();
//                dependency.setGroupId("io.grpc");
//                dependency.setArtifactId("grpc-all");
//                dependency.setVersion("1.58.0");
//
//                Exclusion exclusion = new Exclusion();
//                exclusion.setGroupId("com.google.guava");
//                exclusion.setArtifactId("guava");
//                dependency.addExclusion(exclusion);
//                project.getDependencies().add(dependency);
//            }
//            {
//                Dependency dependency = new Dependency();
//                dependency.setGroupId("com.google.guava");
//                dependency.setArtifactId("guava");
//                dependency.setVersion("32.0.1-jre");
//
//                project.getDependencies().add(dependency);
//            }
//
//            ArtifactFilter artifactFilter = (a) -> true;
//            org.apache.maven.shared.dependency.graph.DependencyNode dependencyNode = getDependencyNode(project, artifactFilter);
//            getLog().debug("========== tpl.xml dependencies : \n" + getDependencyTreeStr(dependencyNode));
//        } catch (Exception e) {
//            throw new MojoExecutionException("err", e);
//        }
//    }


    List<Dependency> getRawDependencyList() throws MojoExecutionException {
        Result<? extends Model> tplResult = buildTemplateRawModel(new File(bomTemplate));
        if (tplResult.hasErrors()) {
            throw new MojoExecutionException("template pom file has error : " + bomTemplate, tplResult.getProblems().iterator().next().getException());
        }

        // version 会有占位符
        List<Dependency> rawDependencyList = tplResult.get().getDependencyManagement().getDependencies();
        getLog().info("====== raw dependencyList.size : " + rawDependencyList.size());

        // 排除 import 的 bom 依赖
        return rawDependencyList.stream().filter(dep -> !(Objects.equals("pom", dep.getType()) && Objects.equals("import", dep.getScope()))).collect(Collectors.toList());
    }

    Set<DependencyNode> getResolvedDependencyNodes() throws ArtifactResolutionException, MojoExecutionException, DependencyGraphBuilderException {
        List<Dependency> rawDependencyList = getRawDependencyList();

        MavenProject tplProject = genMavenProjectFromTemplate(bomTemplate);
        List<Dependency> dependencyList = tplProject.getDependencyManagement().getDependencies();
        getLog().info("====== dependencyList.size : " + dependencyList.size());
        Set<DependencyNode> resolvedDependencyNodes = new HashSet<>();
        for (Dependency dependency : dependencyList) {

            String type = dependency.getType();
            if (!Objects.equals("jar", type) && !Objects.equals("pom", type) && !Objects.equals("bundle", type)) {
                getLog().debug("====== dependencyManagement.dependencies : type=" + type + " found :  " + dependency);
                continue;
            }

            // 只有模板pom.xml 中 dependencyManagement 下的 dependencies 才能给添加 exclude。
            if (!isRawDependency(rawDependencyList, dependency)) {
                continue;
            }


            Artifact artifact = toArtifact(dependency);
            if (!isTargetType(artifact)) {
                continue;
            }

            DependencyNode dependencyNode = getDependencyTree(artifact);
            resolvedDependencyNodes.add(dependencyNode);
            getLog().debug("========== excludeConf.dependencyNodes : \n" + getDependencyTreeStr(dependencyNode));
        }
        return resolvedDependencyNodes;
    }


    /**
     * 从bom模板文件中解析，但不 resolve，以便获取 dependencyManagement 下声明了多少个 atrtifact。
     *
     * @param pomFile
     * @return
     */
    protected Result<? extends Model> buildTemplateRawModel(File pomFile) {
        return modelBuilder.buildRawModel(pomFile, ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_1, true);
    }


    protected boolean isTargetType(Artifact artifact) throws MojoExecutionException, ArtifactResolutionException {
        try {

            org.eclipse.aether.artifact.Artifact pomArtifact = RepositoryUtils.toArtifact(artifact);
            pomArtifact = ArtifactDescriptorUtils.toPomArtifact(pomArtifact);

            ArtifactRequest pomRequest = new ArtifactRequest();
            pomRequest.setArtifact(pomArtifact);
            //pomRequest.setRepositories(config.repositories);
            ArtifactResult pomResult = repoSystem.resolveArtifact(session.getRepositorySession(), pomRequest);
            pomArtifact = pomResult.getArtifact();
            File pomFile = pomArtifact.getFile();
            if (pomFile == null || !pomFile.exists() || !pomFile.isFile()) {
                throw new MojoExecutionException("pomFile not existed : " + pomFile);
            }

            Result<? extends Model> result = modelBuilder.buildRawModel(pomArtifact.getFile(), ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_1, true);
            if (result.hasErrors()) {
                throw new MojoExecutionException("pom artifact build raw model error : " + pomArtifact, result.getProblems().iterator().next().getException());
            }
            Model model = result.get();
            String type = model.getPackaging();
            if (!Objects.equals("jar", type) && !Objects.equals("pom", type) && !Objects.equals("bundle", type)) {
                getLog().info("====== dependencyManagement.dependencies : actual packaging type=" + type + " not supported :  " + artifact);
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    protected Map<DependencyNode, Set<DependencyNode>> genUsedDependencies(Set<DependencyNode> dependencyNodes) {

        Map<DependencyNode, Set<DependencyNode>> map = new HashMap<>(dependencyNodes.size());
        for (DependencyNode dependencyNode : dependencyNodes) {

            UsedDependencyCollector collector = new UsedDependencyCollector(dependencyNode, dependencyNodes);
            dependencyNode.accept(collector);

            Set<DependencyNode> usedDependencyNodes = collector.getUsedDependencyNodes();

            if (!usedDependencyNodes.isEmpty()) {
                map.put(dependencyNode, collector.getUsedDependencyNodes());
            }
        }

        return map;
    }

    protected String toUsedDependencyStr(Map<DependencyNode, Set<DependencyNode>> map) {
        return map.entrySet().stream().map(entry -> entry.getKey().getArtifact() + entry.getValue().stream().map(n -> n.getArtifact().toString()).collect(Collectors.joining("\n   |- ", "\n   |- ", ""))).collect(Collectors.joining("\n"));
    }

    protected String toExcludeDependenciesStr(Map<DependencyNode, Set<Exclusion>> excludesMap) {
        return excludesMap.entrySet().stream().map(entry -> entry.getKey().getArtifact() + entry.getValue().stream().map(n -> n.getGroupId() + ":" + n.getArtifactId()).collect(Collectors.joining("\n   |- ", "\n   |- ", ""))).collect(Collectors.joining("\n"));
    }


    protected Set<Exclusion> findExcludeDependencies(DependencyNode node) throws DependencyGraphBuilderException {

        MavenProject project = genMavenProjectFromTemplate(bomTemplate);

        Dependency dependency = new Dependency();
        dependency.setGroupId(node.getArtifact().getGroupId());
        dependency.setArtifactId(node.getArtifact().getArtifactId());
        // 这里是动态修改 project，需要明确设置版本号，相当于手动根据 dependencyManagement 中的 版本号设置一下。
        dependency.setVersion(node.getArtifact().getVersion());
        //dependency.setClassifier(node.getArtifact().getClassifier());

        excludeConf.usedDependencies.entrySet().stream()
                .filter(entry -> {
                    DependencyNode curNode = entry.getKey();
                    return Objects.equals(curNode.getArtifact().getGroupId(), node.getArtifact().getGroupId()) && Objects.equals(curNode.getArtifact().getArtifactId(), node.getArtifact().getArtifactId())
                            //&& Objects.equals(curNode.getArtifact().getClassifier(), node.getArtifact().getClassifier())
                            ;
                })
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(shouldExcludeDeps -> shouldExcludeDeps.forEach(dep -> {
                    Exclusion exclusion = new Exclusion();
                    exclusion.setGroupId(dep.getArtifact().getGroupId());
                    exclusion.setArtifactId(dep.getArtifact().getArtifactId());
                    dependency.addExclusion(exclusion);
                }));


        project.getDependencies().add(dependency);

        ArtifactFilter artifactFilter = (a) -> true;
        DependencyNode dependencyNode = getDependencyNode(project, artifactFilter);
        ExcludeDependencyCollector collector = new ExcludeDependencyCollector(this.xxxConf.excludeArtifactList);
        dependencyNode.accept(collector);
        return collector.getExcludes();
    }


    Artifact toArtifact(Dependency dependency) {

        return new DefaultArtifact(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope(), dependency.getType(), dependency.getClassifier(), new DefaultArtifactHandler());
    }


    protected MavenProject genMavenProjectFromTemplate(String bomTemplate) {

        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
        buildingRequest.setRepositorySession(this.session.getRepositorySession());

        buildingRequest.setProject(null);
        buildingRequest.setResolveDependencies(false);

        try {
            ProjectBuildingResult result = this.projectBuilder.build(new File(bomTemplate), buildingRequest);
            return result.getProject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //
//    protected MavenProject genMavenProjectFromTemplateWithDependencies(String bomTemplate, Map<DependencyNode, Set<DependencyNode>> x) {
//        return null;
//    }
//
    protected void outputFinalPom() {
        MavenProject project = genMavenProjectFromTemplate(bomTemplate);

        // String bomTemplate, ExcludeConf excludeConf
    }

    protected void lastCheck(MavenProject finalProject) {

    }

//
//    protected DependencyNode toDependencyNode(DependencyNode node) {
//        return null;
//    }


    protected DependencyNode getDependencyTree(Artifact artifact) throws DependencyGraphBuilderException {
        MavenProject project = getMavenProject4SingleArtifact(artifact);
        ArtifactFilter artifactFilter = (a) -> true;
        return getDependencyNode(project, artifactFilter);
    }

    protected MavenProject getMavenProject4SingleArtifact(Artifact artifact) {
        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
        buildingRequest.setRepositorySession(this.session.getRepositorySession());
        buildingRequest.setProject(null);
        buildingRequest.setResolveDependencies(false);

        try {
            ProjectBuildingResult result = this.projectBuilder.build(artifact, buildingRequest);
            return result.getProject();

        } catch (Exception e) {
            throw new RuntimeException("failed to get dependency tree for artifact : " + artifact, e);
        }
    }

    protected DependencyNode getDependencyNode(MavenProject project, ArtifactFilter artifactFilter) throws DependencyGraphBuilderException {
        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
//        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
        //buildingRequest.setResolveDependencies(true);
        buildingRequest.setProject(project);
        return dependencyGraphBuilder.buildDependencyGraph(buildingRequest, artifactFilter);

    }

//    protected String getDependencyTreeStr(DependencyNode root) {
//        StringWriter writer = new StringWriter();
//        SerializingDependencyNodeVisitor v = new SerializingDependencyNodeVisitor(writer, STANDARD_TOKENS);
//        accept(toDependencyNodeEx(null, root), v);
//        return writer.toString();
//    }

    protected String getDependencyTreeStr(DependencyNode root) {
        StringWriter writer = new StringWriter();
        org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor v = new org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor(writer, org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor.STANDARD_TOKENS);
        root.accept(v);
        return writer.toString();
    }


//    protected void showDepsLikeForArtifactDependencyGraphMojo() {
//        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(this.session.getProjectBuildingRequest());
//        buildingRequest.setRepositorySession(this.session.getRepositorySession());
//        buildingRequest.setProject(null);
//        buildingRequest.setResolveDependencies(true);
//        //buildingRequest.setActiveProfileIds(this.profiles);
//
//        Artifact artifact = new DefaultArtifact("org.apache.zookeeper", "zookeeper", "3.8.1", SCOPE_COMPILE, "jar", null, new DefaultArtifactHandler());
//        try {
//            ProjectBuildingResult result = this.projectBuilder.build(artifact, buildingRequest);
//            MavenProject project = result.getProject();
//            getLog().info("====== showDepsLikeForArtifactDependencyGraphMojo : project.artifact : " + project.getArtifact());
//            showDeps(project);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }


//    public boolean accept(DependencyNodeEx node, DependencyVisitor visitor) {
//
//        if (visitor.visitEnter(node)) {
//            for (DependencyNode child : node.getChildren()) {
//                if (!accept(toDependencyNodeEx(node, child), visitor)) {
//                    break;
//                }
//            }
//        }
//
//        return visitor.visitLeave(node);
//    }

//    protected DependencyNodeEx toDependencyNodeEx(Artifact artifact) {
//        return new DependencyNodeExImpl(null, new DefaultDependencyNode(RepositoryUtils.toArtifact(artifact)));
//    }

//    protected DependencyNodeEx toDependencyNodeEx(DependencyNode parent, DependencyNode node) {
//        return new DependencyNodeExImpl(parent, node);
//    }


//    private Artifact createArtifact(String artifact) {
//
//        if (StringUtils.isBlank(artifact)) {
//            throw new IllegalArgumentException("artifact is required.");
//        }
//
//        String groupId = null;
//        String artifactId = null;
//        String version = null;
//        String type = null;
//        String classifier = null;
//
//
//        String[] parts = artifact.split(":");
//
//        groupId = StringUtils.trimToNull(parts[0]);
//        if (parts.length > 1) {
//            artifactId = StringUtils.trimToNull(parts[1]);
//        }
//        if (parts.length > 2) {
//            version = StringUtils.trimToNull(parts[2]);
//        }
//
//        if (parts.length > 3) {
//            type = StringUtils.trimToNull(parts[3]);
//        }
//        if (parts.length > 4) {
//            classifier = StringUtils.trimToNull(parts[4]);
//        }
//
//        return new DefaultArtifact(groupId, artifactId, version, SCOPE_COMPILE, type, classifier, new DefaultArtifactHandler());
//    }


//    private Artifact createArtifact(String groupId, String artifactId, String type, String classifier) {
//        return new DefaultArtifact(groupId, artifactId, (String) null, SCOPE_COMPILE, type, classifier, new DefaultArtifactHandler());
//    }

//
//    //@Override
//    public void execute1() throws MojoExecutionException, MojoFailureException {
//        try {
//            MavenProject project = genProject();
//
//            showDeps(project);
//            showDepsLikeForArtifactDependencyGraphMojo();
//            showDepsLikeTreeMojo();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        getLog().info("Hello, world.");
//    }
}