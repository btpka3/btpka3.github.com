package me.test.first.spring.cloud.sleuth.controller;

import me.test.first.spring.cloud.sleuth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.sleuth.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.concurrent.*;

@Controller
public class MyTestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Tracer tracer;

    @Autowired
    MyService myService;


    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "home " + new Date();
    }


    /**
     *
     */
    @RequestMapping("/a")
    @ResponseBody
    public String a(
            @RequestParam("p")
                    String p
    ) {
        String result = restTemplate.getForObject("http://localhost:8080/b?p=" + p, String.class);
        return "a[" + p + "]:" + result;
    }

    /**
     *
     */
    @RequestMapping("/b")
    @ResponseBody
    public String b(
            @RequestParam("p")
                    String p
    ) {

        String result = restTemplate.getForObject("http://localhost:8080/c?p=" + p, String.class);
        return "b[" + p + "]:" + result;
    }

    /**
     *
     */
    @RequestMapping("/c")
    @ResponseBody
    public String c(
            @RequestParam("p")
                    String p
    ) throws Exception {
        DefaultSpanNamer spanNamer = new DefaultSpanNamer();
        Callable<String> traceCallable = new TraceCallable<>(tracer, spanNamer, myService, "myService");
        String result = traceCallable.call();
        return "c[" + p + "]:" + result;
    }
}
