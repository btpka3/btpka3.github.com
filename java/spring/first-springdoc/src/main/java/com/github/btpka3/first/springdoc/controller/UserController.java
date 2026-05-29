package com.github.btpka3.first.springdoc.controller;

import com.github.btpka3.first.springdoc.model.ApiError;
import com.github.btpka3.first.springdoc.model.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户 CRUD 操作")
public class UserController {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @GetMapping
    @Operation(summary = "分页查询用户列表")
    public List<User> list(
            @Parameter(description = "页码，从 0 开始", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size) {
        return new ArrayList<>(store.values()).stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 查询用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        User user = store.get(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiError.of(404, "用户不存在"));
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "创建用户")
    @ApiResponse(responseCode = "201", description = "创建成功")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        long id = idGen.getAndIncrement();
        user.setId(id);
        store.put(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "根据 ID 更新用户，用户不存在时返回 404")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User user) {
        if (!store.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiError.of(404, "用户不存在"));
        }
        user.setId(id);
        store.put(id, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    @ApiResponse(responseCode = "204", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "用户不存在",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (store.remove(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiError.of(404, "用户不存在"));
        }
        return ResponseEntity.noContent().build();
    }
}
