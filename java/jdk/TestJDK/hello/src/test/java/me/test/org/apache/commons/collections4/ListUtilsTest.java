package me.test.org.apache.commons.collections4;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2019-05-21
 */
public class ListUtilsTest {

    @Test
    public void intersection01() {
        List<String> list1 = Arrays.asList("aa", "bb", "11", "22");
        List<String> list2 = Arrays.asList("11", "22", "xx", "yy");

        List<String> list3 = ListUtils.intersection(list1, list2);

        Assertions.assertEquals(2, list3.size());
        Assertions.assertTrue(list3.contains("11"));
        Assertions.assertTrue(list3.contains("22"));
    }

    @Test
    public void subtract01() {
        List<String> list1 = Arrays.asList("aa", "bb", "11", "22");
        List<String> list2 = Arrays.asList("11", "22", "xx", "yy");

        List<String> list3 = ListUtils.subtract(list1, list2);

        Assertions.assertEquals(2, list3.size());
        Assertions.assertTrue(list3.contains("aa"));
        Assertions.assertTrue(list3.contains("bb"));
    }
}
