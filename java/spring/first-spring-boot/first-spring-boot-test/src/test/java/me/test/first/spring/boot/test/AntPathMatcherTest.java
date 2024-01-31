package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;
import org.wildfly.common.Assert;

import java.net.MalformedURLException;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class AntPathMatcherTest {

    @Test
    public void test01() throws MalformedURLException {
        AntPathMatcher matcher = new AntPathMatcher();
        {
            String pattern = "/path/**/*.jar";
            Assert.assertTrue(matcher.match(pattern, "/path/aaa/bbb/ccc.jar"));
            Assert.assertFalse(matcher.match(pattern, "file:/path/aaa/bbb/ccc.jar"));
        }
        {
            String pattern = "file:/path/**/*.jar";
            Assert.assertTrue(matcher.match(pattern, "file:/path/aaa/bbb/ccc.jar"));
            Assert.assertTrue(matcher.match(pattern, "file:///path/../aaa/bbb/ccc.jar"));
        }
        {
            String pattern = "jar:file:/path/**/*.jar!/BOOT-INFO/lib/*.jar";
            Assert.assertFalse(matcher.match(pattern, "file:/path/aaa/bbb/ccc.jar"));
            Assert.assertTrue(matcher.match(pattern, "jar:file:/path/aaa/bbb/ccc.jar!/BOOT-INFO/lib/ddd.jar"));
        }
    }


    /**
     * 测试ClassName
     *
     * @throws MalformedURLException
     */
    @Test
    public void test02() throws MalformedURLException {
        AntPathMatcher matcher = new AntPathMatcher(".");
        {
            String pattern = "java.**";
            Assert.assertTrue(matcher.match(pattern, "java.lang.String"));
            Assert.assertTrue(matcher.match(pattern, "java.Xxx"));
            Assert.assertFalse(matcher.match(pattern, "javax.Xxx"));
        }

        {
            String pattern = "aa.bb.Xxx*";
            Assert.assertTrue(matcher.match(pattern, "aa.bb.Xxx"));
            Assert.assertTrue(matcher.match(pattern, "aa.bb.Xxx$1"));
            Assert.assertTrue(matcher.match(pattern, "aa.bb.Xxx$Y$Z"));
        }
    }

    @Test
    public void test03() throws MalformedURLException {
        AntPathMatcher matcher = new AntPathMatcher();
        {
            String pattern = "jrt:/**";
            Assert.assertTrue(matcher.match(pattern, "jrt:/jdk.security.jgss"));
        }
    }

}
