package me.test.first.spring.boot.test;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * @author 当千
 * @date 2019-05-15
 */
public class RequestMatcherTest {


    @Test
    public void testAntMatcher001() {
        PathMatcher m = new AntPathMatcher();

        List<String> srcList = Arrays.asList(
                "/ab",
                "/aa",
                "/aac",
                "/aac?x=b",
                "/aa/bb",
                "/aa/bb/cc"
        );

        for (String src : srcList) {

            System.out.println("`" + src + "`" + m.match("/aa*/**/*", src));
        }


    }
}
