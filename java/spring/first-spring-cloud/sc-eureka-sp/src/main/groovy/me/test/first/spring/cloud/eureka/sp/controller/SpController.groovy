package me.test.first.spring.cloud.eureka.sp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

    @Value('${server.port}')
    int port

    @RequestMapping(path = "/sum",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum(
            @RequestParam(name = "a", defaultValue = "1")
                    int a,

            @RequestParam(name = "b", defaultValue = "2")
                    int b
    ) {

        return [
                sum : a + b,
                date: new Date(),
                node: port
        ]
    }
}
