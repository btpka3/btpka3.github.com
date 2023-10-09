package com.github.btpka3.hello.maven.plugin;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionConstraint;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/10/7
 */
public class DependencyNodeExImpl implements DependencyNodeEx {

    DependencyNode node;
    DependencyNode parent;

    public DependencyNodeExImpl(DependencyNode parent, DependencyNode node) {
        this.node = node;
        this.parent = parent;
    }


    @Override
    public DependencyNode getParent() {
        return this.parent;
    }

    // =========== delegate method

    @Override
    public List<DependencyNode> getChildren() {
        return node.getChildren();
    }

    @Override
    public void setChildren(List<DependencyNode> children) {
        node.setChildren(children);
    }

    @Override
    public Dependency getDependency() {
        return node.getDependency();
    }

    @Override
    public Artifact getArtifact() {
        return node.getArtifact();
    }

    @Override
    public void setArtifact(Artifact artifact) {
        node.setArtifact(artifact);
    }

    @Override
    public List<? extends Artifact> getRelocations() {
        return node.getRelocations();
    }

    @Override
    public Collection<? extends Artifact> getAliases() {
        return node.getAliases();
    }

    @Override
    public VersionConstraint getVersionConstraint() {
        return node.getVersionConstraint();
    }

    @Override
    public Version getVersion() {
        return node.getVersion();
    }

    @Override
    public void setScope(String scope) {
        node.setScope(scope);
    }

    @Override
    public void setOptional(Boolean optional) {
        node.setOptional(optional);
    }

    @Override
    public int getManagedBits() {
        return node.getManagedBits();
    }

    @Override
    public List<RemoteRepository> getRepositories() {
        return node.getRepositories();
    }

    @Override
    public String getRequestContext() {
        return node.getRequestContext();
    }

    @Override
    public void setRequestContext(String context) {
        node.setRequestContext(context);
    }

    @Override
    public Map<?, ?> getData() {
        return node.getData();
    }

    @Override
    public void setData(Map<Object, Object> data) {
        node.setData(data);
    }

    @Override
    public void setData(Object key, Object value) {
        node.setData(key, value);
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return node.accept(visitor);
    }

}
