package me.test.jdk.java.util.stream;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortTest {


    public static void main(String[] args) {


        Set<String> s = new HashSet<>(8);
        s.add("bb");
        s.add("cc");
        s.add("aa");
        s.add("zz");
        s.add("a1");
        String str = s.stream()
                .sorted()
                .collect(Collectors.joining("\n"));

        System.out.println(str+"___");
    }

}
