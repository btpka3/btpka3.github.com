package me.test.jdk.java.util;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) throws Exception {
        Optional<Integer> intOpt = Optional.empty().map(o -> 1);
        System.out.println(intOpt.isPresent());
        System.out.println(intOpt.orElse(999));
    }

}
