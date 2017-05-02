package me.test.groovy.lang

import groovy.transform.ToString

/**
 *
 * http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#_command_chains
 */
class OperatorOverloading01 {


    @ToString(includeNames = true)
    static class A {
        int num

        A plus(A a) {
            this.num += a.num
            return this
        }
    }

    static void main(String[] args) {
        println([a: "aaa"] + [b: "bbb"])

        println(new A(num: 1) + new A(num: 2))
    }
}
