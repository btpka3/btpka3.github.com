package me.test

import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/controller")
class MyTestController {


    @RequestMapping("/")
    @PreAuthorize("permitAll")
    String index() {
        return "Hello World!~ " + new Date();
    }



    @RequestMapping("/403")
    @PreAuthorize("permitAll")
    @ResponseBody
    String access() {
        return "access denied! " + new Date();
    }


    @RequestMapping("/pub")
    @ResponseBody
    String pub(@AuthenticationPrincipal Object curUser) {
        return "/controller/pub : " + curUser;
    }

    @RequestMapping("/sec")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    String sec() {
        return "/controller/sec";
    }

    @RequestMapping("/adm")
    @PreAuthorize("isAuthenticated() && hasAnyRole('ADMIN')")
    @ResponseBody
    String adm() {
        return "/controller/adm";
    }

    // ----------

}
