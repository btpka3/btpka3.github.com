package me.test.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MySecController {

    @RequestMapping("/")
    @PreAuthorize("permitAll")
    @ResponseBody
    String index() {
        return "Hello World!~ " + new Date();
    }


    @RequestMapping("/controller/pub")
    @ResponseBody
    String pub(@AuthenticationPrincipal Object curUser) {
        return "/controller/pub : " + curUser;
    }

    @RequestMapping("/controller/sec")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    String sec() {
        return "/controller/sec";
    }

    @RequestMapping("/controller/adm")
    @PreAuthorize("isAuthenticated() && hasAnyRole('ADMIN')")
    @ResponseBody
    String adm() {
        return "/controller/adm";
    }

    // 该路径没有在 SecConf 中明确配置，故交给默认的 http basic 认证配置
    // 浏览器访问该路径，应当只会弹出 basic 认证的输入框，而不会跳转到表单登录画面
    @RequestMapping("/controller/basic")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    String basic() {
        return "/controller/basic";
    }


    // ---------------------------------- ACL test


}
