package me.test.first.spring.cloud.eureka.sc.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
// serviceId
@FeignClient("sc-eureka-sp")
interface SpClient {

    @RequestMapping(path = "/sp/sum",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, Object> sum(
            @RequestParam(name = "a")
                    int a,
            @RequestParam(name = "b")
                    int b)

}
