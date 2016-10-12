package me.test.groovy.transform

import groovy.transform.AutoClone
import groovy.transform.ToString

@AutoClone
@ToString
class AutoClone01 {
    String name
    int age
    List favs

    static void main(String[] args) {

        // 重点: @ToString

        // Map 构造函数
        def a = new AutoClone01(name: "aa", age: 11, favs: ["x", "y"])
        println a

        def b = a.clone()
        println b

        assert a instanceof Cloneable
        assert b instanceof Cloneable
        assert !a.is(b)
        assert a.name == b.name
        assert a.age == b.age
        assert a.favs == b.favs
        assert !a.favs.is(b.favs)
        println "----OK"
    }
}
