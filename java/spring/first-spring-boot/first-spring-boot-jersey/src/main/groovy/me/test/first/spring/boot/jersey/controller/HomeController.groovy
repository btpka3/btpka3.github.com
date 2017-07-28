package me.test.first.spring.boot.jersey.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

import javax.servlet.ServletRegistration
import javax.servlet.http.HttpServletRequest

@Controller
class HomeController {

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

    /** 列出所有的 servelt */
    @RequestMapping(path = "/s",
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    public Object s(HttpServletRequest request) {

        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();

        return servletRegistrations;
    }
}
