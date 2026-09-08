package com.github.btpka3.first.spring.boot4.tpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class XxxClass {

    private String pkg;
    private String name;
    private List<XxxField> fieldList;

}
