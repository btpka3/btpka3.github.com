package me.test.controller

import me.test.service.MyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletResponse

@Controller
class MyTestController {

    @Autowired
    MyService myService

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

    /** 测试 service bean 调用 */
    @RequestMapping("/service")
    @ResponseBody
    String service(
            @RequestParam(defaultValue = "1")
                    int a,
            @RequestParam(defaultValue = "2")
                    int b) {

        return "service ${myService.add(a, b)}"
    }

    /** 测试使用模板引擎 thymeleaf */
    @GetMapping(path = ['/thymeleaf'])
    public ModelAndView thymeleaf() {

        // 对应： classpath:templates/tpl.html
        return new ModelAndView("thymeleaf", [
                a: 1,
                b: 2
        ]);
    }

    /** 测试 ContentNegotiation */
    @GetMapping(path = '/respType', produces = MediaType.TEXT_HTML_VALUE)
    public Object respTypeHtml() {
        // 注意：实际还是去找 view "respType"
        return [
                a: "respTypeHtml",
                b: new Date()
        ];
    }

    @GetMapping(path = '/respType')
    @ResponseBody
    public Object respType() {
        return [
                a: "respType",
                b: new Date()
        ];
    }

    // TODO GSP

    /** 测试错误处理: 500 */
    @GetMapping('/err500')
    public String err500() {
        throw new IllegalArgumentException("err500")
    }

    /** 测试错误处理: 400 */
    @GetMapping('/err400')
    public String err400(HttpServletResponse resp) {

        // FIXME 为何没有被 DefaultHandlerExceptionResolver 处理？
        //throw new MethodArgumentNotValidException("err400")

        //WebUtils.exposeErrorRequestAttributes()

        resp.sendError(HttpStatus.BAD_REQUEST.value())
        return null
    }

    // ================================= 7788
    @Autowired
    ContentNegotiationManager cnm

    /** 测试使用模板引擎 thymeleaf */
    @GetMapping(path = ['/aaa'])
    @ResponseBody
    public Object aaa() {
        return cnm.getAllFileExtensions()
    }

}
