package me.test.first.spring.boot.feign.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
/**
 *
 */

@Api(
        value = "/user",
        description = "用户",
        tags = ["user"]
)
@Path("/user")
interface UserApi {

    @Path("/{companyId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "查询用户列表",
            notes = "你猜~~")
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = UserGetResp)
    ])
    UserGetResp list(

            // HEADER 中单值
            @ApiParam(value = "年龄")
            @HeaderParam("age")
                    int age,

            // HEADER 中多值
            @ApiParam(value = "偏好")
            @HeaderParam("hobbies")
                    List<String> hobbies,


            @PathParam("companyId")
            String companyId,

            // Query 中单值
            @ApiParam(
                value = "姓名",
                allowableValues='zhang3,li4,wang5'
            )
            @DefaultValue("zhang3")
            @QueryParam("name")
                    String name,

            // Query 中多值
            @ApiParam(value = "地址")
            @QueryParam("addrs")
                   List< String> addrs

    )
}