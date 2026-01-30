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

log "------------------jdk9 单模块编译、运行演示"
log "清理"
rm -fr src
rm -fr mods

mkdir src
mkdir mods

mkdir -p src/com.greetings/com/greetings/
mkdir -p mods/com.greetings

log "创建 module 'com.greetings'"
cat >src/com.greetings/module-info.java <<EOF
module com.greetings {
}
EOF

cat >src/com.greetings/com/greetings/Main.java <<EOF
package com.greetings;
public class Main {
    public static void main(String[] args) {
        System.out.println("Greetings!");
    }
}
EOF

log "编译"
javac \
  -d mods/com.greetings \
  src/com.greetings/module-info.java \
  src/com.greetings/com/greetings/Main.java

log "运行"
java \
  --module-path mods \
  -m com.greetings/com.greetings.Main
