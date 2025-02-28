package com.github.btpka3.first.javaagent;

import lombok.SneakyThrows;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/10/28
 */
public class BcelTest {
    @Test
    @SneakyThrows
    public void hello() {
        String target = "java.lang.ClassLoader";
        String superClass = "java.lang.Object";
        JavaClass clazz = Repository.lookupClass(target);
        System.out.println("target=" + target + ", superClass=" + superClass + ", isInstance=" + Repository.instanceOf(clazz, superClass));
        Method[] methods = clazz.getMethods();
        printCode(methods);
    }

    public void printCode(Method[] methods) {
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (!(method.getName().equals("loadClass") && method.getArgumentTypes().length == 1)) {
                continue;
            }

            System.out.println(method);

            Code code = method.getCode();
            if (code != null) // Non-abstract method
                System.out.println(code);
        }
    }
}
