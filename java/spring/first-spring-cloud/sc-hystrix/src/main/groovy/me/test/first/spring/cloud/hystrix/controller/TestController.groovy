package me.test.first.spring.cloud.hystrix.controller

import me.test.first.spring.cloud.hystrix.service.DemoService
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    private DemoService demoService;


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

    @RequestMapping(path = "/test01",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object test01() {

        String str = demoService.doIt([
                a: "aaa",
                b: "bbb"
        ])
        return [
                data: str
        ]
    }

    @RequestMapping(path = "/test02",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object test02() {

        String str = demoService.doIt([
                a  : "aaa",
                b  : "bbb",
                err: 111
        ])
        return [
                data: str
        ]
    }
}
