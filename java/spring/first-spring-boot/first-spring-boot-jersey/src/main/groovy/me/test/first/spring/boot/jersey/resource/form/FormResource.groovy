package me.test.first.spring.boot.jersey.resource.form

import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

// application/x-www-form-urlencoded
@javax.inject.Singleton
@Component
@Path("/form")
@Api(
        value = "/form",
        description = "表单",
        tags = ["form"]
)
@Slf4j
class FormResource {

    FormResource() {
        println "================= new ${this.class.name}() : @ " + new Date()
    }

/*
curl -v \
    -X POST \
    -d age=31 \
    -d hobbies=hobby1 \
    -d hobbies=hobby2 \
    -d q=q2 \
    "http://localhost:8080/api/form?q=q1"

{"age":31,"hobbies":["hobby1","hobby2"],"q3":"q1","q4":"q1"}
 */
    /**
     * TODO 这里写概要设计。
     * @param request
     * @param req
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = FormPostReq)
    ])
    Response update(

            @Context
                    HttpServletRequest request,

            @BeanParam
                    FormPostReq req
    ) {
        String msg = "========== cur req path : " + request.getRequestURI()
        log.debug(msg)

        def data = req
        return Response.status(Response.Status.OK).entity(data).build()
    }


}

