package me.test.first.spring.boot.swagger.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(
        description = "商品信息"
)
class Item {

    @ApiModelProperty(
            value = "Item-商品名称"
    )
    String title

    @ApiModelProperty(
            value = "Item-商品价格"
    )
    Integer price

}
