package me.test.groovy.lang
/**
 *
 */
class Dsl01 {


    static void main(String[] args) {
        def show = { println it }
        def square_root = { Math.sqrt(it) }

        def please = { action ->
            [
                    the: { what ->
                        [
                                of: { n -> action(what(n)) }
                        ]
                    }
            ]
        }

        // please(show).the(square_root).of(100)
        please show the square_root of 100
    }
}
