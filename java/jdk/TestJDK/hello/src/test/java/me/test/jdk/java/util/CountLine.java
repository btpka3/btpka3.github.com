package me.test.jdk.java.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.CharBuffer;
import java.util.Scanner;

/**
 * 展示Scanner的基本用法。
 * 该示例在11G的mysqldump文件运行，结果是21548行。部分行超级长。output:
 * 19408@21548, cost 118 second, initBufSize = 1024, endBufSize = 1048576
 */
public class CountLine {

    public static void main(String[] args) throws IOException {
        final String dumpFile = "/home/zll/tmp/mysql_dump_140928.sql";
        Scanner sc = new Scanner(new FileInputStream(dumpFile));

        int initBufSize = getBufSize(sc);
        int endBufSize = 0;

        final String prefix = "INSERT INTO `trade` VALUES ";
        long line = 0;
        long atLine = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        try {
            while (sc.findWithinHorizon("\n", 0) != null) { // 可以成功计算，对内存峰值～40
                // while (sc.skip("[^\n]*\n") != null) { // 可以成功计算，但需要cache NoSuchElementExcepion, 对内存占用<20M
                line++;
                if (sc.findWithinHorizon(prefix, prefix.length() * 2) != null) {
                    atLine = line;
                }
            }
        } finally {
            endBufSize = getBufSize(sc);
            end = System.currentTimeMillis();
            System.out.println(atLine + "@" + line
                    + ", cost " + ((end - start) / 1000)
                    + " second, initBufSize = " + initBufSize
                    + ", endBufSize = " + endBufSize);
            sc.close();
        }
    }

    private static int getBufSize(Scanner sc) {
        try {
            Field f = sc.getClass().getDeclaredField("buf");
            f.setAccessible(true);
            CharBuffer buf = (CharBuffer) f.get(sc);
            return buf.capacity();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
