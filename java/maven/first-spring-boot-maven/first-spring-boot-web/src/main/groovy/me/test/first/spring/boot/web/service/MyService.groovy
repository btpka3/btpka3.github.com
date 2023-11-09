package me.test.first.spring.boot.web.service

import org.springframework.stereotype.Service

@Service
class MyService {

    int add(int a, int b) {
        println "a+b = $a + $b = ${a + b}"
        a + b
    }
}