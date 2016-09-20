package my.grails3

import grails.converters.JSON

class HelloController {

    def mongo

    def myCalcService

    // gsp 测试
    def index() {
        println("hello" + HelloController.classLoader.loadClass("org.h2.Driver"))
        render(view: "index", model: [a: "aaa", b: "bbb", c: "ccc"])
        //render "aaa " + System.currentTimeMillis()
    }

    // 服务调用测试
    def add() {
        render "myCalcService : " + myCalcService.add(1, 2) + ", " + System.currentTimeMillis()
    }

    def insert() {

        10.times {
            def u = new User(username: "zhang_" + it, age: 10 + it)
            u.save(flush: true)
        }
        render "insert OK " + System.currentTimeMillis()
    }


    def list() {

        def list = User.createCriteria().list {
            ge("age", 15)
        }
        render(list as JSON)
    }

    def clear() {
        User.list()*.delete()
        render("all deleted. ")
    }

    def testMongo() {
        def c = mongo.getDB("zll").user.find()
        def rec
        if (c.hasNext()) {
            rec = c.next()
        }
        render "aaaaaaaaaa " + System.currentTimeMillis() + " === " + rec
    }


}
