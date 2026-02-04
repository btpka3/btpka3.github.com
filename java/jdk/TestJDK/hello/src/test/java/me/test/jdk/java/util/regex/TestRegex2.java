package me.test.jdk.java.util.regex;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex2 {

    public static void main(String[] args) {
        testEscape();
    }

    /**
     * 一个字符串，要么出现在开头，要么出现在逗号后面
     */
    public static void testEscape() {

        /*
        # brew install grep --with-default-names
        cat <<EOF | grep -E -e "^(.*,)?+(abc).*$"
        abcdefg
        aabcde
        aacabc
        ab,ba,abc
        ab,ba,bc
        ab,ba,abcd,bc
        EOF
         */
        String target = "abc";
        List<String> srcList = Arrays.asList("abcdefg", "aabcde", "aacabc", "ab,ba,abc", "ab,ba,bc", "ab,ba,abcd,bc");

        // String reg = "^(abc).*";
        // String reg = ".*(,abc).*";
        // String reg = "(^(abc)|(.*(,abc))).*";
        String reg = "^(.*,)?+(abc).*$";
        Pattern pattern = Pattern.compile(reg);
        srcList.forEach(src -> {
            Matcher matcher = pattern.matcher(src);
            if (matcher.matches()) {
                System.out.println(src);
            }
        });
    }
}
