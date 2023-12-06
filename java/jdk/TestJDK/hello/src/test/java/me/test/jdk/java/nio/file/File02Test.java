package me.test.jdk.java.nio.file;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author dangqian.zll
 * @date 2023/11/28
 */
public class File02Test {

    /**
     * 检查磁盘利用率。
     * <p>
     * public long getTotalSpace()
     * public long getFreeSpace()
     * public long getUsableSpace()
     */
    @Test
    public void testFreeSpace() {
        File f = new File(System.getProperty("user.home"));
        long freeBytes = f.getFreeSpace();
        System.out.println("free = " + freeBytes + " Byte");
        System.out.println("free = " + (freeBytes / 1024) + " KB");
        System.out.println("free = " + (freeBytes / 1024 / 1024) + " MB");
        System.out.println("free = " + (freeBytes / 1024 / 1024 / 1024) + " GB");
    }
}
