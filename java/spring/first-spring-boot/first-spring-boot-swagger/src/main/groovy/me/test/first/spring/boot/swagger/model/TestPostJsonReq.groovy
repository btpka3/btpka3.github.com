package me.test.first.spring.boot.swagger.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class TestPostJsonReq {


    @ApiModelProperty(
            value = "TestPostJsonReq-名称"
    )
    String name


    @ApiModelProperty(
            value = "TestPostJsonReq-数量"
    )
    Integer count

    @ApiModelProperty(
            value = "TestPostJsonReq-地址列表"
    )
    List<String> addresses

    @ApiModelProperty(
            value = "TestPostJsonReq-开始日期"
    )
    Date startDate

    @ApiModelProperty(
            value = "TestPostJsonReq-商品列表"
    )
    List<Item> itemList

    @ApiModelProperty(value = "数据类型")
    MyEnum type
}
