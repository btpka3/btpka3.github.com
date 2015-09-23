package me.test

import org.apache.commons.lang3.exception.ExceptionUtils

class TestController {
    def springSessionRepositoryFilter

    def index() {

        def curStack = ExceptionUtils.getStackTrace(new Throwable())

        render """
<div>aaa = ${session.aaa}</div>
<div>bbb = ${session.bbb}</div>
<div>port = ${System.getProperty("grails.server.port.http")}</div>
<div>springSessionRepositoryFilter = ${springSessionRepositoryFilter}</div>
<div>cur stack = <pre>${curStack}</pre></div>
"""
    }

    def update() {
        // 简单数据类型
        session.aaa = System.currentTimeMillis()

        if (!session.bbb) {
            session.bbb = (0..2).toList()
        } else {
            session.bbb.push(session.bbb.size())
            // 注意：以下这一步是必须
            session.bbb = session.bbb

        }
        Thread.sleep(10 * 1000)

        render "OK update :  ${System.getProperty('grails.server.port.http')}"
    }

    def clear() {
        // 简单数据类型
        session.aaa = 0
        session.bbb = null

        render "OK clear :  ${System.getProperty('grails.server.port.http')}"
    }
}
