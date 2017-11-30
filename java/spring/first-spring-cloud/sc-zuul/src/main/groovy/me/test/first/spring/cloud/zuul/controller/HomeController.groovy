package me.test.first.spring.cloud.zuul.controller

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
                msg: "Hello worold @ sc-zuul : " + new Date()
        ]
    }
}
