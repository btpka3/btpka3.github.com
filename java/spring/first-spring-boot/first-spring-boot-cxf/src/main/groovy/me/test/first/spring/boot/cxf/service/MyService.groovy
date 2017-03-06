package me.test.first.spring.boot.cxf.service

import org.springframework.stereotype.Service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("/add")
@Service
class MyService {

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