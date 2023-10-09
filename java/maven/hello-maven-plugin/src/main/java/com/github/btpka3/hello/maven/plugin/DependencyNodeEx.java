package com.github.btpka3.hello.maven.plugin;

import org.eclipse.aether.graph.DependencyNode;

/**
 * @author dangqian.zll
 * @date 2023/10/7
 */
public interface DependencyNodeEx extends DependencyNode {

    DependencyNode getParent();
}
