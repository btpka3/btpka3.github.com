package me.test.domain

@groovy.transform.ToString(includeNames = true)
class Cart {

    static embedded = ['addr', 'addrList', 'addrMap']
    static hasMany = [items: Item]
    static mapping = { userName index: true }

    String id
    String userName
    me.test.domain.cart.Address addr

    // List : 元素类型需要明确声明
    List<me.test.domain.cart.Address> addrList

    // Map : Key只能是String? Value的类型需要明确声明?
    Map<String, me.test.domain.cart.Address> addrMap

    // Grails 2.4.5 不能使用domain类中的 inner class，否则当更新时，报错：
    // java.lang.IncompatibleClassChangeError ： Class ... does not implement the requested interface java.util.Map
//    static class Address {
//        String id
//
//        String province
//        String city
//        String zipCode
//    }
}
