package me.test.org.apache.commons.text.diff;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class StringsComparatorTest {

    @Test
    public void test01() {
        StringsComparator cmp = new StringsComparator("ABCFGH", "BCDEFG");
        EditScript<Character> script = cmp.getScript();
        int mod = script.getModifications();

        assertEquals(4, mod);
    }
}
