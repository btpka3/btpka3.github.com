package me.test.jdk.java.util.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2023/11/24
 */
public class Iterator2StreamTest {

    @Test
    public void test01() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList(
                "A001",
                "A002",
                "A003",
                "A004",
                "A005",
                "A006",
                "A007",
                "A008",
                "A009"
        ));

        Iterator<String> iterator = list.descendingIterator();
        Stream<String> targetStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );

        List<String> result = targetStream
                .limit(3)
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList("A009", "A008", "A007"), result);

    }
}
