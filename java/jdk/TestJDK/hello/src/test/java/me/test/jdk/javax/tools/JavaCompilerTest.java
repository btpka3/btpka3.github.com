package me.test.jdk.javax.tools;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.tools.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 学习 java compiler API
 *
 * @author dangqian.zll
 * @date 2024/3/8
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html">Java Compiler API</a>
 * @see <a href="https://www.javacodegeeks.com/2015/09/java-compiler-api.html">Java Compiler API</a>
 */
public class ToolProviderTest {

    @SneakyThrows
    @Test
    public void compilerJavaSourceFile() {

        final String clazzName = "me.test.jdk.javax.tools.DemoHello";

        URL url = ToolProviderTest.class.getResource("DemoHello.java");

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        System.out.println("current java compiler supported versions : " + compiler.getSourceVersions());
        StandardJavaFileManager javaFileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        javaFileManager.setLocation(StandardLocation.CLASS_PATH, null);

        // 实际的实现类: com.sun.tools.javac.file.JavacFileManager
        System.out.println("javaFileManager's class = " + javaFileManager.getClass().getName());
        Iterable<? extends JavaFileObject> javaFileObjects = javaFileManager.getJavaFileObjects(url.getFile());
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, collector, null, null, javaFileObjects);
        Boolean result = task.call();

        if (result == null || !result) {
            System.out.println("compile failed");
            collector.getDiagnostics().forEach(System.out::println);
        }

        Assertions.assertTrue(result);

        // 编译完成后，文件默认保存在: ${PROJECT_ROOT}/hello/target/test-classes/me/test/jdk/javax/tools/DemoHello.class
        Class clazz = javaFileManager.getClassLoader(null).loadClass(clazzName);
        Assertions.assertNotNull(clazz);
        System.out.println("clazz = " + clazz.getName());
        System.out.println("clazz.getClassLoader() = " + clazz.getClassLoader());


    }
}
