package me.test.first.chanpay.controller;

import me.test.first.chanpay.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MyTestController {

    /**
     * 商户私钥
     */
    private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB";
    /**
     * 畅捷支付平台公钥
     */
    private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";


    @Autowired
    TradeApi tradeApi;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "home " + new Date();
    }


    /** */
    @RequestMapping("/test01")
    @ResponseBody
    public Object service() {


        TradeApiReq req = new TradeApiReq();
        req.setService("cjt_wap_create_instant_trade");
        req.setVersion("1.0");

        req.setPartnerId("200000140001");
        req.setInputCharset("UTF-8");

        String outTradeNo = (UUID.randomUUID().toString()).replace("-", "");
        req.setOutTradeNo(outTradeNo);
        req.setTradeAmount("0.01");
        req.setProductName("般若的测试商品");
        req.setNotifyUrl("http://dev.chanpay.com/receive.php");

        req.setPayMethod("1");
        req.setPayType("C,GC");
        req.setBankCode("WXPAY");
        req.setIsAnonymous("Y");
        req.setReturnUrl("Y");


        // 以下俩自行选择一个看效果
        // 绑卡
        //req.setExt1("[{'hasUserSign':'true','userSign':'yangyang0test'}]");

        // 绑卡 + wap收银台2.0
        // origMap.put("ext1", "[{'hasUserSign':'true','userSign':'yangyang0test','version':'2.0'}]");


        TradeApiResp resp = tradeApi.execute(req);
        Map map = new LinkedHashMap();
        map.put("req", req);
        map.put("resp", resp);
        return map;
    }

}
