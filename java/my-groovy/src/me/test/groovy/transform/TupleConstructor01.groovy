package me.test.groovy.transform

import groovy.transform.*

@TupleConstructor
@ToString
class TupleConstructor01 {
    String name
    int age

    static void main(String[] args) {

        // 重点: @TupleConstructor

        // Map 构造函数
        def a = new TupleConstructor01(name: "aa", age: 11)
        println a

        // 使用列表一一构造
        def b = new TupleConstructor01("bb", 22)
        println b

        // 按顺序,只部分初始化
        def c = new TupleConstructor01("cc")
        println c
    }
}
