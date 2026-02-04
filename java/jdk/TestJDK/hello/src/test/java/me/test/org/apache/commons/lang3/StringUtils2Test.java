package me.test.org.apache.commons.lang3;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class StringUtils2Test {

    @Test
    public void test01() {
        // 分隔符
        String delim = "|";
        // 转义字符
        String esc = "\\";
        String regex = "(?<!" + Pattern.quote(esc) + ")" + Pattern.quote(delim);

        System.out.println("delim=" + delim);
        System.out.println("regex=" + regex);
        System.out.println("=============");

        String str = "A|B|C|D1\\|D2\\|D3|E";
        String[] arr = str.split(regex);
        for (String s : arr) {
            System.out.println(s);
        }
        Assertions.assertEquals(5, arr.length);
        Assertions.assertEquals("A", arr[0]);
        Assertions.assertEquals("B", arr[1]);
        Assertions.assertEquals("C", arr[2]);
        Assertions.assertEquals("D1\\|D2\\|D3", arr[3]);
        Assertions.assertEquals("E", arr[4]);
    }

    /**
     * 无转义字符。
     * <p>
     * 注意: 分割后的 String 数组中，会 trim 掉 尾部的连续空字符串。
     */
    @Test
    public void slip02() {
        String delim = "|";
        // 行首: "A" 前面的两个字段是连续空字符串，但会保留
        // 行尾: "B" 后面的两个字段是连续空字符串，会被trim掉。
        String str = "||A|||B||";
        String regex = Pattern.quote(delim);

        String[] arr = StringUtils.split(str, regex);
        Assertions.assertEquals(2, arr.length);
        Assertions.assertEquals("A", arr[0]);
        Assertions.assertEquals("B", arr[1]);
    }
}
