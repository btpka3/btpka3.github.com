package com.github.btpka3.my.lib.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Date birthday;
    private Integer height;


}
