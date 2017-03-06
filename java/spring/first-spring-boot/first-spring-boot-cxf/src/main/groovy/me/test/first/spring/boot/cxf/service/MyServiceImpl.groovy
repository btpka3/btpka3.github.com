package me.test.first.spring.boot.cxf.service

import io.swagger.annotations.Api
import org.springframework.stereotype.Service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Api("/add")
class MyServiceImpl {

    @GET
    @Path("/{a}")
    Map<String, Integer> add(
            @PathParam("a") int a,
            int b) {
        println "a+b = $a + $b = ${a + b}"
        return [
                a: a, b: b, sum: a + b
        ]
    }
}