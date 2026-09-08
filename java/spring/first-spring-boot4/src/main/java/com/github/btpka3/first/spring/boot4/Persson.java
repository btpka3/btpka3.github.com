package com.github.btpka3.first.spring.boot4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/30
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Persson {
    private String name;
    private Integer age;
}
