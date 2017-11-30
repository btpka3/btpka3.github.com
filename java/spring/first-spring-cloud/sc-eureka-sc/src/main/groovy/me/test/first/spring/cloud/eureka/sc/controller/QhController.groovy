package me.test.first.spring.cloud.eureka.sc.controller

import groovy.util.logging.Slf4j
import me.test.first.spring.cloud.eureka.sc.client.QhClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
@RestController()
@RequestMapping("/qh")
@Slf4j
class QhController {

    @Autowired
    QhClient qhClient

    // 使用 feign 声明式的 client
    @RequestMapping(path = "/sysConf",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sysConf() {
        return qhClient.sysConf("appendStaticResource")
    }

}
