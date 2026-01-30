package me.test.org.apache.commons.collections4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.SetUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2019-05-21
 */
public class SetUtilsTest {

    @Test
    public void intersection01() {
        Set<String> set1 = new HashSet<>(Arrays.asList("aa", "bb", "11", "22"));
        Set<String> set2 = new HashSet<>(Arrays.asList("11", "22", "xx", "yy"));

        Set<String> set3 = SetUtils.intersection(set1, set2);

        Assertions.assertEquals(2, set3.size());
        Assertions.assertTrue(set3.contains("11"));
        Assertions.assertTrue(set3.contains("22"));
    }

    @Test
    public void difference01() {
        Set<String> set1 = new HashSet<>(Arrays.asList("aa", "bb", "11", "22"));
        Set<String> set2 = new HashSet<>(Arrays.asList("11", "22", "xx", "yy"));
        Set<String> set3 = SetUtils.difference(set1, set2);
        Assertions.assertEquals(2, set3.size());
        Assertions.assertTrue(set3.contains("aa"));
        Assertions.assertTrue(set3.contains("bb"));

    }
}
