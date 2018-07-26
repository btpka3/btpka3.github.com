package com.github.btpka3.demo.war.controller;

import com.github.btpka3.demo.lib.Aaa;
import com.github.btpka3.demo.war.service.Bbb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private Aaa aaa;

    @Autowired
    private Bbb bbb;

    @RequestMapping("/")
    @ResponseBody
    public Map<String, Object> home() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("app", "demo-war");
        map.put("date", new Date());
        map.put("aaa#add", aaa.add(1, 2));
        map.put("bbb#plus", bbb.plus(1, 2));
        return map;
    }
}
