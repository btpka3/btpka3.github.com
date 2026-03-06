/**
 *
 * @author dangqian.zll
 * @date 2026/3/6
 */
module hello {

//    // ========== Java 标准库模块 ==========
//    requires java.base;
//    requires java.management;          // java.lang.management
//    requires java.compiler;            // javax.tools
//    requires java.desktop;             // java.beans 等
//    requires java.instrument;          // instrumentation
//    requires java.logging;             // java.util.logging
//    requires java.net.http;            // HTTP Client (JDK 11+)
//    requires java.prefs;               // Preferences
//    requires java.rmi;                 // RMI
//    requires java.scripting;           // Scripting API
//    requires java.security.jgss;       // GSS-API
//    requires java.sql;                 // JDBC
//    requires java.xml;                 // XML 处理
//
//    // ========== JavaFX 模块 ==========
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.graphics;
//
//    // ========== Apache Commons ==========
//    requires org.apache.commons.lang3;
//    requires org.apache.commons.collections4;
//    requires org.apache.commons.io;
//    requires org.apache.commons.text;
//    requires org.apache.commons.pool2;
//    requires org.apache.commons.compress;
//    requires org.apache.commons.exec;
//    requires commons.codec;
//    requires commons.cli;
//
//    // ========== 序列化与 JSON ==========
//    requires com.esotericsoftware.kryo;
//    requires kryo5;
//    requires com.alibaba.fastjson2;
//    requires com.fasterxml.jackson.databind;
//
//    // ========== 脚本引擎 ==========
//    requires groovy.all;
//    requires org.graalvm.js.scriptengine;
//    requires org.openjdk.nashorn;
//    requires bsh;
//    requires org.graalvm.polyglot;
//
//    // ========== 网络与响应式编程 ==========
//    requires io.netty.all;
//    requires reactor.core;
//    requires io.vavr;
//
//    // ========== 工具库 ==========
//    requires com.google.common;
//    requires com.google.errorprone.core;
//    requires io.github.classgraph;
//    requires joda.time;
//    requires ulidj;
//    requires java.uuid.generator;
//    requires uuid.creator;
//    requires ognl;
//
//    // ========== 安全与加密 ==========
//    requires org.bouncycastle.provider;
//    requires org.bouncycastle.pkix;
//
//    // ========== 日志 ==========
//    requires org.slf4j;
//    requires logback.classic;
//
//    // ========== 注解处理器（编译期）==========
//    requires static lombok;
//    requires static error.prone.refaster;
//
//    // ========== 测试依赖（仅 test scope）==========
//    requires static junit.jupiter.api;
//    requires static org.junit.platform.engine;
//    requires static org.junit.platform.launcher;
//    requires static org.mockito;
//    requires static archunit;
//    requires static assertj.core;
//    requires static xmlunit.core;
//    requires static json.unit.assertj;
//    requires static jmh.core;
//
//    // ========== 内部模块依赖 ==========
//    requires apt;
//
//    // ========== 开放包给反射（JavaFX FXML 需要）==========
//    opens me.test.jdk.javafx to javafx.fxml, javafx.graphics;
//
//    // ========== 导出的包 ==========
//    exports me.test.jdk.javafx;


// ----------------
    requires javafx.controls;
    requires org.apache.commons.lang3;
    requires javafx.fxml;
    // java package : java.lang.management;
//    requires java.management;
    // java package : javax.tools;
//    requires java.compiler;

    opens me.test.jdk.javafx to javafx.fxml,javafx.graphics;
    exports me.test.jdk.javafx;
}