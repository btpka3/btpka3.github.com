package me.test.groovy.transform

import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
class ToString01 {
    String name
    int age

    static void main(String[] args) {

        // 重点: @ToString

        // Map 构造函数
        def a = new ToString01(name: "aa", age: 11)
        println a

    }
}
