package com.github.btpka3.firstbootgraalvm;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author dangqian.zll
 * @date 2023/3/2
 */
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
        ScriptEngine engine = manager.getEngineByName("groovy");
        Object returnObj = engine.eval(script);
        String string = JSON.toJSONString(returnObj);
        return Mono.just(string);
    }
}
