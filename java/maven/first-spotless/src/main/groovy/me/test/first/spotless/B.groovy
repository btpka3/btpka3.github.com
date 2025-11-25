package me.test.first.spotless

import org.apache.commons.lang3.StringUtils

import java.util.function.Function;


class B {

  void code1() {
def p = Person.builder().name("zhang3")
        .age(11)
            .gender("male")
        .build();
        System.out.println("person = " + p);
    }
void code2() {
def p = Person.builder().name("zhang3")
                .age(11)
                .gender("male")
                .job("coder")
                .build();
        System.out.println("person = " + p);
    }


       String handle(String s, Function<String, String> fn) {
        return fn.apply(s);
    }

      void code3() {

        Arrays.asList("aaa", "bbb", "ccc").stream()
                .filter(StringUtils::isNotBlank) .map(s->s  + "123")
                .map(s -> handle(s,
                        str->{

                    return s + "aaa";
                            }
                ))
                .forEach(s -> {
                    if (s.length() > 100) {System.out.println(s);} else if (s.length() > 10) {
                        System.out.println(s);} else {
                        System.out.println(s);
                    }
                });
    }
}