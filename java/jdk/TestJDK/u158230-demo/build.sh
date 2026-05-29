#!/usr/bin/env bash

dir=$(
  cd $(dirname $0)
  pwd
)
echo "------dir=${dir}"

(
  cd ${dir}/u158230-demo-dep
  echo "==================== u158230-demo-dep"
  mvn dependency:tree install
)

(
  cd ${dir}/u158230-demo-bom
  mvn install
)

(
  cd ${dir}/u158230-demo-app1
  echo "==================== u158230-demo-app1"
  # TODO 检查：确保没有依赖 netty-handler
  mvn dependency:tree | grep netty-handler
  # 简单说：u158230-demo-dep 父 pom 里的 dependencyManagement 只在 u158230-demo-dep 自己的 reactor 内生效，不会跟着 jar 传递给下游。
  # TODO 检查：确保依赖的guava版本仍然是 curator-client 传递依赖的 老版本=32.0.0-jre，而非 u158230-demo-dep 中 dependencyManagement 管理的 高版本 33.5.0-jre， 并输出原因
  mvn dependency:tree | grep "com.google.guava:guava:jar"
  # TODO 检查：确保依赖的slf4j-api版本 并不是 curator-client 传递依赖的 1.7.25 ，而是 u158230-demo-dep 中 dependencyManagement 管理的 高版本 1.7.29， 因为 slf4j-api 在 u158230-demo-app的 pom.xml 声明为直接依赖,且非 optional, scope 是 compile 或者 runtime
  mvn dependency:tree | grep "org.slf4j:slf4j-api:jar"
)

(
  cd ${dir}/u158230-demo-app2
  echo "==================== u158230-demo-app2"
  # TODO 检查：确保没有依赖 netty-handler
  mvn dependency:tree | grep netty-handler
  # 简单说：u158230-demo-dep 父 pom 里的 dependencyManagement 只在 u158230-demo-dep 自己的 reactor 内生效，不会跟着 jar 传递给下游。
  # TODO 检查：确保依赖的guava版本仍然是 curator-client 传递依赖的 老版本=32.0.0-jre，而非 u158230-demo-dep 中 dependencyManagement 管理的 高版本 33.5.0-jre， 并输出原因
  mvn dependency:tree | grep "com.google.guava:guava:jar"
  # TODO 检查：确保依赖的slf4j-api版本 并不是 curator-client 传递依赖的 1.7.25 ，而是 u158230-demo-dep 中 dependencyManagement 管理的 高版本 1.7.29， 因为 slf4j-api 在 u158230-demo-app的 pom.xml 声明为直接依赖,且非 optional, scope 是 compile 或者 runtime
  mvn dependency:tree | grep "org.slf4j:slf4j-api:jar"
)
