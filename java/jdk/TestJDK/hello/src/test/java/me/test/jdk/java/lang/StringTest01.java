package me.test.jdk.java.lang;

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
}
