package me.test.first.spring.boot.test.json.c;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author dangqian.zll
 * @date 2024/2/26
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
//@JsonDeserialize
public class A11Mixin {
}
