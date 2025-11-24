package me.test.first.spotless;

import java.util.Arrays;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

/**
 */
public class A {

    public void code1() {
Person p = Person.builder().name("zhang3")
        .age(11)
            .gender("male")
        .build();
        System.out.println("person = " + p);
    }

    public void code2() {
Person p = Person.builder().name("zhang3")
                .age(11)
                .gender("male")
                .job("coder")
                .build();
        System.out.println("person = " + p);
    }

    public String handle(String s, Function<String, String> fn) {
        return fn.apply(s);
    }

    public void code3() {

        Arrays.asList("aaa", "bbb", "ccc").stream()
                .filter(StringUtils::isNotBlank) .map(s->s  + "123")
                .map(s -> handle(s,
                        str->{

                    return s + "aaa";
                            }
                ))
                .forEach(s -> {
                    if (s.length() > 100) {
                        System.out.println(s);
                    } else if (s.length() > 10) {
                        System.out.println(s);
                    } else {
                        System.out.println(s);
                    }
                });
    }
}
