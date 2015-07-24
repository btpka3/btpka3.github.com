package me.test.domain


@groovy.transform.ToString(includeNames = true)
class Cart {

    static embedded = ['addr', 'addrList', 'addrMap', 'addrListMap', 'itemListMap', 'itemMap']
    static hasMany = [
            items   : Item,
            itemList: Item
    ]
    static mappedBy = [
            itemList: null
    ]
    static mapping = {
        userName index: true
    }
    static constraints = {
        addrListMap nullable: true
        addrMap nullable: true
        addrList nullable: true
        addr nullable: true
        userName nullable: true
        itemListMap nullable: true
        itemMap nullable: true
        itemList nullable: true
    }


    String id
    String userName
    me.test.domain.cart.Address addr

    // List : 元素类型需要明确声明
    List<me.test.domain.cart.Address> addrList

    // Map : Key只能是String? Value的类型需要明确声明?
    Map<String, me.test.domain.cart.Address> addrMap

    // Map : List<非Domain类型>
    Map<String, List<me.test.domain.cart.Address>> addrListMap

    // Map : List<Domain类型>
    Map<String, List<Item>> itemListMap

    // Map : value = Domain  Value的类型不能是 Domain？
    Map<String, Item> itemMap

    // List : value = Domain  需要：配置好 hasMany 和 mappedBy。 注意和 items 的区别
    List<Item> itemList

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
