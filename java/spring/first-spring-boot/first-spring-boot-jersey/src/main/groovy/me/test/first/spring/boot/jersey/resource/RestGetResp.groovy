package me.test.first.spring.boot.jersey.resource

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel
class RestGetResp {

    @ApiModelProperty(
            value = "名称",
            allowableValues = "ONE,TWO,THREE"
    )
    String a
    String b
    String c
}

