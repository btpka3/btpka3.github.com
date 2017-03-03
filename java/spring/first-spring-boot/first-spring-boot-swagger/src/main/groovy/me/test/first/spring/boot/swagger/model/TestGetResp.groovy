package me.test.first.spring.boot.swagger.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(
        description = "/tesGet的响应"
)
class TestGetResp {


    @ApiModelProperty(
            value = "TestGetResp-组织ID"
    )
    String oid


    @ApiModelProperty(
            value = "TestGetResp-用户ID"
    )
    String userId


    @ApiModelProperty(
            value = "TestGetResp-当前页面"
    )
    Integer curPage

    @ApiModelProperty(
            value = "TestGetResp-地址列表"
    )
    List<String> addresses

    @ApiModelProperty(
            value = "TestGetResp-开始日期"
    )
    Date startDate

    @ApiModelProperty(
            value = "TestGetResp-商品列表"
    )
    List<Item> itemList
}
