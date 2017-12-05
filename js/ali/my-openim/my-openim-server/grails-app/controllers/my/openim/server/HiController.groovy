package my.openim.server

import grails.util.Holders
import groovy.json.JsonBuilder
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.digest.HmacUtils
import org.apache.commons.lang.StringUtils
import org.springframework.util.Assert
import org.springframework.util.LinkedMultiValueMap

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HiController {
    def restTemplate

    def index() {
        render "OK " + new Date()
    }


    def testGetUser() {
        String appKey = Holders.config.my.ali.openim.appKey
        String pid = Holders.config.my.ali.alipay.partnerId
        String appSecret = Holders.config.my.ali.openim.appSecret

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

        // 注意:逗号分隔, 官网API文档中未说明。
        myParams.userids = ["banruo", "paohui"].join(",")

        myParams.sign = signTOP(myParams, appSecret, myParams.sign_method[0])
        println "sign = $myParams.sign"

        String url = "https://eco.taobao.com/router/rest"
        String respStr = restTemplate.postForObject(url, myParams, String)

        render respStr
    }

    def testAddUser() {

        String appSecret = Holders.config.my.ali.openim.appSecret

        def userinfos = [
                [
                        nick    : "般若",
                        icon_url: "http://qlogo2.store.qq.com/qzone/103430585/103430585/100?1335026205",
                        email   : "btpka3@163.com",
                        mobile  : "18600000000",
                        taobaoid: "btpka3",
                        userid  : "banruo",
                        password: "banruo",
                        remark  : "demoRemark",
                        extra   : "{}",
                        career  : "CTO",
                        vip     : "{}",
                        address : "demoAddr",
                        name    : "demoName",
                        age     : "33",
                        gender  : "M",
                        wechat  : "",
                        qq      : "",
                        weibo   : ""
                ],
                [
                        nick    : "炮灰",
                        icon_url: "http://qlogo4.store.qq.com/qzone/806677583/806677583/100?1369043022",
                        email   : "806677583@qq.com",
                        mobile  : "18100000000",
                        taobaoid: "",
                        userid  : "paohui",
                        password: "paohui",
                        remark  : "demoRemark1",
                        extra   : "{}",
                        career  : "CTO",
                        vip     : "{}",
                        address : "demoAddr1",
                        name    : "demoName1",
                        age     : "22",
                        gender  : "M",
                        wechat  : "",
                        qq      : "",
                        weibo   : ""
                ],
        ]
        def userinfosJsonStr = new JsonBuilder(userinfos).toString()

        def myParams = [
                method     : ["taobao.openim.users.add"],
                app_key    : [Holders.config.my.ali.openim.appKey as String],
//                session       : [""],
                timestamp  : [getTimestamp()],
                format     : ["json"],
                v          : ["2.0"],
                partner_id : [Holders.config.my.ali.alipay.partnerId as String],
//                target_app_key: [""],
//                simplify   : ["false"], //必须全部是 String    xxx
                sign_method: ["hmac"],
                //sign          : [""],

                userinfos  : [userinfosJsonStr],


        ] as LinkedMultiValueMap;

        myParams.sign = signTOP(myParams, appSecret, myParams.sign_method[0])
        println "sign = $myParams.sign"

        String url = "https://eco.taobao.com/router/rest"
        String respStr = restTemplate.postForObject(url, myParams, String)

        render respStr
    }

    def testSign() {
        println "md5  : " + DigestUtils.md5Hex("aaa").toUpperCase() // 47BCE5C74F589F4867DBD57E9CA9F808
        println "hmac : " + HmacUtils.hmacMd5Hex("bbb", "aaa").toUpperCase() // 6161FD740A74BF92DCB0090499B1799F

        def myParams = [
                method     : ["taobao.openim.users.add"],
                app_key    : ["23423859"],
                //session       : [""],
                timestamp  : ["2016-10-24 14:17:56"],
                format     : ["json"],
                v          : ["2.0"],
                partner_id : ["top-sdk-java-20161017"],
                //target_app_key: [""],
                //simplify      : ["false"], //必须全部是 String
                sign_method: ["md5"], // md5
                //sign          : ["54DAE05DD03F5F3C4DE1F72F43FE46A2"],

                userinfos  : ["[{\"nick\":\"\\u822c\\u82e5\",\"icon_url\":\"http://qlogo2.store.qq.com/qzone/103430585/103430585/100?1335026205\",\"email\":\"btpka3@163.com\",\"mobile\":\"18600000000\",\"taobaoid\":\"btpka3\",\"userid\":\"banruo\",\"password\":\"banruo\",\"remark\":\"demoRemark\",\"extra\":\"{}\",\"career\":\"CTO\",\"vip\":\"{}\",\"address\":\"demoAddr\",\"name\":\"demoName\",\"age\":\"33\",\"gender\":\"M\",\"wechat\":\"\",\"qq\":\"\",\"weibo\":\"\"}]"],

        ] as LinkedMultiValueMap;

        def sign = signTOP(myParams, "2283fed9441921cceedc4275b3df7afd", "md5")
        render "exptected '466CF5807E938668B4BB8581A1DD0D24', actual is '$sign'"
    }

    private String getTimestamp() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.now().format(fmt)
    }

    // http://baichuan.taobao.com/doc2/detail?treeId=34&articleId=102485&docType=1
    // 主要:要不包含key或者value为null,空串,空白串的参数
    private String signTOP(LinkedMultiValueMap reqParams, String secret, String signMethod) {

        Assert.isTrue(['md5', 'hmac'].contains(signMethod), "TOP API only support sign method 'md5' or 'hmac', current is '$signMethod'")

        // 按照 参数key进行排序，因此使用 TreeMap
        def signParams = new TreeMap(reqParams);

        def buf = new StringBuilder()
        if ("md5" == signMethod) {
            buf.append(secret)
        }

        signParams.each { k, v ->
            if ("sign" == k || StringUtils.isBlank(k) || !v || v.every { StringUtils.isBlank(it) }) {
                return;
            }
            buf.append(k).append(v[0])
        }

        if ("md5" == signMethod) {
            buf.append(secret)
        }

        String msg = buf.toString()
        if (log.isDebugEnabled()) {
            log.debug("msg is : \n$msg")
        }

        if ("md5" == signMethod) {
            return DigestUtils.md5Hex(msg).toUpperCase()
        } else {
            return HmacUtils.hmacMd5Hex(secret, msg).toUpperCase()
        }
    }
}
