package me.test.first.spring.boot.test.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MyPerson {
    private String name;
}
