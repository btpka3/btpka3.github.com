package com.github.btpka3.first.spring.data.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class HomeController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "home " + new Date();
    }
}
