#!/usr/bin/env zsh
# 直接调用与项目同版本的 GraalVM 25 的 native-image 构建原生可执行文件。
# 说明：native-maven-plugin 0.10.6 在本机会把 native-image 误解析到另一个 JDK(21) 的 GraalVM，
#       导致 class 版本不匹配；故此处显式用 JAVA_HOME(25) 的 native-image，classpath 与插件一致。
set -e
cd "$(dirname "$0")"

GRAALVM_HOME="${GRAALVM_HOME:-$JAVA_HOME}"
NI="$GRAALVM_HOME/bin/native-image"
if [[ ! -x "$NI" ]]; then
  echo "native-image not found at $NI ; set GRAALVM_HOME to a JDK25 GraalVM" >&2
  exit 1
fi

# 组装 classpath：主 jar + 全部运行时依赖
CP="target/expr-bench.jar"
for j in target/lib/*.jar; do CP="$CP:$j"; done

echo "Using native-image: $NI"
"$NI" \
  -cp "$CP" \
  --no-fallback \
  -O2 \
  -H:+ReportExceptionStackTraces \
  -H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image \
  -H:Path=target \
  -o expr-bench \
  me.btpka3.nativeexpr.Main

echo "Built: target/expr-bench"
ls -lh target/expr-bench
