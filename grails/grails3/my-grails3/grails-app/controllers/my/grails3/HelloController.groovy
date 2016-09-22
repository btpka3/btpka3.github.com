package my.grails3

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.access.expression.SecurityExpressionHandler

class HelloController {

    def mongo

    def myCalcService

    def userDetailsService

    // gsp 测试
    def index() {
        render(view: "index", model: [
                a: "aaa" + grailsApplication.mainContext.getBean(MyUserDetails),
                b: "bbb" + grailsApplication.mainContext.getBean(SecurityExpressionHandler)?.defaultRolePrefix +"---",
                c: "ccc" + userDetailsService?.class
        ])
    }

    // 服务调用测试
    def add() {
        render "myCalcService : " + myCalcService.add(1, 2) + ", " + System.currentTimeMillis()
    }

    // 测试插入mongodb
    def insert() {

        10.times {
            def u = new U(username: "zhang_" + it, age: 10 + it)
            u.save(flush: true)
        }
        render "insert OK " + System.currentTimeMillis()
    }

    // 测试查询mongodb
    def list() {

        def list = U.createCriteria().list {
            ge("age", 15)
        }
        render(list as JSON)
    }

    // 测试删除mongodb
    def clear() {
        U.list()*.delete()
        render("all deleted. ")
    }

    // 测试使用mongodb底层API
    def testMongo() {
        def c = mongo.getDB("zll").user.find()
        def rec
        if (c.hasNext()) {
            rec = c.next()
        }
        render "aaaaaaaaaa " + System.currentTimeMillis() + " === " + rec
    }

    // ----------------------------------------------------- spring security
    def setupUsers() {
        MyUser.list()*.delete()
        new MyUser(username: 'admin', password: 'admin', authorities: ['ADMIN']).save()
        new MyUser(username: 'user', password: 'user', authorities: ['USER']).save()
        render "user setup Ok."
    }

    // 测试登录后访问
    @Secured(["isAuthenticated()"])
    def sec() {
        render "can only view after logined"
    }

    // 测试登录后且有特定权限才能访问
    @Secured(["isAuthenticated() && hasAnyRole('ADMIN')"])
//    @Authorities("ADMIN") // 适用于只判断一个权限
    def admin() {
        render "admin only page"
    }


}
