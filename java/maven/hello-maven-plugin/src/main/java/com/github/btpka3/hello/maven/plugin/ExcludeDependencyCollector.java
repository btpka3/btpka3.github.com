package com.github.btpka3.hello.maven.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rules.utils.ArtifactUtils;
import org.apache.maven.model.Exclusion;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.DependencyNodeVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2023/10/13
 */
public class ExcludeDependencyCollector implements DependencyNodeVisitor {
    List<String> excludePatterns;

    Set<Exclusion> excludes;

    public ExcludeDependencyCollector(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
        this.excludes = new HashSet<>();
    }

    public Set<Exclusion> getExcludes() {
        return excludes;
    }

    protected Exclusion toExclusion(String excludePattern, Artifact artifact) {
        if (excludePattern.startsWith("*:*:")) {
            Exclusion exclusion = new Exclusion();
            exclusion.setGroupId(artifact.getGroupId());
            exclusion.setArtifactId(artifact.getArtifactId());
            return exclusion;
        }

        String[] arr = excludePattern.split(":");
        if (arr.length == 1 || (arr.length >= 2 && Objects.equals("*", arr[1]))) {
            Exclusion exclusion = new Exclusion();
            exclusion.setGroupId(artifact.getGroupId());
            exclusion.setArtifactId("*");
            return exclusion;
        }

        Exclusion exclusion = new Exclusion();
        exclusion.setGroupId(artifact.getGroupId());
        exclusion.setArtifactId(artifact.getArtifactId());
        return exclusion;
    }

    protected boolean isAdded(Exclusion toBeAdd) {
        return this.excludes.stream().anyMatch(added -> Objects.equals(added.getGroupId(), toBeAdd.getGroupId()) && Objects.equals(added.getArtifactId(), toBeAdd.getArtifactId()));
    }

    protected void addIfNeeded(Exclusion toBeAdd) {
        if (!isAdded(toBeAdd)) {
            this.excludes.add(toBeAdd);
        }
    }

    @Override
    public boolean visit(DependencyNode node) {
        for (String excludePattern : excludePatterns) {
            if (ArtifactUtils.compareDependency(excludePattern, node.getArtifact())) {
                Exclusion exclusion = toExclusion(excludePattern, node.getArtifact());
                addIfNeeded(exclusion);
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean endVisit(DependencyNode node) {
        return true;
    }
}
