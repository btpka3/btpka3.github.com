package me.test.org.apache.commons.collections4;

import org.apache.commons.collections4.SetUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

        Assert.assertEquals(2, set3.size());
        Assert.assertTrue(set3.contains("11"));
        Assert.assertTrue(set3.contains("22"));
    }

    @Test
    public void difference01() {
        Set<String> set1 = new HashSet<>(Arrays.asList("aa", "bb", "11", "22"));
        Set<String> set2 = new HashSet<>(Arrays.asList("11", "22", "xx", "yy"));
        Set<String> set3 = SetUtils.difference(set1, set2);
        Assert.assertEquals(2, set3.size());
        Assert.assertTrue(set3.contains("aa"));
        Assert.assertTrue(set3.contains("bb"));

    }
}
