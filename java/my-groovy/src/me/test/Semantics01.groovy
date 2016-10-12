package me.test


class Semantics01 {

    static void main(String[] args) {

        // http://www.groovy-lang.org/semantics.html#_optionality
        // 方法调用时圆括弧(parentheses) 是可以省略的。
        def m = Math.max 1, 2
        println "(Math.max 1,2) == $m"
        // println "(Math.max 1,2) == ${Math.max 1, 2}"

        // max(1,2).power(3).plus(1).upto(11){ ... }
        m = Math.max 1, 2 power(3) plus 1 upto 11, {
            println "upto - $it"
        }
        println "Math.max 1, 2 power(3) plus 1 == $m"

    }

    static String join(String s1, String s2) {
        "$s1@$s2"
    }
}
