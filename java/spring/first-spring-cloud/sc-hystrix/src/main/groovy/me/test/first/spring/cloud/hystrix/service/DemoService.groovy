package me.test.first.spring.cloud.hystrix.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component
import org.springframework.util.Assert

/**
 *
 */
@Component
@CompileStatic
class DemoService {

    // DemoService.& doItFallback.getMethod()
    @HystrixCommand(fallbackMethod ="doItFallback")
    Object doIt(Map<String, Object> params) {
        Assert.isTrue(!params.err, "出错啦")
        return "DemoService#doIt : " + params
    }

    Object doItFallback(Map<String, Object> params) {
        return "DemoService#doItFallback : " + params
    }
}
