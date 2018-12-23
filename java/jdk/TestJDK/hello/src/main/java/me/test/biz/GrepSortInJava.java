package me.test.biz;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 每周一题：用JAVA代码完成以下功能，要求使用多线程
 * cat /home/admin/logs/*.log | grep "Login" | uniq -c | sort -nr
 *
 * @author 当千
 * @date 2018-12-22
 */
public class GrepSortInJava {


    public static void main(String[] args) throws IOException {

        String searchPath = args[0];

        Files.walk(Paths.get(searchPath))
                .filter(path -> path.toFile().isDirectory())
                .filter(path -> path.toString().endsWith(".log"))

                //.flatMap(path->Files.lines(path, StandardCharsets.UTF_8))
                .forEach(System.out::println);


    }


    static void findInFile(InputStream in) {


    }


}
