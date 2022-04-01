package me.test.first.spring.boot.test.context;

import java.lang.annotation.*;

/**
 * @author dangqian.zll
 * @date 2022/4/2
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLazy {
}
