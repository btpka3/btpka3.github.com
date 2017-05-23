package me.test.first.spring.cloud.eureka.sp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
@RestController()
@RequestMapping("/sp")
class SpController {

    @Autowired
    private DiscoveryClient discoveryClient

    @RequestMapping(path = "/sum",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum(
            @RequestParam
                    int a,

            @RequestParam
                    int b
    ) {

        return [
                sum : a + b,
                date: new Date()
        ]
    }
}
