package my.openim.server

import grails.transaction.Transactional
import grails.util.Holders
import groovy.json.JsonBuilder
import org.springframework.util.LinkedMultiValueMap

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Transactional
class OpenIMService {


    def getUsers() {

        String appKey = Holders.config.my.ali.openim.appKey
        String pid = Holders.config.my.ali.alipay.partnerId
        def myParams = genDefaultParams();


        def useridsJsonStr = new JsonBuilder(["banruo", "paohui"]).toString()
        myParams.userids = useridsJsonStr

        myParams.sign = signTOP(myParams, appSecret, myParams.sign_method[0])
        println "sign = $myParams.sign"

        String url = "https://eco.taobao.com/router/rest"
        String respStr = restTemplate.postForObject(url, myParams, String)

        render respStr
    }

    def addUsers() {

    }

    def updateUser() {

    }

    def pushMsg() {

    }

    static LinkedMultiValueMap genDefaultParams() {

        String appKey = Holders.config.my.ali.openim.appKey
        String pid = Holders.config.my.ali.alipay.partnerId
        def myParams = [
                method     : ["taobao.openim.users.get"],
                app_key    : [appKey],
                // session       : [""],
                timestamp  : [getTimestamp()],
                format     : ["json"],
                v          : ["2.0"],
                partner_id : [pid],
                // target_app_key: [""],
                // simplify   : ["false"], //必须全部是 String    xxx
                sign_method: ["hmac"],
                //sign          : [""],
        ] as LinkedMultiValueMap;
        return myParams
    }


    static String getTimestamp() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.now().format(fmt)
    }
}
