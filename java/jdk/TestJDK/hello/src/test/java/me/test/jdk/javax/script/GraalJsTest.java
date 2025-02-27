package me.test.jdk.javax.script;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @see <a href="https://www.graalvm.org/latest/reference-manual/js/ScriptEngine/">GraalVM:Js:ScriptEngine Implementation</a>
 */
public class GraalJsTest {

    @Test
    @SuppressWarnings("unchecked")
    public void test01() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory f : factories) {
            System.out.println("egine name:" + f.getEngineName());
            System.out.println("engine version:" + f.getEngineVersion());
            System.out.println("language name:" + f.getLanguageName());
            System.out.println("language version:" + f.getLanguageVersion());
            System.out.println("names:" + f.getNames());
            System.out.println("mime:" + f.getMimeTypes());
            System.out.println("extension:" + f.getExtensions());
            System.out.println("-----------------------------------------------");
        }
        System.out.println("-----------------------------------------------Done");

        //ScriptEngine engine = manager.getEngineByName("graal-js");
        ScriptEngine engine = manager.getEngineByName("js");
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("polyglot.js.allowHostAccess", true);
        bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);

        engine.put("a", 4);
        engine.put("b", 6);
        Object maxNum = engine.eval("function max_num(a,b){return (a>b)?a:b;}max_num(a,b);");
        System.out.println("max_num:" + maxNum + ", (class = " + maxNum.getClass() + ")");

        @SuppressWarnings("rawtypes")
        Map m = new HashMap();
        m.put("c", 10);
        engine.put("m", m);

        Object obj = engine.eval("var x= max_num(a,m.get('c'));");
        System.out.println("obj:" + obj);
        System.out.println("max_num:" + engine.get("x"));
        Assertions.assertEquals(10, engine.get("x"));
    }
}
