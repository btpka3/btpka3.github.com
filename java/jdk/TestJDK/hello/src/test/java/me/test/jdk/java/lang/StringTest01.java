package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class StringTest01 {


    public static void main(String[] args) {

        System.out.println(String.format("%03d", 8));

        testEmoji();

    }

    public static void testEmoji() {
        System.out.println("============================= testEmoji");

        // 😂😍🎉👍💩
        String text = "💩";
        if (text.length() > 1 && Character.isSurrogatePair(text.charAt(0), text.charAt(1))) {
            int codePoint = Character.toCodePoint(text.charAt(0), text.charAt(1));
            System.out.println("codePoint = " + codePoint);
            System.out.println("codePointCount = " + text.codePointCount(0, text.length()));

            char[] charArr = Character.toChars(codePoint);
            for (char c : charArr) {
                System.out.println(c);
            }
        }
        System.out.println(text);
    }

    @Test
    public void threeDoubleQuoteMarks01() {
        String str = """
                aaa   
                """;
        // 删除了头部空白，行尾空白，但保留了换行符
        assertEquals("aaa\n", str);
    }

    @Test
    public void threeDoubleQuoteMarks02() {
        String str = """
                aaa   
                bbb   """;
        assertEquals("aaa\nbbb", str);
    }

    /**
     * 如果行尾通过 "\" escape 换行符，则该行的行尾空白将保留。
     */
    @Test
    public void threeDoubleQuoteMarks03() {
        String str = """
                aaa   \
                bbb   """;
        assertEquals("aaa   bbb", str);
    }

    /**
     * Idea Intellj 中可以通过注释 "// language=<language_ID>" 启用java text block 进行语法着色和校验。
     *
     * @see <a href="https://www.jetbrains.com/help/idea/using-language-injections.html#use-language-injection-comments">IDEA Intellij : Language injections</a></a>
     */
    @Test
    public void threeDoubleQuoteMarks04() {
        // language=JSON
        String str = """
                {
                  "a": "aaa
                }
                """;
        // language=JSON
        String str1="{\"a\":\"aaa}";
    }




}






