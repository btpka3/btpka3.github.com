package me.test.jdk.java.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void test01() {
        Optional<Integer> intOpt = Optional.empty().map(o -> 1);
        System.out.println(intOpt.isPresent());
        System.out.println(intOpt.orElse(999));
    }

    @Test
    public void testFlatMap01() {

        String newValue = Optional.of("a")
                .flatMap(v -> Optional.<String>ofNullable(null))
                .orElse("bbb");

        Assert.assertEquals("bbb", newValue);
    }

    @Test
    public void testFlatMap02() {

        String newValue = Optional.of("a")
                .flatMap(v -> Optional.<String>ofNullable("aaa"))
                .orElse("bbb");

        Assert.assertEquals("aaa", newValue);
    }
}
