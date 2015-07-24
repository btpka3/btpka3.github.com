package me.test

import me.test.domain.Cart
import me.test.domain.Item
import me.test.domain.cart.Address
import org.grails.datastore.mapping.mongo.query.MongoQuery

class HiController {

    def index() {
        render "OK"
    }

    def insert() {
        def now = Calendar.getInstance().getTime()

        10.times { num ->
            def cart = new Cart()
            cart.userName = "zhang3"

            // 为了方便起见，Embed 文档都最好在其Domain类内声明为 public static class
            // 比如 me.test.domain.Cart.Address
            cart.addr = new Address(
                    province: "河南${num}",
                    city: "郑州${num}",
                    zipCode: "11133${num}")

            cart.addrList = [
                    new Address(
                            province: "浙江${num}",
                            city: "杭州${num}",
                            zipCode: "11122${num}"),
                    new Address(
                            province: "浙江${num}",
                            city: "杭州${num}",
                            zipCode: "11123${num}")
            ]

            cart.addrMap = [
                    "key1": new Address(
                            province: "江苏${num}",
                            city: "南京${num}",
                            zipCode: "11144${num}"),
                    "key1": new Address(
                            province: "江苏${num}",
                            city: "南京${num}",
                            zipCode: "11145${num}")
            ]

            cart.items = [
                    new Item(name: "xx面膜${num}", count: 3, createTime: now),
                    new Item(name: "yy洗发水${num}", count: 1, createTime: now),
                    new Item(name: "zz香皂${num}", count: 2, createTime: now)
            ]
            cart.save(flush: true)
        }




        render "inserted " + System.currentTimeMillis()
    }

    private String printAll(list) {
        def buf = new StringBuilder()
        buf.append(list ? list.size() : 0)
        buf.append("<br/>\n")

        list.each {
            buf.append(it ?: "null")
            buf.append("<br/>\n")
        }
        return buf.toString()
    }

    //
    def all() {
        def cartList = Cart.createCriteria().list {
        }
        render "all <br/>\n" + printAll(cartList)
    }

    // WHERE：一级属性
    def where1() {
        def cartList = Cart.createCriteria().list {
            eq("userName", "zhang3")
        }
        render "where1 <br/>\n" + printAll(cartList)
    }

    // WHERE：内部类的属性1
    def where2() {
        def cartList = Cart.createCriteria().list {
            eq("addr.province", "河南3")
        }
        render "where2 <br/>\n" + printAll(cartList)
    }

    // WHERE：内部类的属性2
    def where3() {
        def cartList = Cart.createCriteria().list {
            addr {
                eq("province", "河南3")
            }
        }
        render "where3 <br/>\n" + printAll(cartList)
    }

    // WHERE：内部LIST的属性1
    def where4() {
        def cartList = Cart.createCriteria().list {
            addrList {
                eq("province", "浙江4")
            }
        }
        render "where4 <br/>\n" + printAll(cartList)
    }

    // WHERE：内部LIST的属性1
    def where5() {
        def cartList = Cart.createCriteria().list {
            eq("addrList.province", "浙江4")
        }
        render "where5 <br/>\n" + printAll(cartList)
    }

    // WHERE：内部Map的属性1
    def where6() {
        def cartList = Cart.createCriteria().list {
            eq("addrMap.key1.city", "南京8")

            // Map字段不支持以下写法：
            // addrMap {
            //     key1 {
            //         eq("city", "南京8")
            //     }
            // }
        }
        render "where6 <br/>\n" + printAll(cartList)
    }

    // WHERE：外部引用对象属性 (不支持)
    // Join queries are not supported by MongoDB
    def where7() {
        def cartList = Cart.createCriteria().list {
            items {
                gt("count", 2)
            }
        }
        render "where7 <br/>\n" + printAll(cartList)
    }

    // ORDER : 一级属性
    def order1() {
        def cartList = Cart.createCriteria().list {
            order("id", "desc")
        }
        render "order1 <br/>\n" + printAll(cartList)
    }

    // ORDER : 内部对象的属性
    def order2() {
        def cartList = Cart.createCriteria().list {
            order("addr.city", "desc")
        }
        render "order2 <br/>\n" + printAll(cartList)
    }

    // LIMIT：分页
    def limit1() {
        def cartList = Cart.createCriteria().list(max: 3, offset: 1) {
        }
        render "limit1 <br/>\n" + printAll(cartList)
    }

    // COUNT：去重
    def count1() {
        def cartList = Cart.createCriteria().list() {
            projections {
                countDistinct "userName"
            }
        }
        render "count1 <br/>\n" + printAll(cartList)
    }

    // COUNT：不去重
    def count2() {
        def cartList = Cart.createCriteria().list() {
            projections {
                // count("userName") // 不支持
                rowCount()
            }
        }
        render "count2 <br/>\n" + printAll(cartList)
    }

    def cursor1() {
        // TYPE : org.grails.datastore.mapping.mongo.query.MongoQuery$MongoResultList
        MongoQuery.MongoResultList obj = Cart.createCriteria().scroll() {
        }
        println(obj.class)
        def iterator = obj.iterator()
        while (iterator.hasNext()) {
            Cart cart = iterator.next()
            println cart
            println cart.items.class  // org.grails.datastore.mapping.collection.PersistentSet
            println cart.items[0].name // 外部关联对象虽然不能JOIN查询条件，但查询结果中可以直接使用。
        }
        render "cursor1 <br/>\n"
    }


    def update1() {
        def cartList = Cart.createCriteria().list(max: 1, offset: 1) {
        }
        Cart cart = cartList[0];
        cart.addr.city = '郑州111'
        cart.save(flush: true)
        render "update1 <br/>\n"
    }


    def addrListMap() {
        Cart cart = new Cart();
        cart.userName = "zhang3"
        cart.addrListMap = [:]
        2.times { i ->
            def list = []
            cart.addrListMap.put("key_" + i, list)
            3.times { num ->

                list.add(new Address(
                        province: "河南${num}",
                        city: "郑州${num}",
                        zipCode: "11133${num}"
                ))
            }
        }
        cart.save(flush: true)
        render "~~~~~~#" + System.currentTimeMillis()
    }

    def itemListMap() {
        def now = Calendar.getInstance().getTime()

        def item1 = new Item(name: "yy面膜1", count: 3, createTime: now)
        item1.save(flush: true);
        def item2 = new Item(name: "yy面膜2", count: 3, createTime: now)
        item2.save(flush: true);
        def item3 = new Item(name: "yy面膜3", count: 3, createTime: now)
        item3.save(flush: true);



        Cart cart = new Cart();
        cart.userName = "zhang3"
        cart.itemListMap = [:]
        2.times { i ->
            def list = []
            cart.itemListMap.put("key_" + i, list)
            list.add(item1)
            list.add(item2)
            list.add(item3)
        }
        cart.save(flush: true)
        render "~~~~~~#!!" + System.currentTimeMillis() + "    " + cart
    }

    def itemMap() {
        def now = Calendar.getInstance().getTime()

        def item1 = new Item(name: "yy面膜1", count: 3, createTime: now)
        item1.save(flush: true);
        def item2 = new Item(name: "yy面膜2", count: 3, createTime: now)
        item2.save(flush: true);
        def item3 = new Item(name: "yy面膜3", count: 3, createTime: now)
        item3.save(flush: true);

        Cart cart = new Cart();
        cart.userName = "zhang3"
        cart.itemListMap = [
                i1: item1,
                i2: item2,
                i3: item3
        ]

        cart.save(flush: true)
        render "~~~~~~#~~~~" + System.currentTimeMillis() + "    " + cart
    }

    def itemList() {
        def now = Calendar.getInstance().getTime()

        def item1 = new Item(name: "yy面膜1", count: 3, createTime: now)
        item1.save(flush: true);
        def item2 = new Item(name: "yy面膜2", count: 3, createTime: now)
        item2.save(flush: true);
        def item3 = new Item(name: "yy面膜3", count: 3, createTime: now)
        item3.save(flush: true);

        Cart cart = new Cart();
        cart.userName = "zhang3"
        cart.itemList = [item1, item2, item3]

        cart.save(flush: true)
        render "~~~~~~#~~~#~" + System.currentTimeMillis() + "    " + cart
    }
}
