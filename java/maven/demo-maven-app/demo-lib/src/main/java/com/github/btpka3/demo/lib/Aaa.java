package com.github.btpka3.demo.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aaa {

    // TODO 完善注释
    public int add(int a, int b) {

        int c = a + b;
        return 1 + c;
    }

    public String join(String... strs) {

        // FIXME 完善测试
        return Stream.of(strs)
                .collect(Collectors.joining(","));
    }

    public List<String> toList(String... strs) {
        List<String> list = new ArrayList<>(strs.length);
        for (String str : strs) {
            list.add(str);
        }
        return list;
    }

}
