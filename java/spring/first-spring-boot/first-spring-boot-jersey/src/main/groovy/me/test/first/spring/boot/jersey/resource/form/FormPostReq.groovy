package me.test.first.spring.boot.jersey.resource.form

import io.swagger.annotations.*

import javax.ws.rs.FormParam
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


    // ------------------ 测试url和表单中都有该参数时，能否用两个注解 @FormParam，@QueryParam
    // 既用 QueryParam, 又用 FormParam
    @ApiParam(value = "q1")
    @ApiModelProperty(value = "q1-model")
    @FormParam("q")
    @QueryParam("q")
    String q1           // FIXME null @FormParam @QueryParam 一起用将 无效

    // 既用 QueryParam, 又用 FormParam, 且多值
    @ApiParam(value = "q2")
    @ApiModelProperty(value = "q2-model")
    @FormParam("q")
    @QueryParam("q")
    List<String> q2     // FIXME null @FormParam @QueryParam 一起用将 无效

    @ApiParam(value = "q3")
    @ApiModelProperty(value = "q3-model")
    @FormParam("q")
    String q3

    @ApiParam(value = "q4")
    @ApiModelProperty(value = "q4-model")
    @QueryParam("q")
    String q4
}
