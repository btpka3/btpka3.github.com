package me.test.first.spring.cloud.eureka.sc.controller

import groovy.util.logging.Slf4j
import me.test.first.spring.cloud.eureka.sc.client.SpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import javax.annotation.Resource

/**
 *
 */
@RestController()
@RequestMapping("/sc")
@Slf4j
class ScController {

    @Autowired
    private DiscoveryClient discoveryClient

    // 《Spring RestTemplate as a Load Balancer Client》
    // http://cloud.spring.io/spring-cloud-static/Dalston.SR1/#_spring_resttemplate_as_a_load_balancer_client
    @Autowired
    private RestTemplate restTemplate

    @Resource(name = "noLbRestTemplate")
    RestTemplate noLbRestTemplate

    @Autowired
    LoadBalancerClient lbClient

    @Autowired
    SpClient spClient


    @RequestMapping(path = "/sum",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum() {


        List<ServiceInstance> instances = discoveryClient.getInstances("sc-eureka-sp");
        if (!instances) {
            return [
                    status: "error",
                    msg   : "no one sc-eureka-sp up."
            ]
        }
        // "http://${instances[0].host}:${instances[0].port}/sp/sum"


        URI uri = UriComponentsBuilder
        // 注意：这里使用的是 serviceId
                .fromHttpUrl("http://sc-eureka-sp/sp/sum")
                .queryParam("a", "3")
                .queryParam("b", "4")
                .build()
                .encode("UTF-8")
                .toUri()

        ResponseEntity<Map> respEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Map.class);
        Map map = respEntity.getBody()
        map.sc = true;

        return map
    }

    // 始终都和 /sum 一样，已经 load balanced
    @RequestMapping(path = "/sum2",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum2() {


        List<ServiceInstance> instances = discoveryClient.getInstances("sc-eureka-sp");
        if (!instances) {
            return [
                    status: "error",
                    msg   : "no one sc-eureka-sp up."
            ]
        }


        URI uri = UriComponentsBuilder
        // 注意：这里使用的是 serviceId
                .fromHttpUrl("http://sc-eureka-sp/sp/sum")
                .queryParam("a", "2")
                .queryParam("b", "5")
                .build()
                .encode("UTF-8")
                .toUri()


        ResponseEntity<Map> respEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Map.class);
        Map map = respEntity.getBody()
        map.sc = true;
        map.checkLb = restTemplate == noLbRestTemplate;

        return map
    }

    // 使用底层API，不 load balance
    @RequestMapping(path = "/sum3",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum3() {


        List<ServiceInstance> instances = discoveryClient.getInstances("sc-eureka-sp");
        if (!instances) {
            return [
                    status: "error",
                    msg   : "no one sc-eureka-sp up."
            ]
        }

        ServiceInstance instance = instances[0]
        if (!instance) {
            return [
                    status: "error11",
                    msg   : "no one sc-eureka-sp up."
            ]
        }
        URI serverUri = URI.create(String.format("http://%s:%s/sp/sum",
                instance.getHost(),
                instance.getPort()))



        URI uri = UriComponentsBuilder
        // 注意：这里使用的是 serviceId
                .fromUri(serverUri)
                .queryParam("a", "2")
                .queryParam("b", "11")
                .build()
                .encode("UTF-8")
                .toUri()

        log.debug("======= sum3 : uri = $uri")

        URLConnection conn = uri.toURL().openConnection()
        InputStream respStream = conn.getInputStream();

        return respStream.text
    }

    // 使用 feign 声明式的 client
    @RequestMapping(path = "/sum4",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum4() {
        return spClient.sum(11, 100)
    }

}
