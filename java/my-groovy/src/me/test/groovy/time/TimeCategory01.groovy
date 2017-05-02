package me.test.groovy.time

import groovy.time.TimeCategory

/**
 *
 */
class TimeCategory01 {


    static void main(String[] args) {
        use(TimeCategory) {
            println new Date()
            println 1.minute.from.now
            println 10.hours.ago

            def someDate = new Date()
            println someDate - 3.months

            println "============="
            println 1.minute + 3.seconds
        }
    }
}
