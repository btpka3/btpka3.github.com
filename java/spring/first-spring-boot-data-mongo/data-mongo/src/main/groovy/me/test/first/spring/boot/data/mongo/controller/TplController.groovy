package me.test.first.spring.boot.data.mongo.controller

import me.test.first.spring.boot.data.mongo.core.MyTypeEnum
import me.test.first.spring.boot.data.mongo.domain.Addr
import me.test.first.spring.boot.data.mongo.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.data.mongodb.core.query.Query.query

/**
 * 试用 MongoTemplate
 */
@Controller
@RequestMapping("/tpl")
class TplController {

    @Autowired
    MongoTemplate mongoTemplate


    @RequestMapping("/add")
    @ResponseBody
    String add() {


        Addr addr = new Addr(name: "addr-1", streetList: [
                new Addr.Street(name: "street-1")
        ])
        mongoTemplate.save(addr)  // 需要先保存关联的数据

        mongoTemplate.save(new User(name: "zhang3", age: 13, arrList: [addr]))
        mongoTemplate.save(new User(name: "li4", age: 14, type: MyTypeEnum.THREE))
        mongoTemplate.save(new User(name: "wang5", age: 15, type: MyTypeEnum.TWO))
        mongoTemplate.save(new User(name: "zhao6", age: 16, type: MyTypeEnum.ONE))
        mongoTemplate.save(new User(name: "qian7", age: 17, type: MyTypeEnum.TWO))

        return "add " + new Date()
    }

    // 通过 mongoTemplate 查询
    @RequestMapping(path = "/list", method = [RequestMethod.GET])
    @ResponseBody
    List<User> list() {

        Criteria criteria = new Criteria().andOperator(
                where("name").exists(true),
                where('age').in([13, 15, 16, 17, 18])
        )

        Query query = query(criteria)
                .skip(1)  // 分页
                .limit(2)
                .with(new Sort(Sort.Direction.DESC, "age")) // 排序

        // 应该只有 age = 15, 16 的记录
        List<User> userList = mongoTemplate.find(query, User)
        return userList
    }


    @RequestMapping(path = "/update", method = [RequestMethod.GET])
    @ResponseBody
    Object update() {
        Criteria criteria = where("name").exists(true)

        Query query = query(criteria)
                .skip(1)

        User user = mongoTemplate.findOne(query, User)
        user.memo = "" + user.memo + "a"
        mongoTemplate.save(user)
        return user
    }
}
