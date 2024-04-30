package me.test.org.apache.commons.text;


import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.LookupTranslator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/4/28
 */
public class StringEscapeUtilsTest {


    @Test
    public void translate01() {
        // 自定义格式: `field1|field2|k1=v1,k2=v2,k3=v3|filed4`

        final Map<CharSequence, CharSequence> escapeJsonMap = new HashMap<>();
        escapeJsonMap.put("|", "\\|");
        AggregateTranslator a = new AggregateTranslator(
                new LookupTranslator(Collections.unmodifiableMap(escapeJsonMap))
        );

        {
            String input = "ab";
            String result = a.translate(input);
            Assertions.assertNotSame(result, input);
            Assertions.assertEquals(result, input);
        }
        {
            String input = "a|b";
            String result = a.translate(input);
            Assertions.assertNotSame(result, input);
            Assertions.assertEquals("a\\|b", result);
        }
    }
}
