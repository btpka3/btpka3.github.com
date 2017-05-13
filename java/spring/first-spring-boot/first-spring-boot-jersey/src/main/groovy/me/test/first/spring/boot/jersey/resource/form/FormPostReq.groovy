package me.test.first.spring.boot.jersey.resource.form

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

import javax.ws.rs.DefaultValue
import javax.ws.rs.FormParam
import javax.ws.rs.HeaderParam
import javax.ws.rs.PathParam
import javax.ws.rs.QueryParam

/**
 *
 */
@ApiModel
class FormPostReq {


    // FORM 中单值
    @ApiParam(value = "年龄")
    @ApiModelProperty(value = "年龄-model")
    @FormParam("age")
    int age

    // FORM 中多值
    @ApiParam(value = "偏好")
    @ApiModelProperty(value = "偏好-model")
    @FormParam("hobbies")
    List<String> hobbies


}
