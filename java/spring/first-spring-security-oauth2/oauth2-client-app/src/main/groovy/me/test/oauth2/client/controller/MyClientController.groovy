package me.test.oauth2.client.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import java.security.Principal

/**
 *
 */
@Controller
class MyClientController {

    @RequestMapping("/")
    @ResponseBody
    @PreAuthorize("permitAll")
    String index(@AuthenticationPrincipal Object curUser) {
        return "my oauth2 client app~ " + curUser + " @ " + new Date();
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping("/sec")
    @ResponseBody
    @PreAuthorize("permitAll")
    String sec() {
        return "client sec " + new Date();
    }

    // ---------------------------------------------  OAuth 资源区
    @RequestMapping("/photo")
    @ResponseBody
    @PreAuthorize("permitAll")
    String photos() {
        return "photos sec " + new Date();
    }

    @RequestMapping("/photo/{id}")
    @ResponseBody
    @PreAuthorize("permitAll")
    String photo(@PathVariable("id") String id) {
        return "client sec " + new Date();
    }
}
