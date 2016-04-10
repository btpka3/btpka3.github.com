package me.test.emr

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EmrDemo {
    static Logger log = LoggerFactory.getLogger(EmrDemo)

    static void main(String[] args) {
        log.info "hello world : " + new Date()
    }
}
