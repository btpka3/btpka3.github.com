package me.test.first.spring.boot.jersey.resource.user

import io.swagger.annotations.ApiParam

import javax.ws.rs.DefaultValue
import javax.ws.rs.FormParam
import javax.ws.rs.HeaderParam
import javax.ws.rs.PathParam
import javax.ws.rs.QueryParam

/**
 *
 */
class UserPostReq {


    // HEADER 中单值
    @ApiParam(value = "年龄")
    @HeaderParam("age")
    int age

    // HEADER 中多值
    @ApiParam(value = "偏好")
    @HeaderParam("hobbies")
    List<String> hobbies


    @PathParam("companyId")
    String companyId

    // Query 中单值
    @ApiParam(
            value = "姓名",
            allowableValues='zhang3,li4,wang5'
    )
    @DefaultValue("zhang3")
    @QueryParam("name")
    String name

    // Query 中多值
    @ApiParam(value = "地址")
    @QueryParam("addrs")
    List< String> addrs

}
