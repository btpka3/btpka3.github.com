package me.test.first.spring.boot.data.mongo.controller

import me.test.first.spring.boot.data.mongo.core.MyTypeEnum
import me.test.first.spring.boot.data.mongo.domain.Addr
import me.test.first.spring.boot.data.mongo.domain.User
import me.test.first.spring.boot.data.mongo.repo.AddrRepository
import me.test.first.spring.boot.data.mongo.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 */
@Controller
@RequestMapping("/repo")
class RepoController {

    @Autowired
    UserRepository userRepo


    @Autowired
    AddrRepository addrRepo


    @RequestMapping("/list")
    @ResponseBody
    Object list() {
        // 注意，返回的是 Page<T> 类型
        return userRepo.findAll(new PageRequest(1, 2))
    }


    @RequestMapping(path = "/list1")
    @ResponseBody
    Object list1() {
        return userRepo.findByAgeIn(
                [13, 15, 16, 17, 18],
                new PageRequest(1, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "age"))
                )
        )
    }


    @RequestMapping("/add")
    @ResponseBody
    Object add() {

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

}
