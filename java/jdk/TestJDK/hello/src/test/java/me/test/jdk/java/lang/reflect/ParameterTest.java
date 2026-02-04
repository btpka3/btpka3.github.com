package me.test.jdk.java.lang.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/10/16
 */
public class ParameterTest {

    public static void m1(String str) {}

    public static void m2(int str) {}

    public static void m3(String... strVarArg) {}

    public static void m4(String[] strArr) {}

    public static void m5(Map map) {}

    public static void m6(Map<String, String> map) {}

    @Test
    public void test() {
        Method[] methods = ParameterTest.class.getMethods();
        Arrays.stream(methods)
                .filter(m -> m.getName().startsWith("m"))
                .sorted(Comparator.comparing(Method::getName))
                .forEach(m -> {
                    System.out.println(m.getName() + "  : " + m.getParameters()[0].toString());
                });
    }
}
