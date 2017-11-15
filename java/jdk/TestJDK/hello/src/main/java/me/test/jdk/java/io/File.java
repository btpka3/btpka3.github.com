package me.test.jdk.java.io;

import java.util.*;
import java.util.concurrent.*;

public class File {


    public static void main(String[] args) {

        listFiles("/tmp");
    }


    public static void listFiles(String dir) {
        System.out.println("----------------------------------- listFiles");
        BlockingDeque<java.io.File> q = new LinkedBlockingDeque<>();
        q.add(new java.io.File(dir));

        while (q.peekFirst() != null) {
            java.io.File f = q.poll();

            if (!f.exists()) {
                continue;
            }

            System.out.println(f.getAbsolutePath());
            if (f.isDirectory()) {
                Arrays.stream(f.listFiles()).forEach(q::add);
            }
        }
        System.out.println("done.");
    }


}
