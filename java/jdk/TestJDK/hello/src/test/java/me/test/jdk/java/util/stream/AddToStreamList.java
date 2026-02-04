package me.test.jdk.java.util.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddToStreamList {
    public static void main(String[] args) {

        List<Integer> list =
                Stream.of(1, 2, 3, 4, 5, 6).filter(i -> (i % 2) == 0).collect(Collectors.toList());

        System.out.println(list.getClass());
        list.add(9);
        System.out.println(list);
    }
}
