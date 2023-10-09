package com.github.btpka3.hello.maven.plugin;


import org.apache.maven.shared.dependency.graph.DependencyNode;

import java.util.Map;
import java.util.Set;
import org.apache.maven.model.Exclusion;

/**
 * 1. 先完整构建 dependencyManagement 中的 dependency 的传递依赖树。
 * 2. 根据1的结果，构建 dependencyManagement 中的 dependency 之间的被依赖关系
 * 3. 再编程构建 只单个依赖 dependencyManagement 中的 dependency，并 exclude 掉 dependencyManagement 管理的其他 dependency，
 * 检查并记录要被 全局 exclude 的 maven GAV 列表。
 * 4. 编程构建新 project，依赖全部 dependencyManagement 中声明的 dependency，输出pom.xml 文件。
 * 再次检查是否 所有依赖列表中 仍然包含 要全局排除的依赖列表。如果仍有，则打印对应的依赖树，打印 warn 或 error 日志。
 *
 * @author dangqian.zll
 * @date 2023/10/7
 */
public class ExcludeConf {

    /**
     * dependencyManagement 中声明的 dependency 列表（含各自的单独的传递依赖），已经 resolve 后的。
     */
    Set<DependencyNode> dependencyNodes;

    /**
     * dependencyManagement 中声明的 dependency 被依赖请看。
     * KEY: dependencyManagement 中声明的 dependency A
     * VALUE: dependencyManagement 中声明的 dependency B, 且 B 有传递依赖 A。
     */
    Map<DependencyNode, Set<DependencyNode>> usedDependencies;


    /**
     * KEY: dependencyManagement 中声明的 dependency A
     * VALUE: 该 dependency A 要 exclude 的 全局排除的依赖的列表。
     */
    Map<DependencyNode, Set<Exclusion>> excludeDependencies;
}
