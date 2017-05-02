package me.test.groovy.lang

/**
 *
 */
class DelegateTest {

    @Delegate
    Date when
    String title

    static void main(String[] args) {
        DelegateTest t = new DelegateTest(
                title: "aaa",
                when: new Date()
        )
        println t.before(new Date())
    }
}
