package com.github.btpka3.first.spring.boot.data.elasticsearch.controller

import com.github.btpka3.first.spring.boot.data.elasticsearch.domain.*
import com.github.btpka3.first.spring.boot.data.elasticsearch.repo.AddrRepo
import com.github.btpka3.first.spring.boot.data.elasticsearch.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/es")
class MyEsController {

    @Autowired
    UserRepo userRepo

    @Autowired
    AddrRepo addrRepo


    @RequestMapping("/add")
    @ResponseBody
    String add() {

        Addr addr = new Addr(name: "addr-1", streetList: [
                new Addr.Street(name: "street-1"),
                new Addr.Street(name: "street-2"),
                new Addr.Street(name: "street-3")
        ])

        addrRepo.save(addr)

        userRepo.save(new User(name: "zhang3", age: 13,
                arrList: [addr],
                addr: addr,
                tags: ["z1", "z2", "z3"]))

        addr = new Addr(name: "addr-2", streetList: [
                new Addr.Street(name: "street-21"),
                new Addr.Street(name: "street-22"),
                new Addr.Street(name: "street-23")
        ])
        addrRepo.save(addr)

        userRepo.save(new User(name: "li4", age: 14,
                arrList: [addr],
                addr: addr,
                type: MyTypeEnum.THREE))


        userRepo.save(new User(name: "wang5", age: 15, type: MyTypeEnum.TWO))
        userRepo.save(new User(name: "zhao6", age: 16, type: MyTypeEnum.ONE))
        userRepo.save(new User(name: "qian7", age: 17, type: MyTypeEnum.TWO))

        return "add : " + new Date();

    }


    @RequestMapping("/list")
    @ResponseBody
    Object list() {

        return userRepo.findAll().toList()

    }
}
