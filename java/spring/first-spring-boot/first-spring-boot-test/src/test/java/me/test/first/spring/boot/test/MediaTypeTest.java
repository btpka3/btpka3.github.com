package me.test.first.spring.boot.test;

import org.junit.Test;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

/**
 * @author dangqian.zll
 * @date 2020-03-11
 */
public class MediaTypeTest {

    @Test
    public void test() {
        MediaType mediaType = MediaType.APPLICATION_FORM_URLENCODED;
        System.out.println("'application/x-www-form-urlencoded' type="
                + mediaType.getType()
                + ", subType=" + mediaType.getSubtype());
    }

    @Test
    public void test2() {
        Charset charset = Charset.forName("gb18030");

        System.out.println("charset = " + charset
                + ", displayName = " + charset.displayName()
                + ", aliases = " + charset.aliases()
                + ", name = " + charset.name()

        );
    }
}
