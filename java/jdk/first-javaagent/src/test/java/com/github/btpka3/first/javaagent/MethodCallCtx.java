package com.github.btpka3.first.javaagent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dangqian.zll
 * @date 2024/10/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MethodCallCtx {
    private Object target;
    private long at;
    private long startNanoTime;
    private long endNanoTime;
    private Throwable err;
}
