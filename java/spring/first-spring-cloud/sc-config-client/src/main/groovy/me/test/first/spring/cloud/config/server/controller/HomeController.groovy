package me.test.first.spring.cloud.config.server.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by zll on 09/05/2017.
 */
@RestController()
@RequestMapping("/")
class HomeController {

    @RequestMapping(path = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object index() {

        return [
                msg: "Hello worold @ " + new Date()
        ]
    }
}
