package com.github.btpka3.hello.maven.plugin;

import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.DependencyNodeVisitor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2023/10/13
 */
public class UsedDependencyCollector implements DependencyNodeVisitor {


    final Comparator<DependencyNode> c =

            Comparator.comparing(
                            (DependencyNode node) -> node.getArtifact().getGroupId(),
                            Comparator.nullsLast(Comparator.naturalOrder())
                    )
                    .thenComparing(
                            (DependencyNode node) -> node.getArtifact().getArtifactId(),
                            Comparator.nullsLast(Comparator.naturalOrder())
                    )
                    .thenComparing(
                            (DependencyNode node) -> node.getArtifact().getClassifier(),
                            Comparator.nullsLast(Comparator.naturalOrder())
                    )
                    .thenComparing(
                            (DependencyNode node) -> node.getArtifact().getType(),
                            Comparator.nullsLast(new TypeComparator(false, Comparator.naturalOrder()))
                    );
    DependencyNode root;

    Set<DependencyNode> dependencyNodes;

    Set<DependencyNode> usedDependencyNodes;


    public UsedDependencyCollector(DependencyNode root, Set<DependencyNode> dependencyNodes) {
        this.root = root;
        this.dependencyNodes = dependencyNodes;
        this.usedDependencyNodes = new HashSet<>(dependencyNodes.size());
    }

    public Set<DependencyNode> getUsedDependencyNodes() {
        return Collections.unmodifiableSet(usedDependencyNodes);
    }

    protected DependencyNode findTarget(DependencyNode currentNode) {


        return dependencyNodes.stream().filter(targetNode -> c.compare(targetNode, currentNode) == 0)
                .findFirst()
                .orElse(null);

    }

    protected boolean isRoot(DependencyNode node) {
        return c.compare(root, node) == 0;
    }

    @Override
    public boolean visit(DependencyNode node) {

        if (isRoot(node)) {
            return true;
        }

        DependencyNode targetNode = findTarget(node);
        if (targetNode != null) {
            usedDependencyNodes.add(targetNode);
        }

        return true;
    }

    @Override
    public boolean endVisit(DependencyNode node) {
        return true;
    }
}