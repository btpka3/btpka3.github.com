package me.test.jdk.javax.script;

import groovy.lang.GroovyClassLoader;
import org.graalvm.polyglot.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dangqian.zll
 * @date 2025/3/7
 */
public class Groovy2Test {

    Engine jsEngine;
    //    Value jsFunc;
    Source jsSource;
    Class groovyClazz;
    Object groovyObject;
    Method groovyMethod;

    @BeforeEach
    public void before() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        beforeJava();
        beforeJs();
        beforeGroovy();
    }

    protected void beforeJava() {
    }

    protected void beforeJs() {

        // language=javascript
        String script = """
                (function(map, count){
                    var UUID = Java.type('java.util.UUID');
                    for(var i = 0; i < count; i++){
                        map.clear();
                        map.put('' + i,  'js:' + UUID.randomUUID());
                    }
                })
                """;
        jsEngine = Engine.create();
        jsSource = Source.create("js", script);
    }

    protected void beforeGroovy() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // language=groovy
        String groovyStr = """
                public class MyGrovvy001 {
                    public void testGroovy(Map map, int count) {
                        for (int i = 0; i < count; i++) {
                            map.clear();
                            map.put("" + i, "groovy:" + UUID.randomUUID());
                        }
                    }
                }
                """;

        GroovyClassLoader loader = new GroovyClassLoader();
        groovyClazz = loader.parseClass(groovyStr, "MyGrovvy001.groovy");
        groovyMethod = groovyClazz.getMethod("testGroovy", Map.class, int.class);
        System.out.println("clazz.getName() = " + groovyClazz.getName());
        groovyObject = groovyClazz.getConstructor().newInstance();
    }


    protected void testJava(Map<String, String> map, int count) {
        long start = System.nanoTime();
        for (int i = 0; i < count; i++) {
            map.clear();
            map.put("" + i, "java:" + UUID.randomUUID());
        }
        long end = System.nanoTime();
        long cost = end - start;
        long avg = cost / count;
        System.out.printf("java   test : %10d, avg=%10d, map= %s: %n", cost, avg, map);
    }

    protected void testJs(Map<String, String> map, int count) {

        long start = System.nanoTime();

        try (Context context = Context.newBuilder()
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .engine(jsEngine)
                .build()) {
            Value jsFunc = context.eval(jsSource);
            jsFunc.execute(map, count);
        }
        long end = System.nanoTime();
        long cost = end - start;
        long avg = cost / count;
        System.out.printf("js     test : %10d, avg=%10d, map= %s: %n", cost, avg, map);
    }

    protected void testGroovy(Map<String, String> map, int count) throws InvocationTargetException, IllegalAccessException {
        long start = System.nanoTime();
        groovyMethod.invoke(groovyObject, map, count);
        long end = System.nanoTime();
        long cost = end - start;
        long avg = cost / count;
        System.out.printf("groovy test : %10d, avg=%10d, map= %s: %n", cost, avg, map);
    }

    @Test
    public void test01() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        int loop = 3;
        int count = 10000;

        Map<String, String> map = new ConcurrentHashMap<>(256);
        for (int i = 0; i < loop; i++) {
            testJava(map, count);
        }
        for (int i = 0; i < loop; i++) {
            testJs(map, count);
        }
        for (int i = 0; i < loop; i++) {
            testGroovy(map, count);
        }
    }
}
