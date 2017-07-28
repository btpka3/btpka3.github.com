package me.test.first.spring.boot.jersey.resource.err

import io.swagger.annotations.Api
import me.test.first.spring.boot.jersey.resource.MyRscException
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.file.AccessDeniedException

@javax.inject.Singleton
@Component
@Path("/err")
@Api(
        value = "/err"
)
class ErrResource {

/*
从 Jersey 中抛出，由 MyExceptionMapper 处理并返回 Response。

curl -v -X GET "http://localhost:8080/api/err/e1"

["hahaha @ MyExceptionMapper : Fri Jul 28 15:00:36 CST 2017"]
 */

    @Path("/e1")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response e1(

    ) {
        throw new IllegalArgumentException("hahaha");
    }

/*
从 Jersey 中抛出，因为是 WebApplicationException 所以直接返回 Response。

curl -v -X GET "http://localhost:8080/api/err/e2"

["666 @ MyRscException : Fri Jul 28 15:00:24 CST 2017"]
 */

    @Path("/e2")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response e2(

    ) {
        throw new MyRscException("666");
    }

/*
从 Jersey 中抛出，但 Jersey 没有任何处理，被 spring 框架捕获并处理。

curl -v -X GET "http://localhost:8080/api/err/e3"

{"timestamp":1501225400101,"status":500,"error":"Internal Server Error","exception":"org.glassfish.jersey.server.ContainerException","message":"java.nio.file.AccessDeniedException: 888","path":"/api/err/e3"}
 */

    @Path("/e3")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response e3(

    ) {
        throw new AccessDeniedException("888");
    }

}

