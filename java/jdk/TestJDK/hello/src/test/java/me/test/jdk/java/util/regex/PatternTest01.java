package me.test.jdk.java.util.regex;

import org.junit.Test;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author dangqian.zll
 * @date 2021/4/15
 */
public class PatternTest01 {

    @Test(expected = PatternSyntaxException.class)
    public void x() {
        Pattern.compile(".*[\\u0F00-\\u0FFF]{1 ,}.*", 0);
    }
}
