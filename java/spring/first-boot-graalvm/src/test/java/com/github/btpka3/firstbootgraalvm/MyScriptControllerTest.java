package com.github.btpka3.firstbootgraalvm;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2025/3/3
 */
public class MyScriptControllerTest {

    @Test
    public void testJava01() {
        Map<String, String> map = new ConcurrentHashMap<>(256);
        MyScriptController c = new MyScriptController();
        c.testJava(map, 1);
    }

    @Test
    public void testJs01() {
        Map<String, String> map = new ConcurrentHashMap<>(256);
        MyScriptController c = new MyScriptController();
        c.testJs(map, 1);
    }

    /**
     * gradle --warning-mode none test -i --tests com.github.btpka3.firstbootgraalvm.MyScriptControllerTest.graalJs01
     */
    @Test
    public void graalJs01() {
        Map<String, String> map = new ConcurrentHashMap<>(256);
        MyScriptController c = new MyScriptController();
        Mono<String> result = c.graalJs("1+'2'");
        assertEquals("12", result.block());
    }

    @Test
    public void perf01() {
        Map<String, String> map = new ConcurrentHashMap<>(256);
        MyScriptController c = new MyScriptController();
        c.perf(10000);
        c.perf(10000);
        c.perf(10000);
    }

    /**
     *
     */
    @Test
    public void aaa() {

    }
}
