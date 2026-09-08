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
public class Page {

    private String description;
    private String title;


}
