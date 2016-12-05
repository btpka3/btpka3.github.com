package me.test

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MyTestController {

    @RequestMapping("/")
    @PreAuthorize("permitAll")
    @ResponseBody
    String index() {
        return "Hello World!~ " + new Date();
    }

    @RequestMapping("/403")
    @PreAuthorize("permitAll")
    @ResponseBody
    String access() {
        return "access denied! " + new Date();
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

}
