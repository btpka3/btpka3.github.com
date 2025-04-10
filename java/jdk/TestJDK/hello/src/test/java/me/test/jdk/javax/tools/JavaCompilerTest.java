package me.test.jdk.javax.tools;

import lombok.SneakyThrows;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 学习 java compiler API
 *
 * @author dangqian.zll
 * @date 2024/3/8
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html">Java Compiler API</a>
 * @see <a href="https://www.javacodegeeks.com/2015/09/java-compiler-api.html">Java Compiler API</a>
 * @see ForwardingJavaFileManager
 */
public class JavaCompilerTest {

    @SneakyThrows
    @Test
    public void compilerJavaSourceFile() {

        final String clazzName = "me.test.jdk.javax.tools.DemoHello";

        URL url = JavaCompilerTest.class.getResource("DemoHello.java");

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

        List<StandardLocation> failedLocations = new ArrayList<>();
        for (StandardLocation location : StandardLocation.values()) {

            System.out.printf("location: %-33s = ", location.name());
            List<? extends File> list = Collections.emptyList();
            try {
                list = IterableUtils.toList(javaFileManager.getLocation(location));
            } catch (Throwable e) {
                failedLocations.add(location);
            }
            Collections.sort(list);
            if (list.isEmpty()) {
                System.out.printf("%s%n", list);
                continue;
            }
            System.out.printf("[%n");
            for (File file : list) {
                System.out.println("    " + file.getAbsolutePath());
            }
            System.out.println("]");


        }
        if (!failedLocations.isEmpty()) {
            System.err.println("failed to get location: " + failedLocations);
        }
        System.out.println("111111");

        // 编译完成后，文件默认保存在: ${PROJECT_ROOT}/hello/target/test-classes/me/test/jdk/javax/tools/DemoHello.class
        Class clazz = javaFileManager.getClassLoader(StandardLocation.CLASS_PATH).loadClass(clazzName);

        Assertions.assertNotNull(clazz);
        System.out.println("clazz = " + clazz.getName());
        System.out.println("clazz.getClassLoader() = " + clazz.getClassLoader());
        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{new String[0]});

    }
}
