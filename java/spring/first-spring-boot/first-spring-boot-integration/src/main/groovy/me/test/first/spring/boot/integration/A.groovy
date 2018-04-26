package me.test.first.spring.boot.integration

public class A {

    public static String a = "aaa"

    static String greeting(String self) {
        'Hello, world!'
    }
    public static void main(String[] args) {
        A.metaClass.'static'.getStaticString = { String i ->
            "WORKING"
        }
        assert String.greeting() == 'Hello, world!'
        assert "WORKING" == A.getStaticString('test')

        A.metaClass.'static'.b = "ccc"
        A.metaClass.'static'.c = { String n -> return c + "- 1" }
        println A.a;
        println A.c("CCC");
    }
}