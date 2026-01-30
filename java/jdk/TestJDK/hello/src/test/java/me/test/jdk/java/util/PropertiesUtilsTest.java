package me.test.jdk.java.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/26
 */
public class PropertiesUtilsTest {

    @Test
    public void test01() {
        // language=properties
        String str = """
                b=b1
                a=aa
                zhang3=\\u5F203
                li4=李4
                """;

        String result = PropertiesUtils.removeCommentAndSorted(str);

        String expectedStr = Arrays.asList("a=aa", "b=b1", "li4=李4", "zhang3=张3")
                .stream().collect(Collectors.joining(System.lineSeparator()));

        assertEquals(expectedStr, result);
    }
}
