package me.test.first.spring.cloud.config.server.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 *
 */
@RestController()
@RequestMapping("/test")
class TestController {

    @RequestMapping(path = "/hi",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object hi() {

        return [
                a   : "aaa",
                date: new Date()
        ]
    }
}
