package me.test.oauth2.client.controller

import me.test.oauth2.client.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest

/**
 *
 */
@Controller
class MyClientController {

    @Autowired
    MyOAuth2Properties myOAuth2Props


    @RequestMapping("/")
    @ResponseBody
    @PreAuthorize("permitAll")
    String index(@AuthenticationPrincipal Object curUser) {
        return "my oauth2 client app~ " + curUser + " @ " + new Date() + " == " + myOAuth2Props.auth.url;
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping("/sec")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    String sec() {
        return "client sec " + new Date();
    }

    // ---------------------------------------------  OAuth2 : authorization code

    @Autowired
    OAuth2RestTemplate oAuthCodeRestTemplate

    @RequestMapping("/photo/authCode")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object photoAuthCode() {

        String url = myOAuth2Props.resource.photoListUri
        String respStr = oAuthCodeRestTemplate.getForObject(url, String)
        return respStr;
    }

    // ---------------------------------------------  OAuth2 : implicit

//    @Autowired
//    OAuth2RestTemplate oImplicitRestTemplate

    @RequestMapping("/photo/implicit")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object photoImplicit(WebRequest req) {

        // 注意：这里后 前台传过来的AT，故直接用client模式访问
        oClientRestTemplate.getOAuth2ClientContext().setAccessToken(new DefaultOAuth2AccessToken(req.getParameter("at")))

        String url = myOAuth2Props.resource.photoListUri
        String respStr = oClientRestTemplate.getForObject(url, String)
        return respStr;
    }

    // ---------------------------------------------  OAuth2 : password
    @Autowired
    OAuth2RestTemplate oPasswordRestTemplate

    @RequestMapping("/photo/password")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object photoPassword() {

        String url = myOAuth2Props.resource.photoListUri
        String respStr = oPasswordRestTemplate.getForObject(url, String)
        return respStr;
    }

    // ---------------------------------------------  OAuth2 : client
    @Autowired
    OAuth2RestTemplate oClientRestTemplate

    @RequestMapping("/photo/client")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object photoClient() {

        String url = myOAuth2Props.resource.photoListUri
        String respStr = oClientRestTemplate.getForObject(url, String)
        return respStr;
    }
}
