package me.test.maven.first.jar;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathResource rsc = new ClassPathResource("config.properties");
        System.out.println(IOUtils.toString(rsc.getInputStream()));

        Main m = new Main();
        System.out.println(m.add(1, 2));
        System.out.println(m.repeat('A', 3));
    }

    public int add(int a, int b) {
        int c = a + b;
        return c;
    }

    public String repeat(char ch, int repeat) {
        String str = StringUtils.repeat(ch, repeat);
        return str;
    }

}
