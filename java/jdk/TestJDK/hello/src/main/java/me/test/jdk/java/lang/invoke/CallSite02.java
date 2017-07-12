package me.test.jdk.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

@SuppressWarnings("unused")
public class CallSite02 {

    public static int len() {
        return 0;
    }

    private static String printArgs(Object... args) {
        System.out.println(Arrays.deepToString(args));
        return "aaa " + args.length;
    }

    public static void main(String[] args) throws Throwable {
        String a = "abcdefg";
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle handle = MethodHandles.lookup().findVirtual(
                String.class,
                "substring",
                mt);
        System.out.println(handle.invoke(a, 1, 2)); // 输出b

    }

}
