package me.test.first.spring.cloud.eureka.sc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 *
 */
@RestController()
@RequestMapping("/sc")
class ScController {


    @Autowired
    private RestTemplate restTemplate

    @RequestMapping(path = "/sum",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object sum() {


        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://192.168.0.41:9090/sp/sum")
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


}
