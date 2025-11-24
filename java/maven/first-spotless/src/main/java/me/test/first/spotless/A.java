package me.test.first.spotless;

/**
 */
public class A {

    public void code1    (){
Person p = Person.builder().name("zhang3")
        .age(11)
            .gender("male")
        .build();
        System.out.println("person = " + p);
    }
}
