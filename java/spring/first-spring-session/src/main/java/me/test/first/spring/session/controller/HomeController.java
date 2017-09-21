package me.test.first.spring.session.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 */
@RestController
public class HomeController {


    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseBody
    public Object home() {

        return "first-spring-session : " + new Date();
    }

}
