package me.test.first.spring.boot.test.mockito.child;

import me.test.first.spring.boot.test.mockito.parent.Parent;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
public class Child extends Parent {
    public String hello(String name) {
        return "hello " + findRealName(name);
    }
}
