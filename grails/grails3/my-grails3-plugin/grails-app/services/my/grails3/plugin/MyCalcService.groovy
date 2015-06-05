package my.grails3.plugin

import grails.transaction.Transactional

@Transactional
class MyCalcService {

    def add(def a, def b) {
        a + b
    }
}
