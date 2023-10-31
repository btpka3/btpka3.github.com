package com.github.btpka3.hello.maven.plugin;

/**
 * @author dangqian.zll
 * @date 2023/10/13
 */

import java.util.Comparator;
import java.util.Objects;

public class TypeComparator implements Comparator<String> {

    boolean reverse;
    Comparator<String> c;

    public TypeComparator(boolean reverse, Comparator<String> c) {
        this.reverse = reverse;
        this.c = c;
    }

    @Override
    public int compare(String c1, String c2) {

        if ((Objects.equals("jar", c1) || Objects.equals("bundle", c1)) && (Objects.equals("jar", c2) || Objects.equals("bundle", c2))) {
            return 0;
        }
        return c1.compareTo(c2);
    }

    @Override
    public Comparator<String> reversed() {
        return new TypeComparator(!reverse, c.reversed());
    }
}