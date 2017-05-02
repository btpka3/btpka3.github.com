package me.test.groovy.lang
/**
 *
 */
class Assert01 {


    static void main(String[] args) {


        def x = 2
        def y = 7
        def z = 5
        def calc = { a, b -> a * b + 1 }
        assert calc(x, y) == [x, z].sum()


    }
}
