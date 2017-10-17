#!/usr/bin/env bash


log(){
    #Cyan
    Color_ON='\033[0;36m'
    Color_Off='\033[0m'
    echo -e "${Color_ON}$(date +%Y-%m-%d.%H:%M:%S) : $1${Color_Off}" 1>&2
}

# 参考 http://openjdk.java.net/projects/jigsaw/quick-start

# 列出 JDK 自带的 modules
# java --list-modules"

log "------------------jdk9 多模块编译、运行演示"
log "清理"
rm -fr src
rm -fr mods

mkdir src
mkdir mods

log "创建 module 'org.astro'"
mkdir -p src/org.astro/
mkdir -p src/org.astro/org/astro/

cat > src/org.astro/module-info.java <<EOF
module org.astro {
    exports org.astro;
}
EOF

cat > src/org.astro/org/astro/World.java <<EOF
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
cat > src/com.greetings/module-info.java <<EOF
module com.greetings {
    requires org.astro;
}
EOF

cat > src/com.greetings/com/greetings/Main.java <<EOF
package com.greetings;
import org.astro.World;
public class Main {
    public static void main(String[] args) {
        System.out.format("Greetings %s!%n", World.name());
    }
}
EOF


# 一个module， 一个 module 顺序编译
log "编译"
mkdir -p mods/org.astro mods/com.greetings
javac \
    -d mods/org.astro \
    src/org.astro/module-info.java \
    src/org.astro/org/astro/World.java

javac \
    --module-path mods \
    -d mods/com.greetings \
    src/com.greetings/module-info.java \
    src/com.greetings/com/greetings/Main.java


log "运行"
java \
    --module-path mods \
    -m com.greetings/com.greetings.Main


:<<EOF
# 演示 module-info.java
module com.socket {
    requires org.astro;

    exports com.socket;

    uses com.socket.spi.NetworkSocketProvider;

    provides com.socket.spi.NetworkSocketProvider
        with org.fastsocket.FastNetworkSocketProvider;
}
EOF