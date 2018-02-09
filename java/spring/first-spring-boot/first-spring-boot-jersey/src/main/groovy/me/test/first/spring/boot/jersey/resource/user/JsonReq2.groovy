package me.test.first.spring.boot.jersey.resource.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 *
 */
@ApiModel
class JsonReq2 extends JsonReq1 {


    @ApiModelProperty("地址")
    String addr

}
