package me.test.jdk.java.util.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Iterable2Stream {
    public static void main(String[] args) {

        Iterable<String> iterable = Arrays.asList("AA", "BB", "CC", "DD", "EE");
        List<String> pagedList = StreamSupport.stream(iterable.spliterator(), false)
                .skip(0 * 10)
                .limit(2)
                .collect(Collectors.toList());

        System.out.println(pagedList);
    }

    public static <T> List<T> getPagedData(Iterable<T> it, int pageNum, int pageSize) {
        return StreamSupport.stream(it.spliterator(), false)
                //.filter(...) // TODO acl 过滤
                .skip(pageSize * pageNum)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
