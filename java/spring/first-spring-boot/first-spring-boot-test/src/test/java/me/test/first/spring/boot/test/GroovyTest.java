package me.test.first.spring.boot.test;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.MethodClosure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2021/3/15
 * @see MethodClosure
 * @see Closure
 */
public class GroovyTest {

    @Test
    public void test01() {

        GroovyShell shell = new GroovyShell();
        String scriptText = "myAdd(1,2)";
        Script script = compileScript(shell, scriptText);
        Binding binding = new Binding();
        Closure c = new Closure(null, null) {
            public Object call(Object... args) {
                return ((int) args[0]) + ((int) args[1]);
            }
        };
        binding.setVariable("myAdd", c);
        script.setBinding(binding);
        Object result = script.run();
        System.out.println("result = " + result);
        Assertions.assertEquals(3, result);
    }

    public static class MyAdd {
        public Object call(Object... args) {
            return ((int) args[0]) + ((int) args[1]);
        }
    }

    public static class MyAdd2 {
        public Object call(int a, int b) {
            return a + b;
        }
    }

    @Test
    public void testMyAdd() {

        GroovyShell shell = new GroovyShell();
        String scriptText = "myAdd(1,2)";
        Script script = compileScript(shell, scriptText);
        Binding binding = new Binding();
        MyAdd c = new MyAdd();
        binding.setVariable("myAdd", c);
        script.setBinding(binding);
        Object result = script.run();
        System.out.println("result = " + result);
        Assertions.assertEquals(3, result);
    }


    @Test
    public void testMyAdd2() {
        GroovyShell shell = new GroovyShell();
        String scriptText = "myAdd(1,2)";
        Script script = compileScript(shell, scriptText);
        Binding binding = new Binding();
        MyAdd2 c = new MyAdd2();
        binding.setVariable("myAdd", c);
        script.setBinding(binding);
        Object result = script.run();
        System.out.println("result = " + result);
        Assertions.assertEquals(3, result);
    }


    protected Script compileScript(GroovyShell shell, String scriptText) {
        if (StringUtils.isBlank(scriptText)) {
            return null;
        }
        try {
            Class scriptClazz = shell.getClassLoader().parseClass(scriptText);
            Binding emptyBinding = new Binding();
            return InvokerHelper.createScript(scriptClazz, emptyBinding);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
