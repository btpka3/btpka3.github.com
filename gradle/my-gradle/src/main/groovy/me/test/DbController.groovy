package me.test

import me.test.domain.User
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/db")
@RestController
class DbController {

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    String insert() {

        10.times {
            def u = new User(name: "zll" + it, age: it)
            u.save()
        }

//        new User(name: "zhang1", age: 1).save(flush: true)
//        new User(name: "zhang1", age: 2).save(flush: true)
        return "OK " + System.currentTimeMillis()
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    String listUser() {
        def list = User.createCriteria().list {
            ge("age", 5)
        }
        list << [time: System.currentTimeMillis()] // 为了显示刷新用
        return list
    }
}
