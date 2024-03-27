package me.test.jdk.java.util.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author dangqian.zll
 * @date 2021/4/15
 */
public class PatternTest01 {

    @Test
    public void testGetFileExtension() {
        List<String> inputs = Arrays.asList(
                "中国.人民",
                "中国.人民.txt",
                "中国.人民.tar.GZ"
        );
        List<String> expectedResults = Arrays.asList(
                null,
                "txt",
                "tar.gz"
        );
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            String result = getExt(input);
            String expectedResult = expectedResults.get(i);
            Assertions.assertEquals(
                    "Expected file extension is `" + expectedResult + "` for input string `" + input + "`, but found `" + result + "`",
                    expectedResult,
                    result
            );
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
        }catch (PatternSyntaxException e){

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
        String g2 = null;//m.group(2);
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




}
