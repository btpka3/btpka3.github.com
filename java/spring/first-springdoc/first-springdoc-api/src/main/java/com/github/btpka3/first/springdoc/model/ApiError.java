package com.github.btpka3.first.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一错误响应")
public class ApiError {

    @Schema(description = "HTTP 状态码", example = "404")
    private int status;

    @Schema(description = "错误信息", example = "用户不存在")
    private String message;

    @Schema(description = "错误发生时间")
    private LocalDateTime timestamp;

    public static ApiError of(int status, String message) {
        return new ApiError(status, message, LocalDateTime.now());
    }
}
