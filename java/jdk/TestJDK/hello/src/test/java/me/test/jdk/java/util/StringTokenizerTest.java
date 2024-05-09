package me.test.jdk.java.util;

import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;

/**
 * 注意: StringTokenizer 是历史遗留类，不建议使用。
 *
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class StringTokenizerTest {

    @Test
    public void test01() {
        StringTokenizer st = new StringTokenizer("this is a test");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
