package me.test.first.spring.boot.webflux.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 当千
 * @date 2018-12-06
 */
@RestController
public class HomeController {

    @RequestMapping(path = "/")
    public Object handle() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("time", new Date());
        map.put("msg", "hi~");
        return map;
    }
}
