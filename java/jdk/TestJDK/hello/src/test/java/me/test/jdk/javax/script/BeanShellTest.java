package me.test.jdk.javax.script;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2024/9/9
 * @see
 */
public class BeanShellTest {

    @Test
    @SuppressWarnings("unchecked")
    public void test01() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory f : factories) {
            System.out.println("egine name:" + f.getEngineName());
            // System.out.println("engine version:" + f.getEngineVersion());
            System.out.println("language name:" + f.getLanguageName());
            System.out.println("language version:" + f.getLanguageVersion());
            System.out.println("names:" + f.getNames());
            System.out.println("mime:" + f.getMimeTypes());
            System.out.println("extension:" + f.getExtensions());
            System.out.println("-----------------------------------------------");
        }
        System.out.println("-----------------------------------------------Done");

        ScriptEngine engine = manager.getEngineByName("bsh");
        engine.put("a", 4);
        engine.put("b", 6);
        Object maxNum = engine.eval("a+b");
        System.out.println("max_num:" + maxNum + ", (class = " + maxNum.getClass() + ")");

    }
}
