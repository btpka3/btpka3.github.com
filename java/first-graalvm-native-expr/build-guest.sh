#!/usr/bin/env zsh
# 编译 Espresso guest 字节码：把"用户 Java 单类"编译成普通 class，
# 打成 target/guest.jar，运行期经 -Despresso.guest=target/guest.jar 由 Espresso 动态加载。
# 注意：guest 字节码 **不** 进 native image，这正是 Espresso 作为"运行期动态 Java 载体"的价值所在。
#
# guest 经 com.oracle.truffle.espresso.polyglot.Interop 访问宿主对象，编译期需要 espresso-polyglot.jar；
# 该 jar 从 target/lib/espresso-libs-resources-*.jar 内按 OS/arch 提取到 target/guest-lib/，
# 运行期再由引擎经 java.BootClasspathAppend 挂到 guest boot classpath（-Despresso.polyglot 指定）。
set -e
cd "$(dirname "$0")"

JH="${JAVA_HOME:-/Users/zll/.sdkman/candidates/java/25.0.3-graal}"
JAVAC="$JH/bin/javac"
JAR="$JH/bin/jar"

# --- 1. 提取 espresso-polyglot.jar（guest 侧 Interop 辅助类，架构无关的字节码）---
os="$(uname -s | tr '[:upper:]' '[:lower:]')"   # darwin / linux
arch="$(uname -m)"
case "$arch" in
  arm64|aarch64) arch=aarch64 ;;
  x86_64|amd64)  arch=amd64 ;;
esac
RES_JAR="$(ls target/lib/espresso-libs-resources-*.jar 2>/dev/null | head -1)"
[ -n "$RES_JAR" ] || { echo "缺少 espresso-libs-resources jar，请先 mvn package"; exit 1; }
mkdir -p target/guest-lib .tmp/extract
if [ ! -f target/guest-lib/espresso-polyglot.jar ]; then
  ( cd .tmp/extract && "$JAR" xf "../../$RES_JAR" \
      "META-INF/resources/java/espresso-libs/$os/$arch/lib/espresso-polyglot.jar" )
  cp ".tmp/extract/META-INF/resources/java/espresso-libs/$os/$arch/lib/espresso-polyglot.jar" \
     target/guest-lib/espresso-polyglot.jar
  echo "extracted espresso-polyglot.jar ($os/$arch) -> target/guest-lib/"
fi
rm -rf .tmp/extract

# --- 2. 编译 guest 类（ against espresso-polyglot.jar）并打包 ---
mkdir -p target/guest-classes
"$JAVAC" -cp target/guest-lib/espresso-polyglot.jar -d target/guest-classes \
    src/guest/java/me/btpka3/nativeexpr/guest/EspCases.java
"$JAR" --create --file target/guest.jar -C target/guest-classes .
echo "guest built: target/guest.jar"
"$JAR" --list --file target/guest.jar | grep EspCases || true
