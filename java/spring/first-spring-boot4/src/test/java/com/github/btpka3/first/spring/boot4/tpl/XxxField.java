package com.github.btpka3.first.spring.boot4.tpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class XxxField {
    private String type;
    private String name;
    private String getterLogic;

    public String getCapitalizedName() {
        if (name == null || name.isEmpty()) return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
