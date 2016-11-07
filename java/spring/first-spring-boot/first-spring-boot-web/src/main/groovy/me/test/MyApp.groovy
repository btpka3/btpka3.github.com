package me.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.BasicErrorController
import org.springframework.boot.autoconfigure.web.ErrorAttributes
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.ErrorViewResolver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@EnableAutoConfiguration
@SpringBootApplication
class Example   {

    @Autowired
    MyService myService


    // ---------------------

    // 主页
    @RequestMapping("/")
    String home() {
        return "Hello World!~ ${myService.add(1, 2)} " + new Date();
    }

    // TODO XML
    // TODO JSON
    // TODO 404 错误
    // TODO filters
    // TODO interceptor

    // 使用模板引擎
    @GetMapping(path = ['/tplTest'])
    public ModelAndView tplTest() {

        // 对应： classpath:templates/tpl.html
        return new ModelAndView("tpl"); ;
    }

    // TODO 500 错误
    @GetMapping(path = ['/err'])
    public String err() {
        throw new IllegalArgumentException("hahaha")
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }
}
