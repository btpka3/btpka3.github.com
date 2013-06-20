package me.test;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

// 调用C函数
public class Test01 {

    public interface CLibrary extends Library {
        static CLibrary INSTANCE = (CLibrary) Native.loadLibrary(
                (Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
        System.out.println("============================= CASE 1");
        for (int i = 0; i < 10; i++) {
            CLibrary.INSTANCE.printf("%s = %d: \n", "num" + i, i);
        }
        CLibrary.INSTANCE.printf("Hello, World\n");
        System.out.println();
    }

}
