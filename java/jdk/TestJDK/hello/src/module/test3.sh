#!/usr/bin/env bash

log() {
  #Cyan
  Color_ON='\033[0;36m'
  Color_Off='\033[0m'
  echo -e "${Color_ON}$(date +%Y-%m-%d.%H:%M:%S) : $1${Color_Off}" 1>&2
}

# 参考 http://openjdk.java.net/projects/jigsaw/quick-start

# 列出 JDK 自带的 modules
# java --list-modules"

log "------------------jdk9 多模块编译、打包、运行演示"

log "清理"
rm -fr src
rm -fr mods
rm -fr mlib

mkdir src
mkdir mods
mkdir mlib

log "创建 module 'org.astro'"
mkdir -p src/org.astro/
mkdir -p src/org.astro/org/astro/

cat >src/org.astro/module-info.java <<EOF
module org.astro {
    exports org.astro;
}
EOF

cat >src/org.astro/org/astro/World.java <<EOF
package org.astro;
public class World {
    public static String name() {
        return "world";
    }
}
EOF

log "创建 module 'com.greetings'"
mkdir -p src/com.greetings/com/greetings/
mkdir -p mods/com.greetings
cat >src/com.greetings/module-info.java <<EOF
module com.greetings {
    requires org.astro;
}
EOF

cat >src/com.greetings/com/greetings/Main.java <<EOF
package com.greetings;
import org.astro.World;
public class Main {
    public static void main(String[] args) {
        System.out.format("Greetings %s!%n", World.name());
    }
}
EOF

# 一次性编译所有 module
log "编译"

mkdir -p mods/org.astro mods/com.greetings
javac \
  -d mods \
  --module-source-path src $(find src -name "*.java")

log "打包"
jar \
  --create \
  --file=mlib/org.astro@1.0.jar \
  --module-version=1.0 \
  -C mods/org.astro \
  .

jar \
  --create \
  --file=mlib/com.greetings.jar \
  --main-class=com.greetings.Main \
  -C mods/com.greetings \
  .

log "运行"
java \
  -p mlib \
  -m com.greetings
