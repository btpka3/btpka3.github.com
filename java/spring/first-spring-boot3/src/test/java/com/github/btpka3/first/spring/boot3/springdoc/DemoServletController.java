package com.github.btpka3.first.spring.boot3.springdoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/26
 */
@Tag(name = "用户管理", description = "用户增删改查")
@RestController
@RequestMapping("/api/users")
public class DemoServletController {
    @Operation(summary = "根据 ID 查询用户")
    @ApiResponse(responseCode = "200", description = "成功")
    @GetMapping("/{id}")
    public Object get(@Parameter(description = "用户ID") @PathVariable Long id) {
        return null;
    }
}
