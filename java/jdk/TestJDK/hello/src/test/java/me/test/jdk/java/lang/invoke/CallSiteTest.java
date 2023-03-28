package me.test.jdk.java.lang.invoke;

import java.lang.invoke.*;
import java.util.Arrays;

@SuppressWarnings("unused")
public class CallSiteTest {

    public static int len() {
        return 0;
    }

    private static String printArgs(Object... args) {
        System.out.println(Arrays.deepToString(args));
        return "aaa " + args.length;
    }

    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 找到前面定义的 #printArgs(Object[]) 私有方法
        @SuppressWarnings("rawtypes")
        Class callerClass = lookup.lookupClass(); // (who am I?)

        // callerClass = class me.test.jdk.java.lang.invoke.CallSiteTest
        System.out.println("callerClass = " + callerClass);

        MethodHandle printArgsMethod = lookup.findStatic(
                callerClass,
                "printArgs",
                MethodType.methodType(String.class, Object[].class));

        CallSite callSite1 = new ConstantCallSite(printArgsMethod);
        callSite1.dynamicInvoker().invoke("xxx", "yyy");
        callSite1.dynamicInvoker().invoke("xxx", "yyy", 111);

    }

}
