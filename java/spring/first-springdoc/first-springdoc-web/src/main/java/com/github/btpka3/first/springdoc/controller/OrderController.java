package com.github.btpka3.first.springdoc.controller;

import com.github.btpka3.first.springdoc.api.OrderApi;
import com.github.btpka3.first.springdoc.model.Order;
import com.github.btpka3.first.springdoc.model.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演示：接口返回 POJO 时，通过注入 HttpServletRequest/HttpServletResponse 条件设置 HTTP 状态码和响应头。
 *
 * 优点：
 *  - API 接口层完全不依赖 Spring 框架类型（ResponseEntity），只有 POJO + 注解
 *  - 接口作为契约更纯净，利于跨团队（前端/后端 AI Agent）并行开发
 *  - 与 OpenFeign 客户端兼容性更好（Feign 不需要处理 ResponseEntity 的反序列化）
 *
 * 缺点：
 *  - 状态码和响应头是方法的"副作用"，仅看方法签名无法得知会返回 201 还是 200
 *  - 单元测试需要 mock HttpServletResponse 来验证状态码，不如断言 ResponseEntity.getStatusCode() 直观
 *  - 错误场景（404）如果返回 null，响应体为空；如需返回错误体（如 ApiError JSON），
 *    需配合 @ExceptionHandler 或直接用 response.getWriter() 手写——不如 ResponseEntity 自然
 *  - 依赖 Servlet API（HttpServletRequest/HttpServletResponse），无法直接迁移到 WebFlux
 */
@RestController
public class OrderController implements OrderApi {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1001);

    // Spring 注入的是请求作用域代理（request-scoped proxy），每次请求拿到的是当前线程的实例，线程安全
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Override
    public List<Order> list(OrderStatus status) {
        // 示例：从 request 中读取自定义头
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId != null) {
            response.setHeader("X-Trace-Id", traceId);
        }

        List<Order> all = new ArrayList<>(store.values());
        if (status != null) {
            return all.stream().filter(o -> o.getStatus() == status).toList();
        }
        return all;
    }

    @Override
    public Order getById(Long id) {
        Order order = store.get(id);
        if (order == null) {
            // 条件设置状态码：资源不存在时返回 404
            // 注意：此处返回 null，响应体为空 JSON（null）；如需返回 ApiError 结构体，
            // 建议改用 throw new ResponseStatusException 或 @ExceptionHandler 统一处理
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return order;
    }

    @Override
    public Order create(Order order) {
        long id = idGen.getAndIncrement();
        order.setId(id);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        store.put(id, order);

        // 条件设置状态码：创建成功返回 201
        response.setStatus(HttpServletResponse.SC_CREATED);
        // 条件设置响应头：返回新资源的 Location
        response.setHeader("Location", request.getRequestURI() + "/" + id);

        return order;
    }
}
