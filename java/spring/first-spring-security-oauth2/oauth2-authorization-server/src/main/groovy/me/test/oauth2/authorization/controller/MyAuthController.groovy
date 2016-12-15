package me.test.oauth2.authorization.controller

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
class MyAuthController {

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

    // TODO 同时判断用户权限 和 client 权限
    /** 获取当前用户信息 */
    @RequestMapping("/o2/me")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('read')")  // OAuth2MethodSecurityExpressionHandler
    String me(Principal principal) {
        return [
                name: principal.name
        ]
    }

    /** 获取所有图片列表 */
    @RequestMapping("/o2/photo")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('read')")  // OAuth2MethodSecurityExpressionHandler
    String photos(Principal principal) {
        return photos
    }

    /** 获取单个图片信息 */
    @RequestMapping("/o2/photo/{photoId}")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('read')")  // OAuth2MethodSecurityExpressionHandler
    String photo(Principal principal, @PathVariable("photoId") String id) {
        return photos.get(id)
    }

}
