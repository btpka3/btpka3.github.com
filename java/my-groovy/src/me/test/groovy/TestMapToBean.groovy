package me.test.groovy

import groovy.transform.ToString


@ToString(includeNames = true)
class B {
    String city
    String postCode
}


@ToString(includeNames = true)
class A {
    String name
    int age

    List<String> addrList
    B b
    List<B> bList
}

def a = [
        name    : "aaa",
        age     : 9,
        addrList: ["addr-1", "addr-2"],
        b       : [city: "Weihai", postCode: "000000"],
        bList   : [
                // 使用泛型的类字段，则必须强制明确类型
                [city: "Hangzhou", postCode: "111111"] as B,
                [city: "Beijing", postCode: "222222"] as B
        ]
] as A

// "A(name:aaa, age:9, addrList:[addr-1, addr-2], bList:[[city:Hangzhou, postCode:111111], [city:Beijing, postCode:222222]])"
println a

// "class A"
println a.getClass()

// "class B"
println a.bList[0].getClass()

