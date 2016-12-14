package me.test.oauth2.client.controller

import me.test.oauth2.client.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.util.UriComponentsBuilder

/**
 *
 */
@Controller
class MyClientController {

    @Autowired
    MyOAuth2Properties myOAuth2Props

    @Autowired
    OAuth2RestTemplate myRscRestTemplate

    @RequestMapping("/")
    @ResponseBody
    @PreAuthorize("permitAll")
    String index(@AuthenticationPrincipal Object curUser) {
        return "my oauth2 client app~ " + curUser + " @ " + new Date() + " == " + myOAuth2Props.auth.port;
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping("/sec")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    String sec() {
        return "client sec " + new Date();
    }

    // ---------------------------------------------  OAuth 资源区
    @RequestMapping("/photo")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    String photos() {

        String url = myOAuth2Props.resource.photoListUri
        String respStr = myRscRestTemplate.getForObject(url, String)
        return "photos sec : " + respStr;
    }

    @RequestMapping("/photo/{id}")
    @ResponseBody
    @PreAuthorize("permitAll")
    String photo(@PathVariable("id") String id) {
        return "client sec " + new Date();
    }
}
