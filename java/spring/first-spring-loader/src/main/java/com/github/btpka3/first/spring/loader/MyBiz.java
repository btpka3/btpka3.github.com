package com.github.btpka3.first.spring.loader;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/1/25
 */
public class MyBiz {
    public static void show() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("name", "zhang3");
        map.put("age", 38);
        map.put("classLoader", MyBiz.class.getClassLoader().toString());
        System.out.println(JSON.toJSONString(map));
    }
}
