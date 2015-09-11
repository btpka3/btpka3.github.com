package me.test

class TestController {

    def index() {
        render """
<div>aaa = ${session.aaa}</div>
<div>bbb = ${session.bbb}</div>
<div>port = ${System.getProperty("grails.server.port.http")}</div>
"""
    }

    def update() {
        // 简单数据类型
        session.aaa = System.currentTimeMillis()

        if (!session.bbb) {
            session.bbb = 0..2
        } else {
            session.bbb.push(session.bbb.size())
        }
        Thread.sleep(10 * 1000)

        render "OK :  ${System.getProperty('grails.server.port.http')}"
    }
}
