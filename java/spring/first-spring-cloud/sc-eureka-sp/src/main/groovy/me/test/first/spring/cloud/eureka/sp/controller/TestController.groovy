package me.test.first.spring.cloud.eureka.sp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
@RestController()
@RequestMapping("/test")
class TestController {

    @Autowired
    private DiscoveryClient discoveryClient

    @RequestMapping(path = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object hi() {

        return [
                url : "/test/",
                date: new Date()
        ]
    }

    @RequestMapping(path = "/test01",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object test01() {


        List<String> serviceIds = discoveryClient.getServices();

        List list = []
        for (String s : serviceIds) {

            List list1 = []
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(s);
            for (ServiceInstance si : serviceInstances) {
                list1 << [
                        serviceId: s,
                        host     : si.getHost(),
                        port     : si.port,
                        uri      : si.uri
                ]
            }

            list << [
                    serviceId: s,
                    instance : list1
            ]
        }

        return list
    }


    @RequestMapping(path = "/test02",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object test02() {

        List<ServiceInstance> instances = discoveryClient.getInstances("sc-eureka-sp");

        return instances
    }

}
