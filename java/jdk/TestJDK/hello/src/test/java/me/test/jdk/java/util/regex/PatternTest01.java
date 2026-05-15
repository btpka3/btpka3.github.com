package me.test.jdk.java.util.regex;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2021/4/15
 */
public class PatternTest01 {

    @Test
    public void testGetFileExtension() {
        List<String> inputs = Arrays.asList("中国.人民", "中国.人民.txt", "中国.人民.tar.GZ");
        List<String> expectedResults = Arrays.asList(null, "txt", "tar.gz");
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            String result = getExt(input);
            String expectedResult = expectedResults.get(i);
            Assertions.assertEquals(
                    "Expected file extension is `" + expectedResult + "` for input string `" + input + "`, but found `"
                            + result + "`",
                    expectedResult,
                    result);
        }
    }

    protected String getExt(String s) {
        Pattern p = Pattern.compile("((?:\\.[a-zA-Z0-9]+)+)$");
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1).toLowerCase().substring(1) : null;
    }

    @Test
    public void x() {
        try {
            Pattern.compile(".*[\\u0F00-\\u0FFF]{1 ,}.*", 0);
            Assertions.fail("Expected PatternSyntaxException");
        } catch (PatternSyntaxException e) {

        }
    }

    @Test
    public void match301() {

        Pattern p = Pattern.compile("(b+)");
        String s = "aaaabbbb";
        Matcher m = p.matcher(s);

        Assertions.assertFalse(m.matches());
    }

    @Test
    public void match3() {

        Pattern p = Pattern.compile("(.*)(b+)");
        String s = "aaaabbbb";
        Matcher m = p.matcher(s);

        Assertions.assertTrue(m.matches());
        String g1 = m.group(1);
        String g2 = m.group(2);
        System.out.printf("%-30s -> g1=%s, g2=%s %n", s, g1, g2);
        Assertions.assertEquals("aaaabbb", g1);
        Assertions.assertEquals("b", g2);
    }

    @Test
    public void match302() {

        Pattern p = Pattern.compile("(.*?)(?!b+)");
        String s = "aaaabbbb";
        Matcher m = p.matcher(s);

        Assertions.assertTrue(m.matches());
        String g1 = m.group(1);
        String g2 = null; // m.group(2);
        System.out.printf("%-30s -> g1=%s, g2=%s %n", s, g1, g2);
        Assertions.assertEquals("aaaabbb", g1);
        Assertions.assertEquals("b", g2);
    }

    @Test
    public void notContain01() {

        Pattern p = Pattern.compile("^((?!my string).)*$");
        {
            String s = "this is my string :bababa";
            Matcher m = p.matcher(s);

            Assertions.assertFalse(m.matches());
        }
        {
            String s = "this is my another string :lalala";
            Matcher m = p.matcher(s);

            Assertions.assertTrue(m.matches());
        }
    }

    @Test
    public void match01() {

        Pattern p = Pattern.compile("(b+)");
        String s = "aaaabbbb";
        Matcher m = p.matcher(s);

        Assertions.assertTrue(m.find());
        String g = m.group(1);
        System.out.printf("%-30s -> %s, start=%d, end=%d%n", s, g, m.start(), m.end());
    }

    @Test
    public void match02() {
        Pattern p = Pattern.compile("(b+)");
        String s = "bbbbaaaa";
        Matcher m = p.matcher(s);

        Assertions.assertTrue(m.find());

        String g = m.group(1);
        System.out.printf("%-30s -> %s, start=%d, end=%d%n", s, g, m.start(), m.end());
    }

    static Pattern errCodePattern = Pattern.compile("\\[errCode:([\\w\\-.]{1,128})]");

    public static String getErrCode(String msg) {
        Matcher m = errCodePattern.matcher(msg);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    @Test
    public void testErrCode() {
        Assertions.assertEquals("a.B-c_1", getErrCode("111 [errCode:a.B-c_1] 222"));
        Assertions.assertNull(getErrCode("111 222"));
    }

    @Test
    public void testReplaceNumbers() {
        String input = "A123-456-789";
        // 正则解释：
        // (?<![A-Za-z]) : 负向后顾，确保匹配位置的前一个字符不是 A-Z 或 a-z
        // \d+           : 匹配一个或多个连续数字
        //        String regex = "(?<![A-Za-z])\\d+";

        // (?<![\dA-Za-z])：负向后顾，要求前面不能是数字，也不能是字母。
        // 如果是 A123：1 前面是 A，匹配失败。整个 123 都不会被选中（因为 2 前面是 1 也是数字，也失败）。
        // 如果是 -456：4 前面是 -（非数字非字母），匹配成功。选中 456。
        // 如果是 ^789 (开头)：前面没字符，匹配成功。
        String regex = "(?<![\\dA-Za-z])\\d+";
        String result = input.replaceAll(regex, "N");
        System.out.println("原始字符串: " + input);
        System.out.println("替换结果: " + result);

        Assertions.assertEquals("A123-N-N", result);
    }
}
