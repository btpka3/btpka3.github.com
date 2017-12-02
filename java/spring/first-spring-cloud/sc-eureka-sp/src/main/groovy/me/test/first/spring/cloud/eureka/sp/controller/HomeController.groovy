package me.test.first.spring.cloud.eureka.sp.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
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
                app: "sc-eureka-sp",
                msg: "Hello worold @ " + new Date()
        ]
    }
}
