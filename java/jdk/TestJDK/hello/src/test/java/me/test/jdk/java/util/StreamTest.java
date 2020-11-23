package me.test.jdk.java.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2020/10/26
 */
public class StreamTest {

    @Test
    public void testNullValues01() {

        List<Integer> list = Arrays.asList(101, 102, 103, 104, 105);
        List<String> newList = list.stream()
                .map(i -> (i % 2) == 0 ? null : "" + i)
                .collect(Collectors.toList());

        System.out.println(newList);
    }
}
