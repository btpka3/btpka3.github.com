package me.test.jdk.java.util;

import java.io.*;
import java.util.*;

public class HashMapTest {


    public static void main(String[] args) throws IOException {
        test01();
    }


    public static void test01() throws IOException {

        //Map<String, String> m = new LinkedHashMap<>();
        Map<String, String> m = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        m.put("a", "aaa");
        m.put("b", "bbb");
        m.put("c", "ccc");

        // 每1秒新增一个元素
        new Thread(() -> {
            try {
                Thread.sleep(3000);// 确保已经删除一个了。
                for (int i = 0; i < 10; i++) {
                    String key = "a" + i;
                    m.put(key, key);

                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(m);
        }).start();

        // 每2秒删1个元素
        new Thread(() -> {
            Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
            try {
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next(); // ConcurrentModificationException
                    it.remove();
                    System.out.println("---------- 删除 " + entry);
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
