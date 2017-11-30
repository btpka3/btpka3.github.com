package me.test.first.spring.cloud.eureka.sc.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
// serviceId
@FeignClient(value = "qh", url = "https://kingsilk.net")
interface QhClient {

    @RequestMapping(path = "/qh/mall/api/common/sysConf",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, Object> sysConf(
            @RequestParam(name = "key", defaultValue = "appendStaticResource")
                    String key
    )
}
