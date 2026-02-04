package me.test.jdk.java.nio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/7
 */
public class PathTest {
    @Test
    public void x() {
        Path p0 = Paths.get("/aaa/");
        Path p1 = Paths.get("/aaa");
        Path p2 = Paths.get("/aaa/bbb/ccc/ddd.txt");

        assertEquals("ddd.txt", p2.getFileName().toString());
        {
            Path p3 = p2.relativize(p1);
            System.out.println(p3);
            assertEquals("../../..", p3.toString());
        }
        {
            Path p = p1.relativize(p2);
            System.out.println(p);
            assertEquals("bbb/ccc/ddd.txt", p.toString());
        }
        {
            Path p = p1.relativize(p0);
            System.out.println(p);
            assertEquals("", p.toString());
        }
    }
}
