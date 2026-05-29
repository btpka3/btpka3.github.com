package com.github.btpka3.first.springdoc.api;

import com.github.btpka3.first.springdoc.model.ApiError;
import com.github.btpka3.first.springdoc.model.Order;
import com.github.btpka3.first.springdoc.model.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "订单管理", description = "订单查询与创建")
@RequestMapping("/api/orders")
public interface OrderApi {

    @GetMapping
    @Operation(summary = "查询订单列表", description = "支持按状态过滤")
    List<Order> list(
            @Parameter(description = "按状态过滤", schema = @Schema(implementation = OrderStatus.class))
            @RequestParam(required = false) OrderStatus status);

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 查询订单")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "订单不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    Order getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "创建订单")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "创建成功"),
            @ApiResponse(responseCode = "400", description = "参数校验失败",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    Order create(@Valid @RequestBody Order order);
}
