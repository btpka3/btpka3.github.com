package me.test.maven.first.jar;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        ClassPathResource rsc = new ClassPathResource("config.properties");
        logger.info(IOUtils.toString(rsc.getInputStream()));

        Main m = new Main();
        logger.info(Integer.toString(m.add(1, 2)));
        logger.info(m.repeat('A', 3));
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
