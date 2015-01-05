package me.test

import me.test.domain.Address
import me.test.domain.Cart
import me.test.domain.Item

class HiController {

    def index(){
        render "OK"
    }

    def insert(){
        def num = 0
        def now = Calendar.getInstance().getTime()

        def cart  = new Cart()
        cart.userName = 'zhang3'

//        def addr1 = new Address(province:'浙江${num}', city:'杭州${num}',zipCode:'11122${num}')
        def addr2 = new Address(province:'河南${num}', city:'郑州${num}',zipCode:'11133${num}')
        def addr3 = new Address(province:'江苏${num}', city:'南京${num}',zipCode:'11144${num}')

//        addr1.save(flush : false)
//        addr2.save(flush : false)
//        addr3.save(flush : false)

        cart.addresses = [
            new Address(province:'浙江${num}', city:'杭州${num}',zipCode:'11122${num}'),
            addr2,
            addr3
        ]
        println cart.addresses

        cart.items = [
            new Item(name : 'xx面膜', count : 3, createTime : now ),
            new Item(name : 'yy洗发水', count : 1, createTime : now ),
            new Item(name : 'zz香皂', count : 2, createTime : now )
        ]

        cart.save(flush:true)

        render "inserted"
    }

    def select(){

        render "selected"
    }
}
