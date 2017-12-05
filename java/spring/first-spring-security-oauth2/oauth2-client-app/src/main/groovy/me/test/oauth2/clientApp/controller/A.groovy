package me.test.oauth2.clientApp.controller

import org.springframework.web.util.UriComponentsBuilder

/**
 *
 */
class A {

    static void main(String[] args) {
        String url = "a"
        String backUrlTpl = "https://kingsilk.net/qh/mall/local/?_ddnsPort=16300&wxPub=sys&orgId=59545b702c5efb0242c1c88d#/menu?s=1"
        String redirectUriTpl = "https://kingsilk.net/qh/mall/local/11300/api/weiXin/wxLoginVerify"
        String wxUrlTpl = "https://open.weixin.qq.com/connect/oauth2/authorize"


        String backUrl = getBackUrl(backUrlTpl, url)
        String redirectUrl = getRedirectUrl(redirectUriTpl, backUrl)
        String wxUrl = UriComponentsBuilder.fromHttpUrl(wxUrlTpl)
                .queryParam("appid", "wxbba6def439fc0bd0")

                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("scope", "snsapi_base,snsapi_userinfo")
                .queryParam("state", "sAdjzeQm0c5p9oe6kHojbA3UKfLx4T78")
//                .queryParam("redirect_uri", redirectUrl)
                .fragment("wechat_redirect")
                .build()
                .encode("UTF-8")
                .toString()


        println "1. backUrl     = " + backUrl
        println "2. redirectUrl = " + redirectUrl
        println "2. wxUrl       = " + wxUrl
    }


    static String getBackUrl(String tpl, String url) {
        return UriComponentsBuilder.fromUriString(tpl)
//                .queryParam("url", url)
                .build()
                .encode("UTF-8")
                .toUri()
                .toString()
    }

    static String getRedirectUrl(String tpl, String backUrl) {

        return UriComponentsBuilder.fromHttpUrl(tpl)
                .queryParam("backUrl", backUrl)
                .build()
                .encode("UTF-8")
                .toUri()
                .toString()
    }

//    static String getBackUrl(String tpl, String url) {
//        return UriComponentsBuilder.fromUriString(tpl)
//                .queryParam("url", URLEncoder.encode(url,"UTF-8"))
//                .build(true)
////                .encode("UTF-8")
//                .toUri()
//                .toString()
//    }

//    static String getRedirectUrl(String tpl, String backUrl) {
//
//        return UriComponentsBuilder.fromHttpUrl(tpl)
//                .queryParam("backUrl", URLEncoder.encode(backUrl, "UTF-8"))
//                .build(true)
////                .encode("UTF-8")
//                .toUri()
//                .toString()
//    }


}
