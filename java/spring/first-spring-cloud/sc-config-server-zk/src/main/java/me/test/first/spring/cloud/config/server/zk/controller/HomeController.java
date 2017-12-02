package me.test.first.spring.cloud.config.server.zk.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.endpoint.*;
import org.springframework.core.env.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/")
public class HomeController {


    @Autowired
    Environment env;

    @RequestMapping(path = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object index() {

        Map<String, Object> map = new HashMap<>();
        map.put("app", "sc-config-server-zk");
        map.put("msg", "/");
        map.put("date", new Date());
        return map;
    }


    @RequestMapping(path = "/hi",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object hi() {

        Map<String, Object> map = new LinkedHashMap<>();

        String k = "server.port";
        String v = env.getProperty(k);
        map.put(k, v);

        k = "a";
        v = env.getProperty(k);
        map.put(k, v);

        k = "b";
        v = env.getProperty(k);
        map.put(k, v);

        k = "x.y.z";
        v = env.getProperty(k);
        map.put(k, v);


        k = "date";
        v = new Date().toString();
        map.put(k, v);

        return map;
    }
}
