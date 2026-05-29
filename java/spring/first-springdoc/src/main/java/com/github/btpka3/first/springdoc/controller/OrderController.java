package com.github.btpka3.first.springdoc.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单管理", description = "订单查询与创建")
public class OrderController {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1001);

    @GetMapping
    @Operation(summary = "查询订单列表", description = "支持按状态过滤")
    public List<Order> list(
            @Parameter(description = "按状态过滤", schema = @Schema(implementation = OrderStatus.class))
            @RequestParam(required = false) OrderStatus status) {
        List<Order> all = new ArrayList<>(store.values());
        if (status != null) {
            return all.stream().filter(o -> o.getStatus() == status).toList();
        }
        return all;
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 查询订单")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "订单不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Order order = store.get(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiError.of(404, "订单不存在"));
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Operation(summary = "创建订单")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "创建成功"),
            @ApiResponse(responseCode = "400", description = "参数校验失败",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
        long id = idGen.getAndIncrement();
        order.setId(id);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        store.put(id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
