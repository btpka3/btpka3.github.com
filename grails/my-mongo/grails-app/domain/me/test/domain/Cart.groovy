package me.test.domain

@groovy.transform.ToString(includeNames = true)
class Cart {

    static embedded = ['addr', 'addrList', 'addrMap']
    static hasMany = [items: Item]
    static mapping = { userName index: true }

    String id
    String userName
    Address addr

    // List : 元素类型需要明确声明
    List<Address> addrList

    // Map : Key只能是String? Value的类型需要明确声明?
    Map<String, Address> addrMap
}
