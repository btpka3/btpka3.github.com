package me.test.jdk.java.lang;

import java.lang.reflect.Method;
import me.test.jdk.java.lang.classloader4.Bbb;
import me.test.jdk.java.lang.classloader4.Xxx;
import me.test.jdk.java.lang.classloader4.Yyy;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/3/9
 */
public class ClassLoader4Test {


    /**
     * 增加 jvm 参数 `-verbose:class` 确认 类加载情况
     */
    @Test
    public void test() {
        System.out.println("==== : " + Xxx.class);
        Class clazz = Bbb.class;
        //
        Method[] methods = clazz.getDeclaredMethods();

        System.out.println("==== : methods = " + methods);

        //  只有  bbb.myMethod() 方法执行 才会触发 Ccc.class 的类加载。
        Bbb bbb = new Bbb();
        bbb.myMethod();
        System.out.println("==== : bbb.myMethod()");


        System.out.println("==== : " + Yyy.class);

    }


}
