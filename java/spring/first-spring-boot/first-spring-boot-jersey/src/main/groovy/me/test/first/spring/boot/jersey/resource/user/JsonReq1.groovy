package me.test.first.spring.boot.jersey.resource.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 *
 */
@ApiModel(
       // subTypes = [ JsonReq2.class ]
)
class JsonReq1 {


    @ApiModelProperty("姓名")
    String name

    @ApiModelProperty("年龄")
    Integer age


}
