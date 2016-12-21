package me.test.oauth2.resource.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import java.security.Principal

/**
 *
 */
@Controller
class MyRscController {

    // 模拟图片信息
    private Map photos = [
            "aaa": [
                    name: "aaa.png",
                    size: "10KB"
            ],
            "bbb": [
                    name: "bbb.png",
                    size: "20KB"
            ],
            "ccc": [
                    name: "ccc.png",
                    size: "30KB"
            ]
    ]

    @RequestMapping("/")
    @ResponseBody
    @PreAuthorize("permitAll")
    String index(@AuthenticationPrincipal Object curUser) {
        return "Hello World!~ " + curUser + " @ " + new Date();
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping("/sec")
    @ResponseBody
    @PreAuthorize("permitAll")
    String sec() {
        return "sec " + new Date();
    }

    // ---------------------------------------------  resource server

    /** 获取所有图片列表 */
    @RequestMapping("/o2/photo")
    @ResponseBody
    // OAuth2MethodSecurityExpressionHandler, OAuth2SecurityExpressionMethods, OAuth2Authentication
    @PreAuthorize("#oauth2.hasScope('read')")
    @CrossOrigin
    Object photos(Principal principal) {
        return photos
    }
}
