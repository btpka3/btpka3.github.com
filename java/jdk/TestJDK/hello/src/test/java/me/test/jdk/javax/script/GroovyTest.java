package me.test.jdk.javax.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/2/28
 */
public class GroovyTest {


    @Test
    @SuppressWarnings("unchecked")
    public void test01() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        engine.put("a", 4);
        engine.put("b", 6);
        Object maxNum = engine.eval("def max_num(int a,int b){return (a>b)?a:b;}; return max_num(a,b);");
        System.out.println("max_num:" + maxNum + ", (class = " + maxNum.getClass() + ")");
    }
}
