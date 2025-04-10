package me.test.jdk.java.lang.classloader4;

import me.test.jdk.java.lang.classloader4.a.Ccc;
import me.test.jdk.java.lang.classloader4.a.Ddd;

import java.util.Optional;

/**
 * @author dangqian.zll
 * @date 2025/3/9
 */
public class Bbb {
    public MyReturnObj myMethod() throws MyException {
        Ccc ccc = new Ccc();
        return null;
    }

    public MyReturnObj myMethod2() throws MyException {
        Ddd ddd = new Ddd();
        String s = Optional.of("zhang3")
                .map(ddd::myMethod)
                .orElse(null);
        System.out.println(s);
        return null;
    }
}
