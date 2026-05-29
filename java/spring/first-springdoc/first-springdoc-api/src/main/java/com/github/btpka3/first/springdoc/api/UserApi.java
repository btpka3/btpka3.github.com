package com.github.btpka3.first.springdoc.api;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理", description = "用户 CRUD 操作")
@RequestMapping("/api/users")
public interface UserApi {

    @GetMapping
    @Operation(summary = "分页查询用户列表")
    List<User> list(
            @Parameter(description = "页码，从 0 开始", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 查询用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    User getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "创建用户")
    @ApiResponse(responseCode = "201", description = "创建成功")
    User create(@Valid @RequestBody User user);

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "根据 ID 更新用户，用户不存在时返回 404")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    User update(@PathVariable Long id, @Valid @RequestBody User user);

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    @ApiResponse(responseCode = "204", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "用户不存在",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    void delete(@PathVariable Long id);
}
