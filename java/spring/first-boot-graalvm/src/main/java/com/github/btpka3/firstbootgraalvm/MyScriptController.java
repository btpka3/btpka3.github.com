package com.github.btpka3.firstbootgraalvm;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dangqian.zll
 * @date 2023/3/2
 */
@Slf4j
@RestController
@RequestMapping("/script")
public class MyScriptController {

    @PostMapping(value = "/groovy", consumes = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> groovy(

            @RequestBody String script
    ) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Object returnObj = engine.eval(script);
        String string = JSON.toJSONString(returnObj);
        return Mono.just(string);
    }

    @PostMapping(value = "/js", consumes = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> js(
            @RequestBody String script
    ) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object returnObj = engine.eval(script);
        String string = JSON.toJSONString(returnObj);
        return Mono.just(string);
    }

    @PostMapping(value = "/graalJs", consumes = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> graalJs(
            @RequestBody String script
    ) {
        try (Context context = Context.newBuilder()
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .build()) {
            Value value = context.eval("js", script);
            return Mono.just(value.asString());
        }
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
        System.out.printf("java test : %10d, avg=%10d, map= %s: %n", cost, avg, map);
    }

    protected void testJs(Map<String, String> map, int count) {
        long start = System.nanoTime();

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
        try (Context context = Context.newBuilder()
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .build()) {

            Value jsFunc = context.eval("js", script);
            jsFunc.execute(map, count);
        }
        long end = System.nanoTime();
        long cost = end - start;
        long avg = cost / count;
        System.out.printf("js   test : %10d, avg=%10d, map= %s: %n", cost, avg, map);
    }

    /**
     * 验证 native 下 ： java vs. js 的操作 java对象的性能。
     */
    @GetMapping(value = "/perf")
    public Mono<String> perf(@RequestParam(value = "count", defaultValue = "1") int count) {

        Map<String, String> map = new ConcurrentHashMap<>(256);
        testJava(map, count);
        testJs(map, count);
        return Mono.just("Done.");
    }


}
