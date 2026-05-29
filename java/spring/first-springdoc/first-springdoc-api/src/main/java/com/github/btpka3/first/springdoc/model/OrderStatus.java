package com.github.btpka3.first.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "订单状态")
public enum OrderStatus {

    @Schema(description = "待支付")
    PENDING,

    @Schema(description = "已支付")
    PAID,

    @Schema(description = "已发货")
    SHIPPED,

    @Schema(description = "已完成")
    COMPLETED,

    @Schema(description = "已取消")
    CANCELLED
}
