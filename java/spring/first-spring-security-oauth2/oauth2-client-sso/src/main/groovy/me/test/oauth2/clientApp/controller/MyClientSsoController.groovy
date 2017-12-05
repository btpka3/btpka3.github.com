package me.test.oauth2.clientApp.controller

import me.test.oauth2.common.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import java.security.Principal

/**
 *
 */
@Controller
class MyClientSsoController {

    @Autowired
    MyOAuth2Properties myOAuth2Props


    @RequestMapping("/")
    @ResponseBody
    @PreAuthorize("permitAll")
    String index(@AuthenticationPrincipal Object curUser) {
        return "my oauth2 client sso~ " + curUser + " @ " + new Date() + " == " + myOAuth2Props.auth.url;
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping("/sec")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object sec(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg      : "client sso sec",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()

        ]
    }

}
